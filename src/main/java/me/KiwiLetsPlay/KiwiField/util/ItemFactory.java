package me.KiwiLetsPlay.KiwiField.util;

import java.util.ArrayList;

import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Ammunition;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemFactory {
	
	public static ItemStack getItem(Material material, String name, String type) {
		ItemStack is = new ItemStack(material, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET.toString() + ChatColor.BOLD.toString() + type);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack setAmmo(ItemStack is, Gun g) {
		Ammunition.setItemMeta(is, g.getAmmoCapacity(), g.getBackupAmmoCapacity());
		return is;
	}
}
