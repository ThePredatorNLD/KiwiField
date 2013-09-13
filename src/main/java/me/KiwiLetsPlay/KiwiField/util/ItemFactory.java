package me.KiwiLetsPlay.KiwiField.util;

import java.util.ArrayList;

import me.KiwiLetsPlay.KiwiField.item.GameItem;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Ammunition;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemFactory {
	
	private ItemFactory() {}
	
	public static ItemStack getItem(GameItem item, Material material) {
		return getItem(item, material, item.getName());
	}
	
	public static ItemStack getItem(GameItem item, Material material, String name) {
		ItemStack is = new ItemStack(material, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET.toString() + ChatColor.BOLD.toString() + item.getType().toString());
		im.setLore(lore);
		is.setItemMeta(im);
		if (item instanceof Gun) {
			Gun g = (Gun) item;
			Ammunition.setItemMeta(is, g.getAmmoCapacity(), g.getBackupAmmoCapacity());
		}
		return is;
	}
}
