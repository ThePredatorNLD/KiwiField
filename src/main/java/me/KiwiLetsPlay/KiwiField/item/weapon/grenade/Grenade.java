package me.KiwiLetsPlay.KiwiField.item.weapon.grenade;

import org.bukkit.entity.Item;

import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;

public interface Grenade extends Weapon, Buyable {
	
	public void explode(Item i);
	
	public int getFuseLenght();
	
	public int getTimesBuyable();
}
