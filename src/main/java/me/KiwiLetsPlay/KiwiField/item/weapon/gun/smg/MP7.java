package me.KiwiLetsPlay.KiwiField.item.weapon.gun.smg;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

public class MP7 implements Gun {
	
	@Override
	public String getName() {
		return "MP7";
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(Material.SLIME_BALL, getName(), "SMG");
	}
	
	@Override
	public double getDamage() {
		return 5;
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
	public int getBackupAmmoCapacity() {
		return 120;
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
	public int getReloadTime() {
		return 3133;
	}
	
	@Override
	public boolean isAutomatic() {
		return true;
	}
	
	@Override
	public boolean isArmorPiercing() {
		return false;
	}
	
	@Override
	public int getPrice() {
		return 1700;
	}
	
	@Override
	public int getKillReward() {
		return 600;
	}
	
	@Override
	public int getInventorySlot() {
		return 0;
	}
}
