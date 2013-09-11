package me.KiwiLetsPlay.KiwiField.item.weapon.grenade;

import java.util.ArrayList;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.KiwiField;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HighExplosiveGrenade implements Grenade {
	
	@Override
	public String getName() {
		return "HE-Grenade";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.CLAY_BALL, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET.toString() + ChatColor.BOLD.toString() + "Grenade");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	@Override
	public double getDamage() {
		return 100;
	}
	
	@Override
	public int getFiringCooldown() {
		return 1000;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1f, 1f);
	}
	
	@Override
	public void explode(Item i) {
		if (!(i.hasMetadata("shooter"))) return;
		Player shooter = (Player) i.getMetadata("shooter").get(0).value();
		Location loc = i.getLocation();
		
		List<Entity> entities = i.getNearbyEntities(8, 3, 8);
		for (Entity entity : entities) {
			if (!(entity instanceof LivingEntity)) continue;
			LivingEntity le = (LivingEntity) entity;
			
			double dist = le.getLocation().distanceSquared(loc) / 3;
			double dmg = getDamage() / (dist + 1);
			
			if (!(le.hasLineOfSight(i))) dmg *= 0.25;
			if (dist > 25) return;
			
			if (le instanceof Player) {
				Player p = (Player) le;
				
				if (KiwiField.getCurrentGame().isSpawnProtected(p)) continue;
				
				if (p.getInventory().getHelmet() != null) {
					dmg *= 0.9;
				}
				if (p.getInventory().getChestplate() != null) {
					dmg *= 0.9;
				}
				
				KiwiField.getCurrentGame().getStatsTracker().registerWeaponHit(shooter, p, getName(), dmg, false);
			}
			
			le.setNoDamageTicks(0);
			if (le.getHealth() <= dmg) {
				if (le != shooter) {
					le.damage(0, shooter);
					le.setNoDamageTicks(0);
				}
				EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(i, le, DamageCause.PROJECTILE, dmg);
				le.setLastDamageCause(event);
			}
			le.damage(dmg);
		}
		
		i.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 5);
		explosionEffect(i.getLocation(), 0, 0, 0);
		for (int c = 0; c < 2; c++) {
			explosionEffect(i.getLocation(), Math.random() * 2 - 1, Math.random(), Math.random() * 2 - 1);
		}
	}
	
	private void explosionEffect(Location loc, double x, double y, double z) {
		loc.getWorld().createExplosion(loc.getX() + x, loc.getY() + y, loc.getZ() + z, 0, false, false);
	}
	
	@Override
	public int getFuseLenght() {
		return 40;
	}
	
	@Override
	public int getPrice() {
		return 300;
	}
	
	@Override
	public int getTimesBuyable() {
		return 1;
	}
	
	@Override
	public int getKillReward() {
		return 200;
	}
	
	@Override
	public int getInventorySlot() {
		return 3;
	}
}
