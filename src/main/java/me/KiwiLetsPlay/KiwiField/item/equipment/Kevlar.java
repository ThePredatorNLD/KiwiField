package me.KiwiLetsPlay.KiwiField.item.equipment;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kevlar implements Equipment {
	
	@Override
	public String getName() {
		return "Kevlar Vest";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET.toString() + ChatColor.BOLD.toString() + "Equipment");
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
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
