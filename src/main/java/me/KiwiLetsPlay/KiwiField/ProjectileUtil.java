package me.KiwiLetsPlay.KiwiField;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.weapon.melee.MeleeWeapon;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
	
	private ProjectileUtil() {}
	
	public static Projectile launchBullet(Player player, Gun g) {
		Location spawn = player.getLocation().add(0, 1.2, 0);
		spawn.add(spawn.getDirection().multiply(0.3));
		spawn.setPitch(0);
		spawn.add(spawn.getDirection().multiply(0.2));
		
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
		dir.setY(dir.getY() + (0.6 / g.getBulletSpeed()));
		dir = dir.multiply(0.3 * g.getBulletSpeed());
		
		Projectile p = (Projectile) player.getWorld().spawnEntity(spawn, EntityType.SNOWBALL);
		p.setVelocity(dir);
		p.setShooter(player);
		p.setMetadata("weaponname", new FixedMetadataValue(KiwiField.getInstance(), g.getName()));
		p.setMetadata("damage", new FixedMetadataValue(KiwiField.getInstance(), g.getDamage()));
		p.setMetadata("piercing", new FixedMetadataValue(KiwiField.getInstance(), g.isArmorPiercing()));
		return p;
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
			if (secondary ) {
				weaponFiring.put(player.getName(), System.currentTimeMillis() + m.getSecondaryCooldown());
			} else {
				weaponFiring.put(player.getName(), System.currentTimeMillis() + m.getFiringCooldown());
			}
		} else {
			weaponFiring.remove(player.getName());
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
}
