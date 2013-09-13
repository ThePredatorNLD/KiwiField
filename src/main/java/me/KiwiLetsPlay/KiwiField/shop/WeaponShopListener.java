package me.KiwiLetsPlay.KiwiField.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.game.GameType;
import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.item.equipment.Equipment;
import me.KiwiLetsPlay.KiwiField.item.weapon.grenade.Grenade;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
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
		if (!(KiwiField.getCurrentGame().getHandler().allowPlayerBuyItem(p, b))) {
			p.sendMessage("Disabled in this game mode.");
			return;
		}
		
		int money = KiwiField.getCurrentGame().getStatsTracker().getMoney(p);
		if (KiwiField.getCurrentGame().hasMoneySystem() && money < b.getPrice()) {
			p.sendMessage("Insufficient funds!");
			return;
		}
		
		int lastSlot = p.getInventory().getHeldItemSlot();
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
			if (p.getInventory().contains(is.getType())) {
				p.sendMessage("You already own this weapon!");
				return;
			}
			int slot = ((Gun) b).getInventorySlot();
			if (slot < 2) { // Primary or secondary
				clearItemSlot(p, slot, KiwiField.getCurrentGame().hasMoneySystem());
				p.getInventory().setItem(slot, is);
				p.getInventory().setHeldItemSlot(slot);
			} else { // Melee
				p.getInventory().setItem(2, is);
			}
		} else if (b instanceof Equipment) {
			GameType gt = KiwiField.getCurrentGame().getType(); // TODO: Outsource to GameHandler
			if (gt != GameType.CLASSICAL_BOMB_DEFUSAL && gt != GameType.CLASSICAL_HOSTAGE_RESCUE) {
				p.sendMessage("Disabled in this game mode.");
				return;
			}
			
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
		
		if (p.getInventory().getHeldItemSlot() == lastSlot) {
			PlayerItemHeldEvent event = new PlayerItemHeldEvent(p, 8, lastSlot);
			Bukkit.getPluginManager().callEvent(event);
		}
		
		KiwiField.getCurrentGame().getStatsTracker().setMoney(p, money - b.getPrice());
	}
	
	private void clearItemSlot(Player p, int slot, boolean drop) {
		ItemStack is = p.getInventory().getItem(slot);
		if (is == null) return;
		is.setAmount(1);
		if (drop) {
			p.getWorld().dropItemNaturally(p.getLocation(), is);
		}
		p.getInventory().clear(slot);
	}
}
