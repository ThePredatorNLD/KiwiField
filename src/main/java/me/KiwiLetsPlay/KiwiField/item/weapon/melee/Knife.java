package me.KiwiLetsPlay.KiwiField.item.weapon.melee;

import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Knife implements MeleeWeapon {
	
	@Override
	public String getName() {
		return "Knife";
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(Material.COAL, getName(), "Melee Weapon");
	}
	
	@Override
	public double getDamage() {
		return 4;
	}
	
	@Override
	public double getBackstabDamage() {
		return 18;
	}
	
	@Override
	public int getFiringCooldown() {
		return 750;
	}
	
	@Override
	public double getSecondaryDamage() {
		return 11;
	}
	
	@Override
	public double getSecondaryBackstabDamage() {
		return 36;
	}
	
	@Override
	public int getSecondaryCooldown() {
		return 1500;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.2f, 1f);
	}
	
	@Override
	public double getAttackRange() {
		return 1.5;
	}
	
	@Override
	public int getKillReward() {
		return 1500;
	}
	
	@Override
	public int getInventorySlot() {
		return 2;
	}
}
