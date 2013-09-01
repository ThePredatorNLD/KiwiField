package me.KiwiLetsPlay.KiwiField;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.weapon.Weapons;
import me.KiwiLetsPlay.KiwiField.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.weapon.gun.Ammunition;
import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.weapon.gun.SingleLoader;
import me.KiwiLetsPlay.KiwiField.weapon.gun.heavy.Shotgun;
import me.KiwiLetsPlay.KiwiField.weapon.melee.MeleeWeapon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public final class ProjectileUtil {
	
	private static long movementTolerance = 90;
	private static long rightClickAutomatic = 175;
	private static long rightClickSemi = 225;
	
	private static HashMap<String, Long> moving = new HashMap<String, Long>();
	private static HashMap<String, Long> jumping = new HashMap<String, Long>();
	private static HashMap<String, Long> weaponCooldown = new HashMap<String, Long>();
	private static HashMap<String, Long> weaponFiring = new HashMap<String, Long>();
	private static HashMap<String, ReloadManager> reloading = new HashMap<String, ReloadManager>();
	
	private ProjectileUtil() {}
	
	public static boolean launchBullet(Player player, Gun g) {
		if (g instanceof Shotgun) {
			return launchBullet(player, g, ((Shotgun) g).getPelletCount());
		} else {
			return launchBullet(player, g, 1);
		}
	}
	
	private static boolean launchBullet(Player player, Gun g, int count) {
		if (isReloading(player)) {
			if (g instanceof SingleLoader) {
				reloading.get(player.getName()).cancel();
			} else {
				return false;
			}
		}
		if (!(isWeaponCooledDown(player))) return false;
		if (!(Ammunition.fromItemStack(player.getItemInHand()).shoot())) {
			player.playSound(player.getLocation(), Sound.CLICK, 0.5f, 1.75f);
			setWeaponCooldown(player, g, true);
			return false;
		}
		
		Location spawn = player.getLocation().add(0, 1.525, 0);
		spawn.add(spawn.getDirection().multiply(-0.21));
		
		for (int i = 0; i < count; i++) {
			Location dirLoc = player.getLocation();
			double spray = g.getBaseRecoil();
			double sprayfactor = 0;
			if (isMoving(player)) {
				if (player.isSprinting()) {
					sprayfactor += 1;
				} else {
					sprayfactor += 0.5;
				}
			}
			if (player.isSneaking()) sprayfactor -= 0.5;
			if (isJumping(player)) sprayfactor += 1.5;
			sprayfactor *= g.getRecoilModifier();
			spray *= (sprayfactor + 1);
			
			dirLoc.setPitch((float) (dirLoc.getPitch() - (spray / 2) + Math.random() * spray));
			dirLoc.setYaw((float) (dirLoc.getYaw() - (spray / 2) + Math.random() * spray));
			
			Vector dir = dirLoc.getDirection();
			dir = dir.multiply(0.5 * g.getBulletSpeed());
			
			Projectile p = (Projectile) player.getWorld().spawnEntity(spawn, EntityType.SNOWBALL);
			p.setVelocity(dir);
			p.setShooter(player);
			p.setMetadata("weaponname", new FixedMetadataValue(KiwiField.getInstance(), g.getName()));
			p.setMetadata("damage", new FixedMetadataValue(KiwiField.getInstance(), g.getDamage()));
			p.setMetadata("piercing", new FixedMetadataValue(KiwiField.getInstance(), g.isArmorPiercing()));
			NoGravityUtil.getInstance().register(p);
		}
		
		g.playFiringSound(player);
		setWeaponCooldown(player, g, true);
		return true;
	}
	
	public static Item launchGrenade(Player player, Grenade g) {
		Location spawn = player.getLocation().add(0, 1.2, 0);
		spawn.add(spawn.getDirection().multiply(0.3));
		spawn.setPitch(0);
		spawn.add(spawn.getDirection().multiply(0.2));
		
		Vector dir = player.getLocation().getDirection();
		dir.setY(dir.getY() + 0.06);
		dir = dir.multiply(1.4);
		
		Item i = player.getWorld().dropItem(spawn, player.getItemInHand());
		player.getInventory().clear(player.getInventory().getHeldItemSlot());
		i.setPickupDelay(Integer.MAX_VALUE);
		i.setVelocity(dir);
		i.setMetadata("weaponname", new FixedMetadataValue(KiwiField.getInstance(), g.getName()));
		i.setMetadata("shooter", new FixedMetadataValue(KiwiField.getInstance(), player));
		return i;
	}
	
	public static void switchWeapon(Player player, ItemStack is) {
		Weapon w = Weapons.getWeaponByItemStack(is);
		weaponCooldown.put(player.getName(), 200L);
		if (w instanceof Gun) {
			Ammunition ammo = Ammunition.fromItemStack(is);
			player.setLevel(ammo.getBackupAmmo());
		} else {
			player.setLevel(0);
		}
	}
	
	public static void setMoving(Player player, boolean value) {
		if (value) {
			moving.put(player.getName(), System.currentTimeMillis() + movementTolerance);
		} else {
			moving.remove(player.getName());
		}
	}
	
	public static boolean isMoving(Player player) {
		Long val = moving.get(player.getName());
		if (val == null) {
			return false;
		} else {
			return val > System.currentTimeMillis();
		}
	}
	
	public static void setJumping(Player player, boolean value) {
		if (value) {
			jumping.put(player.getName(), System.currentTimeMillis() + movementTolerance);
		} else {
			jumping.remove(player.getName());
		}
	}
	
	public static boolean isJumping(Player player) {
		Long val = jumping.get(player.getName());
		if (val == null) {
			return false;
		} else {
			return val > System.currentTimeMillis();
		}
	}
	
	public static void setFiringWeapon(Player player, Gun g, boolean value) {
		if (value) {
			if (g.isAutomatic()) {
				weaponFiring.put(player.getName(), System.currentTimeMillis() + rightClickAutomatic);
			} else {
				weaponFiring.put(player.getName(), System.currentTimeMillis() + rightClickSemi);
			}
		} else {
			weaponFiring.remove(player.getName());
		}
	}
	
	public static void setUsingKnife(Player player, MeleeWeapon m, boolean secondary, boolean value) {
		if (value) {
			if (secondary) {
				weaponCooldown.put(player.getName(), System.currentTimeMillis() + m.getSecondaryCooldown());
			} else {
				weaponCooldown.put(player.getName(), System.currentTimeMillis() + m.getFiringCooldown());
			}
		} else {
			weaponCooldown.remove(player.getName());
		}
	}
	
	public static boolean isFiringWeapon(Player player) {
		Long val = weaponFiring.get(player.getName());
		if (val == null) {
			return false;
		} else {
			return val > System.currentTimeMillis();
		}
	}
	
	public static void setWeaponCooldown(Player player, Weapon w, boolean value) {
		if (value) {
			weaponCooldown.put(player.getName(), System.currentTimeMillis() + w.getFiringCooldown());
		} else {
			weaponCooldown.remove(player.getName());
		}
	}
	
	public static boolean isWeaponCooledDown(Player player) {
		Long val = weaponCooldown.get(player.getName());
		if (val == null) {
			return true;
		} else {
			return val <= System.currentTimeMillis();
		}
	}
	
	public static void startReloading(Player player) {
		if (isReloading(player)) return;
		
		Weapon w = Weapons.getWeaponByItemStack(player.getItemInHand());
		if (!(w instanceof Gun)) return;
		
		Ammunition a = Ammunition.fromItemStack(player.getItemInHand());
		if (a.getBackupAmmo() == 0) return;
		if (a.getPrimaryAmmo() == ((Gun) w).getAmmoCapacity()) return;
		
		ReloadManager rm = new ReloadManager(player);
		reloading.put(player.getName(), rm);
		Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), rm, 1);
	}
	
	public static boolean isReloading(Player player) {
		if (!reloading.containsKey(player.getName())) return false;
		return !reloading.get(player.getName()).isDone();
	}
}

class ReloadManager implements Runnable {
	
	private final Player p;
	private final ItemStack is;
	private final Gun g;
	private final int reloadTicks;
	private int waitAccumulated;
	private int waitBefore;
	private int wait;
	private boolean cancelled;
	
	ReloadManager(Player player) {
		p = player;
		is = player.getItemInHand();
		Weapon w = Weapons.getWeaponByItemStack(is);
		if (!(w instanceof Gun)) {
			throw new IllegalArgumentException("ItemStack not instanceof Gun.");
		}
		g = (Gun) w;
		wait = (int) g.getReloadTime() / 50;
		if (g instanceof SingleLoader) {
			Ammunition a = Ammunition.fromItemStack(is);
			waitBefore = (int) (((SingleLoader) g).getReloadStartTime() / 50);
			reloadTicks = waitBefore + a.getReloadableAmmo() * (((int) (g.getReloadTime() / 50)) + 1);
		} else {
			waitBefore = 0;
			reloadTicks = wait + 1;
		}
	}
	
	@Override
	public void run() {
		// Return if reloading is cancelled, or player is dead, left or selected another gun.
		if (cancelled || p == null || p.isDead() || !is.equals(p.getItemInHand())) {
			p.setExp(0f);
			waitAccumulated = reloadTicks;
			return;
		}
		
		// Keep count of how long this has been running.
		++waitAccumulated;
		
		// Single loading shotguns need time before being able to reload
		if (waitBefore > 0) {
			--waitBefore;
			p.setExp((float) waitAccumulated / reloadTicks);
			Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), this, 1);
			return;
		}
		
		if (wait > 0) {
			--wait;
			p.setExp((float) waitAccumulated / reloadTicks);
			p.setLevel(p.getLevel());
			Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), this, 1);
			return;
		}
		
		// Wait values are 0 --> Reload
		Ammunition a = Ammunition.fromItemStack(is);
		a.reload(p);
		if (g instanceof SingleLoader) {
			if (a.getPrimaryAmmo() < g.getAmmoCapacity() - 1) {
				wait = (int) (g.getReloadTime() / 50);
				Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), this, 1);
			} else {
				p.setExp(0f);
			}
		} else {
			p.setExp(0f);
		}
	}
	
	public boolean isDone() {
		return (reloadTicks == waitAccumulated);
	}
	
	public void cancel() {
		cancelled = true;
	}
}
