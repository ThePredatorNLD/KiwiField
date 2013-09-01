package me.KiwiLetsPlay.KiwiField.item.weapon;

import me.KiwiLetsPlay.KiwiField.item.Item;

import org.bukkit.entity.Player;

public interface Weapon extends Item {
	
	public double getDamage();
	
	public int getFiringCooldown();
	
	public void playFiringSound(Player p);
}
