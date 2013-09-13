package me.KiwiLetsPlay.KiwiField.shop;

import java.util.List;

import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class WeaponShop {
	
	private static final String prefix = ChatColor.RESET.toString() + ChatColor.GOLD.toString() + "$ ";
	
	private WeaponShop() {}
	
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, getName());
		addItems(inv, 0, Items.SHOP_PISTOLS);
		addItems(inv, 1, Items.SHOP_SMGS);
		addItems(inv, 2, Items.SHOP_HEAVY_WEAPONS);
		addItems(inv, 3, Items.SHOP_RIFLES);
		addItems(inv, 4, Items.SHOP_EQUIPMENT);
		addItems(inv, 5, Items.SHOP_GRENADES);
		p.openInventory(inv);
	}
	
	public static String getName() {
		return "Weapon Shop";
	}
	
	public static ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.EMERALD);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.BOLD + getName());
		is.setItemMeta(im);
		return is;
	}
	
	private static void addItems(Inventory inv, int offset, Buyable[] items) {
		for (int i = 0; i < items.length; i++) {
			ItemStack is = items[i].getItemStack();
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			lore.add("");
			lore.add(prefix + String.valueOf(items[i].getPrice()));
			im.setLore(lore);
			is.setItemMeta(im);
			inv.setItem(offset + 9 * i, is);
		}
	}
}
