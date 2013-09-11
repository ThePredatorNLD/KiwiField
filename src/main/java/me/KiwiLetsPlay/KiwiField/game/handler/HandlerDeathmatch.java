package me.KiwiLetsPlay.KiwiField.game.handler;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.shop.WeaponShop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HandlerDeathmatch implements GameHandler {
	
	private HashMap<String, Weapon> weapons;
	
	public HandlerDeathmatch() {
		weapons = new HashMap<String, Weapon>();
	}
	
	@Override
	public void onGameStart() {
		
	}
	
	@Override
	public void onRoundStart() {
		
	}
	
	@Override
	public void onRoundEnd() {
		
	}
	
	@Override
	public void onPlayerRespawn(Player player) {
		KiwiField.getCurrentGame().setSpawnProtected(player, true);
		
		PlayerInventory inv = player.getInventory();
		inv.clear();
		
		inv.setItem(1, Items.DESERT_EAGLE.getItemStack()); // TODO: Set to default team pistol.
		inv.setItem(2, Items.KNIFE.getItemStack());
		inv.setItem(8, WeaponShop.getItemStack());
		inv.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		inv.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
		
		Weapon w = weapons.get(player.getName());
		if (w == null) {
			player.getInventory().setHeldItemSlot(1);
		} else {
			inv.setItem(w.getInventorySlot(), w.getItemStack());
			player.getInventory().setHeldItemSlot(w.getInventorySlot());
		}
	}
	
	@Override
	public void onPlayerKilled(Player killer, Player victim, Weapon weapon) {
		if (killer == victim) {
			return;
		}
		KiwiField.getCurrentGame().rewardMoney(killer, 100);
	}
	
	@Override
	public boolean allowPlayerBuyItem(Player player, Buyable item) {
		if (item instanceof Gun) {
			weapons.put(player.getName(), (Weapon) item);
			return true;
		} else {
			return true; // False
		}
	}
}
