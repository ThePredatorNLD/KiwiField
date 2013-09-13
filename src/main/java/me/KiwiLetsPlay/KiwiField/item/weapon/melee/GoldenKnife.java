package me.KiwiLetsPlay.KiwiField.item.weapon.melee;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoldenKnife extends Knife {
	
	// GameItem
	@Override
	public String getName() {
		return "Golden Knife";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.MELEE;
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = ItemFactory.getItem(this, Material.COAL);
		is.setDurability((short) 1);
		return is;
	}
}
