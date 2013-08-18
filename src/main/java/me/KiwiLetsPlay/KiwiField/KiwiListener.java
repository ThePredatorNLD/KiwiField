package me.KiwiLetsPlay.KiwiField;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.weapon.grenade.HighExplosiveGrenade;
import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.weapon.gun.pistol.DesertEagle;
import me.KiwiLetsPlay.KiwiField.weapon.gun.smg.MP7;
import me.KiwiLetsPlay.KiwiField.weapon.heavy.Nova;
import me.KiwiLetsPlay.KiwiField.weapon.heavy.Shotgun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KiwiListener implements Listener {
	
	private HashMap<String, Boolean> headshot;
	private HashMap<String, Long> spawnProtection;
	private StatsUtil statsUtil;
	
	public KiwiListener() {
		headshot = new HashMap<String, Boolean>();
		spawnProtection = new HashMap<String, Long>();
		
		statsUtil = new StatsUtil(Bukkit.getOnlinePlayers());
		// TEMP
		for (Player p : Bukkit.getOnlinePlayers()) {
			statsUtil.setChatColor(p, ChatColor.GREEN);
		}
		
		Bukkit.getScheduler().runTaskTimer(KiwiField.getInstance(), new TickListener(this), 1, 1);
		Bukkit.getScheduler().runTaskTimer(KiwiField.getInstance(), new SnowballRemover(), 20, 20);
	}
	
	public StatsUtil getStatsUtil() {
		return statsUtil;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
				event.setCancelled(true);
			}
		} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if (player.getItemInHand() == null) return;
			
			Weapon w = getWeaponFromItemStack(player.getItemInHand());
			if (w == null) return;
			
			setSpawnProtected(player, false);
			
			if (w instanceof Shotgun) {
				Shotgun g = (Shotgun) w;
				
				if (!(ProjectileUtil.isFiringWeapon(player))) {
					if (!(ProjectileUtil.isWeaponCooledDown(player))) return;
					for (int i = 0; i < g.getPelletCount(); i++) {
						ProjectileUtil.launchBullet(player, g);
					}
					ProjectileUtil.setWeaponCooldown(player, g, true);
					statsUtil.registerWeaponUsed(player, w, g.getPelletCount());
					w.playFiringSound(player);
				}
				
				ProjectileUtil.setFiringWeapon(player, g, true);
			} else if (w instanceof Gun) {
				Gun g = (Gun) w;
				if (g.isAutomatic()) {
					ProjectileUtil.setFiringWeapon(player, g, true);
				} else {
					if (!(ProjectileUtil.isFiringWeapon(player))) {
						if (!(ProjectileUtil.isWeaponCooledDown(player))) return;
						ProjectileUtil.launchBullet(player, g);
						ProjectileUtil.setWeaponCooldown(player, g, true);
						statsUtil.registerWeaponUsed(player, w);
						w.playFiringSound(player);
					}
					
					ProjectileUtil.setFiringWeapon(player, g, true);
				}
			} else if (w instanceof Grenade) {
				if (!(ProjectileUtil.isWeaponCooledDown(player))) return;
				
				Grenade g = (Grenade) w;
				Item i = ProjectileUtil.launchGrenade(player, g);
				ProjectileUtil.setWeaponCooldown(player, g, true);
				statsUtil.registerWeaponUsed(player, w);
				g.playFiringSound(player);
				
				GrenadeExploder ge = new GrenadeExploder(g, i, statsUtil);
				Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), ge, g.getFuseLenght());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickUp(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
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
				
				statsUtil.registerPlayerKilled(shooter, corpse, weaponname, hs);
			} else if (projectileHit.getDamager().getType() == EntityType.DROPPED_ITEM) {
				Item item = (Item) projectileHit.getDamager();
				Player shooter = (Player) item.getMetadata("shooter").get(0).value();
				Player corpse = event.getEntity();
				String weaponname = item.getMetadata("weaponname").get(0).asString();
				
				sb.append(ChatColor.GREEN).append(shooter.getName()).append(ChatColor.WHITE);
				sb.append(" [").append(weaponname).append("] ");
				sb.append(ChatColor.RED).append(corpse.getName());
				
				statsUtil.registerPlayerKilled(shooter, corpse, weaponname, false);
			}
			break;
		case ENTITY_ATTACK:
			// TODO: Filter out punching with weapons.
			EntityDamageByEntityEvent entityDamage = (EntityDamageByEntityEvent) lastDmg;
			if (entityDamage.getDamager() == null || entityDamage.getDamager().getType() != EntityType.PLAYER) {
				// Death caused by a vanilla mob, don't alter
				return;
			}
			Player damager = (Player) entityDamage.getDamager();
			Player victim = event.getEntity();
			
			sb.append(ChatColor.GREEN).append(damager.getName());
			sb.append(ChatColor.WHITE).append(" [KNIFE] ");
			sb.append(ChatColor.RED).append(victim.getName());
			
			statsUtil.registerPlayerKilled(damager, victim, "Knife", false);
			break;
		default:
			sb.append(ChatColor.RED).append(event.getEntity().getName());
			sb.append(ChatColor.WHITE).append(" killed himself.");
			
			statsUtil.registerPlayerKilled(event.getEntity(), event.getEntity(), "Stupidity", false);
			break;
		}
		
		event.setDeathMessage(sb.toString());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		setSpawnProtected(player, true);
		
		player.closeInventory();
		player.getInventory().clear();
		
		// Subject to change
		player.getInventory().setItem(0, new MP7().getItemStack());
		player.getInventory().setItem(1, new DesertEagle().getItemStack());
		player.getInventory().setItem(2, null); // TODO: Knife
		player.getInventory().setItem(3, new HighExplosiveGrenade().getItemStack());
		player.getInventory().setItem(4, new Nova().getItemStack());
		
		player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
		
		player.getInventory().setHeldItemSlot(1);
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
		
		double damage = proj.getMetadata("damage").get(0).asDouble();
		boolean piercing = proj.getMetadata("piercing").get(0).asBoolean();
		
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			
			if (isSpawnProtected(player)) {
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
				entity.damage(0, proj.getShooter());
				entity.setNoDamageTicks(0);
			}
			entity.setLastDamageCause(event);
		}
		entity.damage(damage);
		
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if (from.getBlockX() != to.getBlockX() 
				|| from.getBlockY() != to.getBlockY()
				|| from.getBlockZ() != to.getBlockZ()) {
			setSpawnProtected(p, false);
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
		statsUtil.addPlayer(event.getPlayer());
		// TEMP
		statsUtil.setChatColor(event.getPlayer(), ChatColor.GREEN);
	}
	
	private Weapon getWeaponFromItemStack(ItemStack i) {
		// TODO: Implement weapons.
		if (i.getType() == Material.FLINT) return new DesertEagle();
		if (i.getType() == Material.CLAY_BALL) return new HighExplosiveGrenade();
		if (i.getType() == Material.GLOWSTONE_DUST) return new Nova();
		return new MP7();
	}
	
	private boolean isSpawnProtected(Player player) {
		Long val = spawnProtection.get(player.getName());
		if (val == null) {
			return false;
		} else {
			return val > System.currentTimeMillis();
		}
	}
	
	private void setSpawnProtected(Player player, boolean value) {
		if (value) {
			spawnProtection.put(player.getName(), System.currentTimeMillis() + 20000);
			Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), new InvisibilityAdder(player), 1);
		} else {
			spawnProtection.remove(player.getName());
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}
}

class TickListener implements Runnable {
	
	KiwiListener kl;
	
	public TickListener(KiwiListener kiwiListener) {
		kl = kiwiListener;
	}
	
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
			
			kl.getStatsUtil().registerWeaponUsed(p, w);
		}
	}
	
	private Weapon getWeaponFromItemStack(ItemStack i) {
		// TODO: Implement weapons.
		if (i.getType() == Material.FLINT) return new DesertEagle();
		if (i.getType() == Material.GLOWSTONE_DUST) return new Nova();
		return new MP7();
	}
}

class SnowballRemover implements Runnable {
	
	@Override
	public void run() {
		World w = Bukkit.getWorlds().get(0);
		for (Snowball sb : w.getEntitiesByClass(Snowball.class)) {
			if (sb.getTicksLived() > 60) {
				sb.remove();
			}
		}
	}
}

class GrenadeExploder implements Runnable {
	
	Grenade g;
	Item i;
	StatsUtil su;
	
	GrenadeExploder(Grenade grenade, Item item, StatsUtil statsUtil) {
		g = grenade;
		i = item;
		su = statsUtil;
	}
	
	@Override
	public void run() {
		g.explode(i, su);
		i.remove();
	}
}

class InvisibilityAdder implements Runnable {
	
	Player p;
	
	InvisibilityAdder(Player player) {
		p = player;
	}
	
	@Override
	public void run() {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 1));
	}
}
