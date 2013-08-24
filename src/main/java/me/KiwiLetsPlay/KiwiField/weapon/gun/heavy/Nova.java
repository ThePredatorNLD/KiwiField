package me.KiwiLetsPlay.KiwiField.weapon.gun.heavy;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Nova implements Shotgun {
	
	@Override
	public String getName() {
		return "Nova";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.GLOWSTONE_DUST, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		is.setItemMeta(im);
		return is;
	}
	
	@Override
	public double getDamage() {
		return 3;
	}
	
	@Override
	public int getFiringCooldown() {
		return 890;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 2);
	}
	
	@Override
	public int getAmmoCapacity() {
		return 8;
	}
	
	@Override
	public int getBackupAmmoCapacity() {
		return 32;
	}
	
	@Override
	public double getBaseRecoil() {
		return 10;
	}
	
	@Override
	public double getRecoilModifier() {
		return 0.2;
	}
	
	@Override
	public int getPelletCount() {
		return 8;
	}
	
	@Override
	public double getBulletSpeed() {
		return 5;
	}
	
	@Override
	public boolean isAutomatic() {
		return false;
	}
	
	@Override
	public boolean isArmorPiercing() {
		return true;
	}
}
