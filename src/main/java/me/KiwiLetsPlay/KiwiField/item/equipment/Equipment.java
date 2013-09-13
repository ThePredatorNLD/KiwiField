package me.KiwiLetsPlay.KiwiField.item.equipment;

import org.bukkit.entity.Player;

import me.KiwiLetsPlay.KiwiField.item.Buyable;

public interface Equipment extends Buyable {
	
	public void buy(Player p);
}
