package me.KiwiLetsPlay.KiwiField.item.equipment;

import java.util.ArrayList;

import me.KiwiLetsPlay.KiwiField.item.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KevlarAndHelmet implements Equipment {
	
	@Override
	public String getName() {
		return "Kevlar & Helmet";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.LEATHER_HELMET, 1);
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
		ItemStack is = getItemStack();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Helmet");
		is.setItemMeta(im);
		p.getInventory().setHelmet(is);
		Items.KEVLAR_VEST.buy(p);
	}
	
	@Override
	public int getPrice() {
		return 1000;
	}
}
