package me.KiwiLetsPlay.KiwiField.item.equipment;

import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KevlarAndHelmet implements Equipment {
	
	// GameItem
	@Override
	public String getName() {
		return "Kevlar & Helmet";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.EQUIPMENT;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.LEATHER_HELMET, "Helmet");
	}
	
	// Buyable
	@Override
	public int getPrice() {
		return 1000;
	}
	
	// Equipment
	@Override
	public void buy(Player p) {
		ItemStack is = getItemStack();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Helmet");
		is.setItemMeta(im);
		p.getInventory().setHelmet(is);
		Items.KEVLAR_VEST.buy(p);
	}
}
