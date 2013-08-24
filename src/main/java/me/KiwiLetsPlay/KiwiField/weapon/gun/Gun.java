package me.KiwiLetsPlay.KiwiField.weapon.gun;

import me.KiwiLetsPlay.KiwiField.weapon.Weapon;

public interface Gun extends Weapon {
	
	public int getAmmoCapacity();
	
	public int getBackupAmmoCapacity();
	
	public double getBaseRecoil();
	
	public double getRecoilModifier();
	
	public double getBulletSpeed();
	
	public boolean isAutomatic();
	
	public boolean isArmorPiercing();
}
