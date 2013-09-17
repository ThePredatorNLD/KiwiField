package me.KiwiLetsPlay.KiwiField.item.weapon.gun.rifle;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

public class AWP implements Gun {
	
	// GameItem
	@Override
	public String getName() {
		return "AWP";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.RIFLE;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.BONE);
	}
	
	// Weapon
	@Override
	public double getDamage() {
		return 5.0;
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
	public int getKillReward() {
		return 600;
	}
	
	@Override
	public int getInventorySlot() {
		return 0;
	}
	
	// Buyable
	@Override
	public int getPrice() {
		return 4750;
	}
	
	// Gun
	@Override
	public int getAmmoCapacity() {
		return 10;
	}
	
	@Override
	public int getBackupAmmoCapacity() {
		return 30;
	}
	
	@Override
	public double getBaseRecoil() {
		return 3.0;
	}
	
	@Override
	public double getRecoilModifier() {
		return 0.5;
	}
	
	@Override
	public double getBulletSpeed() {
		return 15.0;
	}
	
	@Override
	public int getReloadTime() {
		return 3800;
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
