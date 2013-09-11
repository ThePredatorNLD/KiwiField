package me.KiwiLetsPlay.KiwiField.game.handler;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.Items;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.shop.WeaponShop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandlerArmsRace implements GameHandler {
	
	private static final Weapon[] weapons = {Items.DESERT_EAGLE, Items.MP7, Items.NOVA, Items.GOLDEN_KNIFE}; // TODO: TEMP
	
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
	public void onPlayerKilled(Player killer, Player victim, Weapon weapon) {
		if (killer == victim) return;
		int kills = KiwiField.getCurrentGame().getStatsTracker().getKills(killer);
		if (kills == weapons.length) {
			killer.getServer().broadcastMessage(killer.getName() + " has won the game!");
			// End game
		}
	}
	
	@Override
	public void onPlayerRespawn(Player player) {
		KiwiField.getCurrentGame().setSpawnProtected(player, true);
		
		player.getInventory().clear();
		
		player.getInventory().setItem(2, Items.KNIFE.getItemStack());
		player.getInventory().setItem(8, WeaponShop.getItemStack());
		player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
		
		int kills = KiwiField.getCurrentGame().getStatsTracker().getKills(player);
		kills = Math.min(0, Math.max(weapons.length - 1, kills));
		Weapon w = weapons[kills];
		player.getInventory().setItem(w.getInventorySlot(), w.getItemStack());
		
		player.getInventory().setHeldItemSlot(w.getInventorySlot());
	}
	
	@Override
	public boolean allowPlayerBuyItem(Player player, Buyable item) {
		return false;
	}
}
