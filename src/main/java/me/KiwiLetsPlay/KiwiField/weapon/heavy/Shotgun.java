package me.KiwiLetsPlay.KiwiField.weapon.heavy;

import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;

public interface Shotgun extends Gun {
	
	public int getAmmoCapacity();
	
	public double getBaseRecoil();
	
	public double getRecoilModifier();
	
	public double getPelletCount();
	
	public double getBulletSpeed();
	
	public boolean isAutomatic();
	
	public boolean isArmorPiercing();
}
