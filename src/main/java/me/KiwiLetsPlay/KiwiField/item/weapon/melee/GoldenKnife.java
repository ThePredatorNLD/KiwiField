package me.KiwiLetsPlay.KiwiField.item.weapon.melee;

import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoldenKnife extends Knife {
	
	@Override
	public String getName() {
		return "Golden Knife";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = ItemFactory.getItem(Material.COAL, getName(), "Melee Weapon");
		is.setDurability((short) 1);
		return is;
	}
}
