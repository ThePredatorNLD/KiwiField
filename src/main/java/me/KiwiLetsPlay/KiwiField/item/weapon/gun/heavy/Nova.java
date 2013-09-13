package me.KiwiLetsPlay.KiwiField.item.weapon.gun.heavy;

import me.KiwiLetsPlay.KiwiField.item.weapon.gun.SingleLoader;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Nova implements Shotgun, SingleLoader {
	
	@Override
	public String getName() {
		return "Nova";
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.setAmmo(ItemFactory.getItem(Material.GLOWSTONE_DUST, getName(), "Shotgun"), this);
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
	public int getReloadStartTime() {
		return 366;
	}
	
	@Override
	public int getReloadTime() {
		return 433;
	}
	
	@Override
	public int getReloadEndTime() {
		return 800;
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
		return true;
	}
	
	@Override
	public boolean isArmorPiercing() {
		return true;
	}
	
	@Override
	public int getPrice() {
		return 1200;
	}
	
	@Override
	public int getKillReward() {
		return 900;
	}
	
	@Override
	public int getInventorySlot() {
		return 0;
	}
}
