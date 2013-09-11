package me.KiwiLetsPlay.KiwiField.game.handler;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandlerBombDefusal implements GameHandler {
	
	private final boolean competitive;
	
	private boolean roundInProgress;
	
	public HandlerBombDefusal(boolean isCompetitive) {
		competitive = isCompetitive;
	}
	
	@Override
	public void onGameStart() {
		
	}
	
	@Override
	public void onRoundStart() {
		roundInProgress = true; 
	}
	
	@Override
	public void onRoundEnd() {
		roundInProgress = false;
		
	}
	
	@Override
	public void onPlayerKilled(Player killer, Player victim, Weapon weapon) {
		dropItems(victim);
		
		if (killer == victim) return;
		// TODO: Add Team logic
		int reward = weapon.getKillReward();
		if (!(competitive)) reward /= 2;
		KiwiField.getCurrentGame().rewardMoney(killer, reward);
	}
	
	private void dropItems(Player p) {
		if (p.getInventory().getItem(0) != null) {
			p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getItem(0));
		} else if (p.getInventory().getItem(1) != null) {
			p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getItem(1));
		}
		
		// TODO: Zeus handling?
		
		for (int i = 3; i < 6; i++) {
			ItemStack is = p.getInventory().getItem(i);
			if (is != null) {
				p.getWorld().dropItemNaturally(p.getLocation(), is);
			}
		}
	}
	
	@Override
	public void onPlayerRespawn(Player player) {
		if (roundInProgress) {
			// Spectate....
		}
	}
	
	@Override
	public boolean allowPlayerBuyItem(Player player, Buyable item) {
		return true;
	}
}
