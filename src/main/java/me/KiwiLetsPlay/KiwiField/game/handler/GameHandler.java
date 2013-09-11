package me.KiwiLetsPlay.KiwiField.game.handler;

import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;

import org.bukkit.entity.Player;

public interface GameHandler {
	
	public void onGameStart();
	
	public void onRoundStart();
	
	public void onRoundEnd();
	
	public void onPlayerKilled(Player killer, Player victim, Weapon weapon);
	
	public void onPlayerRespawn(Player player);
	
	public boolean allowPlayerBuyItem(Player player, Buyable item);
}
