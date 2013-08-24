package me.KiwiLetsPlay.KiwiField.weapon.grenade;

import org.bukkit.entity.Item;

import me.KiwiLetsPlay.KiwiField.KiwiListener;
import me.KiwiLetsPlay.KiwiField.weapon.Weapon;

public interface Grenade extends Weapon {
	
	public void explode(Item i, KiwiListener kl);
	
	public long getFuseLenght();
}
