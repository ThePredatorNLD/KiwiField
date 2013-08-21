package me.KiwiLetsPlay.KiwiField.weapon.melee;

import me.KiwiLetsPlay.KiwiField.weapon.Weapon;


public interface MeleeWeapon extends Weapon {
	
	public double getBackstabDamage();
	
	public double getSecondaryDamage();
	
	public double getSecondaryBackstabDamage();
	
	public int getSecondaryCooldown();
	
	public double getAttackRange();
	
}
