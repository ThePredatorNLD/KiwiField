package me.KiwiLetsPlay.KiwiField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.weapon.gun.pistol.DesertEagle;
import me.KiwiLetsPlay.KiwiField.weapon.gun.smg.MP7;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class KiwiListener implements Listener {
	
	private KiwiField plugin;
	
	private static HashMap<String, Boolean> headshot = new HashMap<String, Boolean>();
	
	public KiwiListener() {
		this.plugin = KiwiField.getInstance();
		headshot = new HashMap<String, Boolean>();
		
		Bukkit.getScheduler().runTaskTimer(KiwiField.getInstance(), new TickListener(), 1, 1);
		Bukkit.getScheduler().runTaskTimer(KiwiField.getInstance(), new SnowballRemover(), 20, 20);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
				event.setCancelled(true);
			}
		} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final Player player = event.getPlayer();
			if (player.getItemInHand() == null) return;
			if (!(player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasLore())) return;
			
			Weapon w = getWeaponFromItemStack(player.getItemInHand());
			if (w == null) return;
			
			if (w instanceof Gun) {
				Gun g = (Gun) w;
				if (g.isAutomatic()) {
					ProjectileUtil.setFiringWeapon(player, g, true);
				} else {
					if (!(ProjectileUtil.isFiringWeapon(player))) {
						if (!(ProjectileUtil.isWeaponCooledDown(player))) return;
						ProjectileUtil.launchBullet(player, g);
						ProjectileUtil.setWeaponCooldown(player, g, true);
						w.playFiringSound(player);
					}
					
					ProjectileUtil.setFiringWeapon(player, g, true);
				}
			}
			
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntityType() != EntityType.SNOWBALL) return;
		
		final Snowball snowballH = (Snowball) event.getEntity();
		if (snowballH.hasMetadata("grenade")) {
			snowballH.getWorld().playSound(snowballH.getLocation(), Sound.DIG_STONE, 1, 2);
			final Item grsno = snowballH.getWorld().dropItem(snowballH.getLocation(), new ItemStack(Material.CLAY_BALL,
					1));
			grsno.setMetadata("ubrot", new FixedMetadataValue(plugin, true));
			
			// XXX: Temporary, should be moved.
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					
					List<Entity> entities = snowballH.getNearbyEntities(3, 3, 3);
					for (Entity entity : entities) {
						if (entity.getType() == EntityType.PLAYER) {
							Player player = (Player) entity;
							Projectile proj = (Projectile) snowballH;
							
							if (player.getHealth() <= 15) {
								// Make sure that the player WILL take this damage
								player.setNoDamageTicks(0);
								player.damage(15, proj);
							} else {
								// Directly modify health to avoid Minecraft hit protection
								player.setHealth(player.getHealth() - 6);
							}
						}
					}
					grsno.remove();
					grsno.getWorld().playSound(grsno.getLocation(), Sound.EXPLODE, 10, 1);
					grsno.getWorld().playEffect(grsno.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
				}
			}, 50L);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickUp(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerThrowMedikit(PlayerDropItemEvent event) {
		
		// To be removed after proper implementation of throwables
		
		switch (event.getItemDrop().getItemStack().getType()) {
		/* Disable Medikits and Ammokits
		 * case IRON_INGOT:
			if (!(checkWeaponCooledDown(event.getPlayer()))) {
				event.setCancelled(true);
				return;
			}
			
			weaponCooldown.put(event.getPlayer().getName(), System.currentTimeMillis() + 5000);
			
			final Item medikit = event.getItemDrop();
			medikit.setMetadata("medikit", new FixedMetadataValue(plugin, true));
			event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 0.5f, 2);
			event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.DIG_GRAVEL, 0.5f, 2);
			
			List<Entity> healed = medikit.getNearbyEntities(3, 3, 3);
			for (int i = 0; i < healed.size(); i++) {
				final Entity entity = medikit.getNearbyEntities(3, 3, 3).get(i);
				if (entity instanceof Player) {
					((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
					((Player) entity).setFoodLevel(20);
				}
			}
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					medikit.remove();
				}
			}, 100L);
			
			List<String> mk = new ArrayList<String>();
			mk.add(ChatColor.GRAY + "Medikit");
			ItemStack medi = setName(new ItemStack(Material.IRON_INGOT), "Medikit", mk);
			
			event.getPlayer().getInventory().setItem(3, medi);
			event.getPlayer().updateInventory();
			return;
		case GOLD_INGOT:
			if (!(checkWeaponCooledDown(event.getPlayer()))) {
				event.setCancelled(true);
				return;
			}
			
			weaponCooldown.put(event.getPlayer().getName(), System.currentTimeMillis() + 5000);
			
			final Item ammokit = event.getItemDrop();
			ammokit.setMetadata("ammokit", new FixedMetadataValue(plugin, true));
			event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 0.5f, 2);
			event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.DIG_WOOD, 0.5f, 1);
			
			List<Entity> resupplied = ammokit.getNearbyEntities(3, 3, 3);
			for (int i = 0; i < resupplied.size(); i++) {
				final Entity entity = ammokit.getNearbyEntities(3, 3, 3).get(i);
				if (entity instanceof Player) {
					Player player = (Player) entity;
					List<String> gr = new ArrayList<String>();
					gr.add(ChatColor.GRAY + "M67");
					ItemStack grenade = setName(new ItemStack(Material.CLAY_BALL), "Grenade", gr);
					player.getInventory().setItem(1, grenade);
					if (player.getInventory().getHelmet() != null) {
						player.getInventory().getHelmet().setDurability((short) 0);
					}
					if (player.getInventory().getChestplate() != null) {
						player.getInventory().getChestplate().setDurability((short) 0);
					}
					if (player.getInventory().getLeggings() != null) {
						player.getInventory().getLeggings().setDurability((short) 0);
					}
					if (player.getInventory().getBoots() != null) {
						player.getInventory().getBoots().setDurability((short) 0);
					}
				}
			}
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					ammokit.remove();
				}
			}, 100L);
			
			List<String> ak = new ArrayList<String>();
			ak.add(ChatColor.GRAY + "Ammokit");
			ItemStack ammo = setName(new ItemStack(Material.GOLD_INGOT), "Ammokit", ak);
			
			event.getPlayer().getInventory().setItem(3, ammo);
			event.getPlayer().updateInventory();
			return;*/
		default:
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		EntityDamageEvent lastDmg = event.getEntity().getLastDamageCause();
		StringBuilder sb = new StringBuilder();
		switch (lastDmg.getCause()) {
		case PROJECTILE:
			EntityDamageByEntityEvent projectileHit = (EntityDamageByEntityEvent) lastDmg;
			Projectile proj = (Projectile) projectileHit.getDamager();
			if (proj == null || proj.getType() != EntityType.SNOWBALL || !(proj.hasMetadata("weaponname"))) {
				// Death caused by a vanilla Minecraft projectile, don't alter
				return;
			}
			Player shooter = (Player) proj.getShooter();
			sb.append(ChatColor.GREEN).append(shooter.getName()).append(ChatColor.WHITE);
			sb.append(" [");
			sb.append(proj.getMetadata("weaponname").get(0).asString());
			sb.append("] ");
			if (headshot.containsKey(event.getEntity().getName())
					&& headshot.get(event.getEntity().getName()).booleanValue()) {
				sb.append(ChatColor.GOLD).append("<+> ");
			}
			sb.append(ChatColor.RED).append(event.getEntity().getName());
			break;
		case ENTITY_ATTACK:
			// TODO: Filter out punching with weapons.
			EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) lastDmg;
			Entity damager = entityDamage.getDamager();
			if (damager == null || damager.getType() != EntityType.PLAYER) {
				// Death caused by a vanilla mob, don't alter
				return;
			}
			sb.append(ChatColor.GREEN).append(((Player) damager).getName());
			sb.append(ChatColor.WHITE).append(" [KNIFE] ");
			sb.append(ChatColor.RED).append(event.getEntity().getName());
			break;
		default:
			sb.append(ChatColor.RED).append(event.getEntity().getName());
			sb.append(ChatColor.WHITE).append(" killed himself.");
			break;
		}
		
		event.setDeathMessage(sb.toString());
		
		// Should be moved to respawn!
		List<String> gr = new ArrayList<String>();
		gr.add(ChatColor.GRAY + "M67");
		ItemStack grenade = setName(new ItemStack(Material.CLAY_BALL), "Grenade", gr);
		event.getEntity().getInventory().setItem(1, grenade);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSnowballHit(EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() != EntityType.SNOWBALL) return;
		if (!(event.getEntity() instanceof LivingEntity)) {
			event.setCancelled(true);
			return;
		}
		
		LivingEntity entity = (LivingEntity) event.getEntity();
		Projectile proj = (Projectile) event.getDamager();
		if (!(proj.hasMetadata("damage"))) {
			return;
		}
		
		double damage = proj.getMetadata("damage").get(0).asDouble();
		
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			
			boolean hs = proj.getLocation().getY() - event.getEntity().getLocation().getY() > 1.35;
			headshot.put(player.getName(), hs);
			if (hs) damage *= 3;
		}
		
		// Make sure that the player will take damage
		entity.setNoDamageTicks(0);
		if (entity.getHealth() <= damage) {
			if (entity != proj.getShooter()) {
				entity.damage(0, proj.getShooter());
				entity.setNoDamageTicks(0);
			}
			entity.setLastDamageCause(event);
			entity.damage(damage);
		} else {
			entity.damage(damage);
		}
		
		event.setCancelled(true);
	}
	
	private ItemStack setName(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	private Weapon getWeaponFromItemStack(ItemStack i) {
		// TODO: Implement weapons.
		if (i.getType() == Material.FLINT) return new DesertEagle();
		return new MP7();
	}
}

class TickListener implements Runnable {
	
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isDead()) continue;
			Weapon w = getWeaponFromItemStack(p.getItemInHand());
			if (w == null) continue;
			if (!(w instanceof Gun)) continue;
			Gun g = (Gun) w;
			if (!(g.isAutomatic())) continue;
			
			if (ProjectileUtil.isFiringWeapon(p) && ProjectileUtil.isWeaponCooledDown(p)) {
				ProjectileUtil.launchBullet(p, g);
				ProjectileUtil.setWeaponCooldown(p, g, true);
				g.playFiringSound(p);
			}
		}
	}
	
	private Weapon getWeaponFromItemStack(ItemStack i) {
		// TODO: Implement weapons.
		if (i.getType() == Material.FLINT) return new DesertEagle();
		return new MP7();
	}
}

class SnowballRemover implements Runnable {
	
	@Override
	public void run() {
		World w = Bukkit.getWorlds().get(0);
		for (Snowball sb : w.getEntitiesByClass(Snowball.class)) {
			if (sb.getTicksLived() > 80) {
				sb.remove();
			}
		}
	}
}

class GrenadeExploder implements Runnable {
	
	Grenade g;
	
	GrenadeExploder(Grenade grenade) {
		g = grenade;
	}
	
	@Override
	public void run() {
		g.explode();
	}
}