package me.KiwiLetsPlay.KiwiField.item.equipment;

import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

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
		return ItemFactory.getItem(Material.LEATHER_HELMET, "Helmet", "Equipment");
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
