package me.KiwiLetsPlay.KiwiField.item.equipment;

import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kevlar implements Equipment {
	
	@Override
	public String getName() {
		return "Kevlar Vest";
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(Material.LEATHER_CHESTPLATE, getName(), "Equipment");
	}
	
	@Override
	public void buy(Player p) {
		p.getInventory().setChestplate(getItemStack());
	}
	
	@Override
	public int getPrice() {
		return 650;
	}
}
