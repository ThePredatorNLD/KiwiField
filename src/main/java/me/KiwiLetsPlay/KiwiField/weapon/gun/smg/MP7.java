package me.KiwiLetsPlay.KiwiField.weapon.gun.smg;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;

public class MP7 implements Gun {
	
	@Override
	public String getName() {
		return "MP7";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack i = new ItemStack(Material.SLIME_BALL, 1);
		return i;
	}
	
	@Override
	public double getDamage() {
		return 6;
	}
	
	@Override
	public int getFiringCooldown() {
		return 90;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 2);
	}
	
	@Override
	public int getAmmoCapacity() {
		return 30;
	}
	
	@Override
	public double getBaseRecoil() {
		return 6;
	}
	
	@Override
	public double getRecoilModifier() {
		return 0.5;
	}
	
	@Override
	public double getBulletSpeed() {
		return 10;
	}
	
	@Override
	public boolean isAutomatic() {
		return true;
	}
	
}
