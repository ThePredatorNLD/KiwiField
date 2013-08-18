package me.KiwiLetsPlay.KiwiField.weapon.melee;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Knife implements MeleeWeapon {
	
	@Override
	public String getName() {
		return "Knife";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.COAL, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		is.setItemMeta(im);
		return is;
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
		return 500;
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
	public double getSecondaryCooldown() {
		return 1500;
	}
	
	@Override
	public void playFiringSound(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double getAttackRange() {
		return 1.5;
	}
}
