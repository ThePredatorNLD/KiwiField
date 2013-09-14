package me.KiwiLetsPlay.KiwiField;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.game.Game;
import me.KiwiLetsPlay.KiwiField.item.GameItem;
import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.item.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Ammunition;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.item.weapon.melee.MeleeWeapon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KiwiListener implements Listener {
	
	private HashMap<String, Boolean> headshot;
	
	public KiwiListener() {
		headshot = new HashMap<String, Boolean>();
		
		Bukkit.getScheduler().runTaskTimer(KiwiField.getInstance(), new TickListener(), 1, 1);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!(event.getRightClicked() instanceof LivingEntity)) return;
		Player player = event.getPlayer();
		LivingEntity entity = (LivingEntity) event.getRightClicked();
		if (entity.isDead()) {
			event.setCancelled(true);
			return;
		}
		
		Weapon w = Items.getWeaponByPlayer(player);
		if (!(w instanceof MeleeWeapon)) {
			event.setCancelled(true);
			PlayerInteractEvent pie = new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, player.getItemInHand(), null, null);
			Bukkit.getPluginManager().callEvent(pie);
			return;
		}
		
		EntityDamageByEntityEvent e = new EntityDamageByEntityEvent(player, entity, DamageCause.ENTITY_ATTACK, -1d);
		Bukkit.getPluginManager().callEvent(e);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getPlayer().isDead()) {
			event.setCancelled(true);
			return;
		}
		
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if (player.getGameMode() == GameMode.SURVIVAL) {
				event.setCancelled(true);
			}
			Weapon w = Items.getWeaponByPlayer(player);
			if (w instanceof Gun) {
				ProjectileUtil.startReloading(player);
			}
		} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if (player.getItemInHand() == null) return;
			
			Weapon w = Items.getWeaponByPlayer(player);
			if (w == null) return;
			
			KiwiField.getCurrentGame().setSpawnProtected(player, false);
			
			if (w instanceof Gun) {
				Gun g = (Gun) w;
				if (g.isAutomatic()) {
					ProjectileUtil.setFiringWeapon(player, g, true);
				} else {
					if (!(ProjectileUtil.isFiringWeapon(player))) {
						if (ProjectileUtil.launchBullet(player, g)) {
							KiwiField.getCurrentGame().getStatsTracker().registerWeaponUsed(player, w);
						}
					}
					
					ProjectileUtil.setFiringWeapon(player, g, true);
				}
			} else if (w instanceof Grenade) {
				if (!(ProjectileUtil.isWeaponCooledDown(player))) return;
				
				Grenade g = (Grenade) w;
				Item i = ProjectileUtil.launchGrenade(player, g);
				ProjectileUtil.setWeaponCooldown(player, g, true);
				KiwiField.getCurrentGame().getStatsTracker().registerWeaponUsed(player, w);
				g.playFiringSound(player);
				
				GrenadeExploder ge = new GrenadeExploder(g, i);
				Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), ge, g.getFuseLenght());
			} else if (w instanceof MeleeWeapon) {
				if (!(ProjectileUtil.isWeaponCooledDown(player))) return;
				
				ProjectileUtil.setUsingKnife(player, (MeleeWeapon) w, false, true);
				KiwiField.getCurrentGame().getStatsTracker().registerWeaponUsed(player, w);
				w.playFiringSound(player);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickUp(PlayerPickupItemEvent event) {
		event.setCancelled(true);
		
		ItemStack is = event.getItem().getItemStack();
		GameItem i = Items.getItemByItemStack(is);
		if (!(i instanceof Gun)) return;
		Weapon w = (Weapon) i;
		
		if (event.getPlayer().getInventory().getItem(w.getInventorySlot()) != null) return;
		
		Ammunition a = Ammunition.fromItemStack(is);
		if (a == null) return;
		
		Ammunition.setItemMeta(is, a.getPrimaryAmmo(), a.getBackupAmmo());
		event.getPlayer().getInventory().setItem(w.getInventorySlot(), is);
		event.getItem().remove();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
		Player p = event.getPlayer();
		if (p.getGameMode() == GameMode.SURVIVAL) {
			if (KiwiField.getCurrentGame().hasMoneySystem()) {
				Weapon w = Items.getWeaponByPlayer(p);
				if (w instanceof Gun) { // TODO: Enable dropping C4s
					ItemStack is = p.getItemInHand();
					is.setAmount(1);
					p.getWorld().dropItemNaturally(event.getPlayer().getLocation(), is);
					p.getInventory().clear(p.getInventory().getHeldItemSlot());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked().getGameMode() == GameMode.SURVIVAL) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		EntityDamageEvent lastDmg = event.getEntity().getLastDamageCause();
		if (lastDmg == null) return;
		
		StringBuilder sb = new StringBuilder();
		switch (lastDmg.getCause()) {
		case PROJECTILE:
			EntityDamageByEntityEvent projectileHit = (EntityDamageByEntityEvent) lastDmg;
			if (projectileHit.getDamager() == null || !(projectileHit.getDamager().hasMetadata("weaponname"))) {
				// Death caused by a vanilla Minecraft projectile, don't alter
				return;
			}
			if (projectileHit.getDamager().getType() == EntityType.SNOWBALL) {
				Projectile proj = (Projectile) projectileHit.getDamager();
				Player shooter = (Player) proj.getShooter();
				Player corpse = event.getEntity();
				String weaponname = proj.getMetadata("weaponname").get(0).asString();
				boolean hs = headshot.containsKey(corpse.getName()) && headshot.get(corpse.getName()).booleanValue();
				
				sb.append(ChatColor.GREEN).append(shooter.getName()).append(ChatColor.WHITE);
				sb.append(" [").append(weaponname).append("] ");
				if (hs) sb.append(ChatColor.GOLD).append("<+> ");
				sb.append(ChatColor.RED).append(corpse.getName());
				
				KiwiField.getCurrentGame().getHandler().onPlayerKilled(shooter, corpse, (Weapon) Items.getItemByName(weaponname));
				KiwiField.getCurrentGame().getStatsTracker().registerPlayerKilled(shooter, corpse, weaponname, hs);
			} else if (projectileHit.getDamager().getType() == EntityType.DROPPED_ITEM) {
				Item item = (Item) projectileHit.getDamager();
				Player shooter = (Player) item.getMetadata("shooter").get(0).value();
				Player corpse = event.getEntity();
				String weaponname = item.getMetadata("weaponname").get(0).asString();
				
				sb.append(ChatColor.GREEN).append(shooter.getName()).append(ChatColor.WHITE);
				sb.append(" [").append(weaponname).append("] ");
				sb.append(ChatColor.RED).append(corpse.getName());
				
				KiwiField.getCurrentGame().getHandler().onPlayerKilled(shooter, corpse, (Weapon) Items.getItemByName(weaponname));
				KiwiField.getCurrentGame().getStatsTracker().registerPlayerKilled(shooter, corpse, weaponname, false);
			}
			break;
		case ENTITY_ATTACK:
			EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) lastDmg;
			if (entityDamage.getDamager() == null || entityDamage.getDamager().getType() != EntityType.PLAYER) {
				// Death caused by a vanilla mob, don't alter
				return;
			}
			Player damager = (Player) entityDamage.getDamager();
			Player victim = event.getEntity();
			
			ItemStack is = damager.getItemInHand();
			
			sb.append(ChatColor.GREEN).append(damager.getName());
			if (is.getDurability() == 0) {
				sb.append(ChatColor.WHITE).append(" [KNIFE] ");
			} else {
				sb.append(ChatColor.WHITE).append(" [GOLDEN KNIFE] ");
			}
			sb.append(ChatColor.RED).append(victim.getName());
			
			if (is.getDurability() == 0) {
				KiwiField.getCurrentGame().getHandler().onPlayerKilled(damager, victim, Items.KNIFE);
				KiwiField.getCurrentGame().getStatsTracker().registerPlayerKilled(damager, victim, "Knife", false);
			} else {
				KiwiField.getCurrentGame().getHandler().onPlayerKilled(damager, victim, Items.GOLDEN_KNIFE);
				KiwiField.getCurrentGame().getStatsTracker().registerPlayerKilled(damager, victim, "Golden Knife", false);
			}
			break;
		default:
			sb.append(ChatColor.RED).append(event.getEntity().getName());
			sb.append(ChatColor.WHITE).append(" killed himself.");
			
			KiwiField.getCurrentGame().getHandler().onPlayerKilled(event.getEntity(), event.getEntity(), null);
			KiwiField.getCurrentGame().getStatsTracker().registerPlayerKilled(event.getEntity(), event.getEntity(), "Stupidity", false);
			break;
		}
		
		event.setDeathMessage(sb.toString());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		player.closeInventory();
		KiwiField.getCurrentGame().getHandler().onPlayerRespawn(player);
		ProjectileUtil.switchWeapon(event.getPlayer(), player.getItemInHand());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		// Cancel unless actually a valid attack
		event.setCancelled(true);
		
		if (event.getCause() == DamageCause.ENTITY_ATTACK && event.getDamager().getType() == EntityType.PLAYER) {
			if (!(event.getEntity() instanceof LivingEntity)) return;
			
			Player damager = (Player) event.getDamager();
			LivingEntity entity = (LivingEntity) event.getEntity();
			Weapon w = Items.getWeaponByPlayer(damager);
			if (!(w instanceof MeleeWeapon)) return;
			MeleeWeapon m = (MeleeWeapon) w;
			
			double damage;
			float diff = Math.abs(damager.getLocation().getYaw() - entity.getLocation().getYaw());
			if (diff > 180) diff -= 360;
			
			if (entity instanceof Player && KiwiField.getCurrentGame().isSpawnProtected((Player) entity)) return;
			if (!(ProjectileUtil.isWeaponCooledDown(damager))) return;
			
			if (event.getDamage() == -1) {
				if (Math.abs(diff) < 60) {
					damage = m.getBackstabDamage();
					entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
				} else {
					damage = m.getDamage();
				}
				m.playFiringSound(damager);
			} else {
				if (Math.abs(diff) < 60) {
					damage = m.getSecondaryBackstabDamage();
					entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
				} else {
					damage = m.getSecondaryDamage();
				}
				m.playSecondaryAttackSound(damager);
			}
			
			KiwiField.getCurrentGame().setSpawnProtected(damager, false);
			ProjectileUtil.setUsingKnife(damager, m, true, true);
			KiwiField.getCurrentGame().getStatsTracker().registerWeaponUsed(damager, w);
			
			// Make sure that the player will take damage
			entity.setNoDamageTicks(0);
			if (entity.getHealth() <= damage) {
				entity.setLastDamageCause(event);
			}
			entity.damage(damage);
		} else if (event.getCause() == DamageCause.PROJECTILE && event.getDamager().getType() == EntityType.SNOWBALL) {
			if (!(event.getEntity() instanceof LivingEntity)) return;
			
			LivingEntity entity = (LivingEntity) event.getEntity();
			Projectile proj = (Projectile) event.getDamager();
			
			double damage = proj.getMetadata("damage").get(0).asDouble();
			boolean piercing = proj.getMetadata("piercing").get(0).asBoolean();
			
			if (event.getEntityType() == EntityType.PLAYER) {
				Player player = (Player) event.getEntity();
				
				if (KiwiField.getCurrentGame().isSpawnProtected(player)) {
					event.setCancelled(true);
					return;
				}
				
				boolean hs = proj.getLocation().getY() - event.getEntity().getLocation().getY() > 1.35;
				headshot.put(player.getName(), hs);
				if (hs) damage *= 4;
				PlayerInventory i = player.getInventory();
				if ((hs && i.getHelmet() != null) || (!hs && i.getChestplate() != null)) {
					if (piercing) {
						damage *= 0.75;
					} else {
						damage *= 0.5;
					}
				}
			}
			
			// Make sure that the player will take damage
			entity.setNoDamageTicks(0);
			if (entity.getHealth() <= damage) {
				if (entity != proj.getShooter()) {
					entity.damage(0d, proj.getShooter());
					entity.setNoDamageTicks(0);
				}
				entity.setLastDamageCause(event);
			}
			entity.damage(damage);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY()
				|| from.getBlockZ() != to.getBlockZ()) {
			KiwiField.getCurrentGame().setSpawnProtected(p, false);
		}
		
		if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
			ProjectileUtil.setMoving(p, true);
		} else {
			ProjectileUtil.setMoving(p, false);
		}
		
		Location b1 = new Location(to.getWorld(), to.getX() + 0.3, to.getY() - 0.75, to.getZ() + 0.3);
		Location b2 = new Location(to.getWorld(), to.getX() - 0.3, to.getY() - 0.75, to.getZ() + 0.3);
		Location b3 = new Location(to.getWorld(), to.getX() + 0.3, to.getY() - 0.75, to.getZ() - 0.3);
		Location b4 = new Location(to.getWorld(), to.getX() - 0.3, to.getY() - 0.75, to.getZ() - 0.3);
		if (b1.getBlock().isEmpty() && b2.getBlock().isEmpty() && b3.getBlock().isEmpty() && b4.getBlock().isEmpty()) {
			ProjectileUtil.setJumping(p, true);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		KiwiField.getCurrentGame().getStatsTracker().addPlayer(event.getPlayer());
		// TEMP
		KiwiField.getCurrentGame().getStatsTracker().setChatColor(event.getPlayer(), ChatColor.GREEN);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemHeldChange(PlayerItemHeldEvent event) {
		ItemStack is = event.getPlayer().getInventory().getItem(event.getNewSlot());
		ProjectileUtil.switchWeapon(event.getPlayer(), is);
	}
}

class TickListener implements Runnable {
	
	@Override
	public void run() {
		Game game = KiwiField.getCurrentGame();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isDead()) continue;
			Weapon w = Items.getWeaponByPlayer(p);
			if (!(w instanceof Gun)) continue;
			Gun g = (Gun) w;
			if (!(g.isAutomatic())) continue;
			
			if (ProjectileUtil.isFiringWeapon(p)) {
				if (ProjectileUtil.launchBullet(p, g)) {
					game.getStatsTracker().registerWeaponUsed(p, g);
				}
			}
			
			game.getStatsTracker().registerWeaponUsed(p, w);
		}
	}
}

class GrenadeExploder implements Runnable {
	
	Grenade g;
	Item i;
	
	GrenadeExploder(Grenade grenade, Item item) {
		g = grenade;
		i = item;
	}
	
	@Override
	public void run() {
		g.explode(i);
		i.remove();
	}
}
