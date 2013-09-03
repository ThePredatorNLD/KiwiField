package me.KiwiLetsPlay.KiwiField.item.equipment;

import org.bukkit.entity.Player;

import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.GameItem;

public interface Equipment extends GameItem, Buyable {
	
	public void buy(Player p);
}
