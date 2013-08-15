package me.KiwiLetsPlay.KiwiField.weapon.gun.pistol;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;

public class DesertEagle implements Gun {
	
	@Override
	public String getName() {
		return "DEagle";
	}
	
	@Override
	public ItemStack getItemStack() {
		return null;
	}
	
	@Override
	public double getDamage() {
		return 8;
	}
	
	@Override
	public int getFiringCooldown() {
		return 390;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.LAVA_POP, 0.5f, 0.1f);
		p.getWorld().playSound(p.getLocation(), Sound.CLICK, 0.5f, 2);
	}
	
	@Override
	public int getAmmoCapacity() {
		return 7;
	}
	
	@Override
	public double getBaseRecoil() {
		return 9;
	}
	
	@Override
	public double getRecoilModifier() {
		return 2;
	}
	
	@Override
	public double getBulletSpeed() {
		return 8;
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
