package me.KiwiLetsPlay.KiwiField.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.KiwiListener;
import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.item.equipment.Equipment;
import me.KiwiLetsPlay.KiwiField.item.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponShopListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerClickShopItem(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p == null) return;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() == null) return;
			if (e.getItem().getType() == Material.EMERALD) {
				WeaponShop.open(p);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onShopInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (p == null) return;
		if (e.getInventory() == null || !WeaponShop.getName().equals(e.getInventory().getName())) return;
		if (e.getCurrentItem() == null) return;
		
		e.setCancelled(true);
		
		if (e.getAction() == InventoryAction.NOTHING) return;
		
		Buyable b = (Buyable) Items.getItemByItemStack(e.getCurrentItem());
		if (b == null) return;
		
		int money = KiwiListener.getStatsUtil().getMoney(p);
		// TODO:
//		if (money < b.getPrice()) {
//			p.sendMessage("Insufficient funds!");
//			return;
//		}
		
		ItemStack is = b.getItemStack();
		if (b instanceof Grenade) {
			Grenade g = (Grenade) b;
			if (p.getInventory().containsAtLeast(is, g.getTimesBuyable())) {
				p.sendMessage("You already own this weapon!");
				return;
			}
			for (int i = 3; i < 6; i++) {
				ItemStack invItem = p.getInventory().getItem(i);
				if (invItem == null) {
					p.getInventory().setItem(i, is);
					break;
				} else if (invItem.getType() == is.getType()) {
					invItem.setAmount(invItem.getAmount() + 1);
					break;
				}
				if (i == 6) return;
			}
		} else if (b instanceof Gun) {
			if (p.getInventory().contains(is.getTypeId())) {
				p.sendMessage("You already own this weapon!");
				return;
			}
			int mod = e.getSlot() % 9;
			if (mod == 0) { // Pistol
				clearItemSlot(p, 1);
				p.getInventory().setItem(1, is);
				p.getInventory().setHeldItemSlot(1);
			} else if (mod == 4) { // Equipment --> Zeus
				p.getInventory().setItem(2, is);
			} else {
				clearItemSlot(p, 0);
				p.getInventory().setItem(0, is);
				p.getInventory().setHeldItemSlot(0);
			}
		} else if (b instanceof Equipment) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			items.addAll(Arrays.asList(p.getInventory().getContents()));
			items.add(p.getInventory().getChestplate());
			items.add(p.getInventory().getHelmet());
			for (ItemStack item : items) {
				if (item != null && item.getType() == is.getType() && item.getDurability() == 0) {
					p.sendMessage("You already own this item!");
					return;
				}
			}
			((Equipment) b).buy(p);
		} else {
			return;
		}
		KiwiListener.getStatsUtil().setMoney(p, money - b.getPrice());
	}
	
	private void clearItemSlot(Player p, int slot) {
		ItemStack is = p.getInventory().getItem(slot);
		if (is == null) return;
		is.setAmount(1);
		p.getWorld().dropItemNaturally(p.getLocation(), is);
		p.getInventory().clear(slot);
	}
}
