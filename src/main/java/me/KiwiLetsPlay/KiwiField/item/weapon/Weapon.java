package me.KiwiLetsPlay.KiwiField.item.weapon;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Weapon {
	
	public String getName();
	
	public ItemStack getItemStack();
	
	public double getDamage();
	
	public int getFiringCooldown();
	
	public void playFiringSound(Player p);
}
