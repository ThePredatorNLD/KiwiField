package me.KiwiLetsPlay.KiwiField.item.weapon.grenade;

import java.util.ArrayList;

import me.KiwiLetsPlay.KiwiField.KiwiField;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BlindnessGrenade implements Grenade {
	
	@Override
	public String getName() {
		return "Blindness Grenade";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.BLAZE_ROD, 1);
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
		return 0;
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
		for (Entity e : i.getNearbyEntities(25, 15, 25)) {
			if (!(e instanceof LivingEntity)) continue;
			LivingEntity le = (LivingEntity) e;
			
			if (le instanceof Player) {
				if (KiwiField.getCurrentGame().isSpawnProtected((Player) le)) continue;
			}
			
			if (!le.hasLineOfSight(i)) {
				if (le.getLocation().distanceSquared(i.getLocation()) < 25) {
					le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1));
				}
				continue;
			}
			
			Vector d1 = le.getLocation().getDirection();
			double dx = i.getLocation().getX() - le.getLocation().getX();
			double dy = i.getLocation().getY() - le.getLocation().getY() - le.getEyeHeight();
			double dz = i.getLocation().getZ() - le.getLocation().getZ();
			Vector d2 = new Vector(dx, dy, dz).normalize();
			
			double arc = d1.dot(d2);
			if (arc > 0.6) {
				double dist = Math.max(1, i.getLocation().distance(le.getLocation()) / 5);
				double time = 200 * arc / dist;
				le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) time, 1));
			} else if (le.getLocation().distanceSquared(i.getLocation()) < 100) {
				le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1));
			}
		}
	}
	
	@Override
	public int getFuseLenght() {
		return 40;
	}
	
	@Override
	public int getPrice() {
		return 200;
	}
	
	@Override
	public int getTimesBuyable() {
		return 2;
	}
	
	@Override
	public int getKillReward() {
		return 0;
	}
	
	@Override
	public int getInventorySlot() {
		return 3;
	}
}
