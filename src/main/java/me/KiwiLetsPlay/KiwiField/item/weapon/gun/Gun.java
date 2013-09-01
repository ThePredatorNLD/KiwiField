package me.KiwiLetsPlay.KiwiField.item.weapon.gun;

import me.KiwiLetsPlay.KiwiField.item.Buyable;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;

public interface Gun extends Weapon, Buyable {
	
	public int getAmmoCapacity();
	
	public int getBackupAmmoCapacity();
	
	public int getReloadTime();
	
	public double getBaseRecoil();
	
	public double getRecoilModifier();
	
	public double getBulletSpeed();
	
	public boolean isAutomatic();
	
	public boolean isArmorPiercing();
}
