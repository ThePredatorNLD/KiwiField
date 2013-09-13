package me.KiwiLetsPlay.KiwiField.item;

import org.bukkit.inventory.ItemStack;

public interface GameItem {
	
	public String getName();
	
	public ItemType getType();
	
	public ItemStack getItemStack();
}
