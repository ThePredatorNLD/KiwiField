package me.KiwiLetsPlay.KiwiField.item.weapon.gun.heavy;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Nova implements Shotgun {
	
	// GameItem
	@Override
	public String getName() {
		return "Nova";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.HEAVY;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.GLOWSTONE_DUST);
	}
	
	// Weapon
	@Override
	public double getDamage() {
		return 3.0;
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
	public int getKillReward() {
		return 900;
	}
	
	@Override
	public int getInventorySlot() {
		return 0;
	}
	
	// Buyable
	@Override
	public int getPrice() {
		return 1200;
	}
	
	// Gun
	@Override
	public int getAmmoCapacity() {
		return 8;
	}
	
	@Override
	public int getBackupAmmoCapacity() {
		return 32;
	}
	
	@Override
	public int getReloadTime() {
		return 433;
	}
	
	@Override
	public double getBaseRecoil() {
		return 10.0;
	}
	
	@Override
	public double getRecoilModifier() {
		return 0.2;
	}
	
	@Override
	public double getBulletSpeed() {
		return 5.0;
	}
	
	@Override
	public boolean isAutomatic() {
		return true;
	}
	
	@Override
	public boolean isArmorPiercing() {
		return true;
	}
	
	// SingleLoader
	@Override
	public int getReloadStartTime() {
		return 366;
	}
	
	@Override
	public int getReloadEndTime() {
		return 800;
	}
	
	// Shotgun
	@Override
	public int getPelletCount() {
		return 8;
	}
}
