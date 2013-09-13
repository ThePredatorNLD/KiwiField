package me.KiwiLetsPlay.KiwiField.item.equipment;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kevlar implements Equipment {
	
	// GameItem
	@Override
	public String getName() {
		return "Kevlar Vest";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.EQUIPMENT;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.LEATHER_CHESTPLATE);
	}
	
	// Buyable
	@Override
	public int getPrice() {
		return 650;
	}
	
	// Equipment
	@Override
	public void buy(Player p) {
		p.getInventory().setChestplate(getItemStack());
	}
}
