package me.KiwiLetsPlay.KiwiField.item.weapon;

import me.KiwiLetsPlay.KiwiField.item.GameItem;

import org.bukkit.entity.Player;

public interface Weapon extends GameItem {
	
	public double getDamage();
	
	public int getFiringCooldown();
	
	public void playFiringSound(Player p);
}
