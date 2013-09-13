package me.KiwiLetsPlay.KiwiField.item.weapon.gun.smg;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

public class MP7 implements Gun {
	
	// GameItem
	@Override
	public String getName() {
		return "MP7";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.SMG;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.SLIME_BALL);
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
		return 1700;
	}
	
	// Gun
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
		return 6.0;
	}
	
	@Override
	public double getRecoilModifier() {
		return 0.5;
	}
	
	@Override
	public double getBulletSpeed() {
		return 10.0;
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
}
