package me.KiwiLetsPlay.KiwiField.item.weapon.gun.pistol;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

public class DesertEagle implements Gun {
	
	// GameItem
	@Override
	public String getName() {
		return "DEagle";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.PISTOL;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.FLINT, "Desert Eagle");
	}
	
	// Weapon
	@Override
	public double getDamage() {
		return 12.6;
	}
	
	@Override
	public int getFiringCooldown() {
		return 240;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.LAVA_POP, 0.5f, 0.1f);
		p.getWorld().playSound(p.getLocation(), Sound.CLICK, 0.5f, 2);
	}
	
	@Override
	public int getKillReward() {
		return 300;
	}
	
	@Override
	public int getInventorySlot() {
		return 1;
	}
	
	// Buyable
	@Override
	public int getPrice() {
		return 800;
	}
	
	// Gun
	@Override
	public int getAmmoCapacity() {
		return 7;
	}
	
	@Override
	public int getBackupAmmoCapacity() {
		return 35;
	}
	
	@Override
	public int getReloadTime() {
		return 2200;
	}
	
	@Override
	public double getBaseRecoil() {
		return 8.0;
	}
	
	@Override
	public double getRecoilModifier() {
		return 1.5;
	}
	
	@Override
	public double getBulletSpeed() {
		return 8.0;
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
