package me.KiwiLetsPlay.KiwiField.weapon.melee;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldenKnife extends Knife {
	
	@Override
	public String getName() {
		return "Golden Knife";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.COAL, 1);
		is.setDurability((short) 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		is.setItemMeta(im);
		return is;
	}
}
