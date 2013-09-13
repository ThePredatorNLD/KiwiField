package me.KiwiLetsPlay.KiwiField.item.weapon.gun.heavy;

import me.KiwiLetsPlay.KiwiField.item.weapon.gun.Gun;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.SingleLoader;

public interface Shotgun extends Gun, SingleLoader {
	
	public int getPelletCount();
}
