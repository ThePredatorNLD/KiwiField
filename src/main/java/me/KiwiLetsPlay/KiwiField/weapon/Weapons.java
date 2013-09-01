package me.KiwiLetsPlay.KiwiField.weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.weapon.grenade.*;
import me.KiwiLetsPlay.KiwiField.weapon.gun.heavy.*;
import me.KiwiLetsPlay.KiwiField.weapon.gun.pistol.*;
import me.KiwiLetsPlay.KiwiField.weapon.gun.smg.*;
import me.KiwiLetsPlay.KiwiField.weapon.melee.*;

import org.bukkit.inventory.ItemStack;

public abstract class Weapons {
	
	private static final HashMap<Integer, Weapon> ids = new HashMap<Integer, Weapon>();
	private static final HashMap<String, Weapon> names = new HashMap<String, Weapon>();
	private static final List<Weapon> weapons = new ArrayList<Weapon>();
	
	// Pistols
	public static final DesertEagle DESERT_EAGLE = new DesertEagle();
	
	// Heavy weapons (shotguns and MGs)
	public static final Nova NOVA = new Nova();
	
	// SMGs
	public static final MP7 MP7 = new MP7();
	
	// Rifles
	
	
	// Grenades
	public static final HighExplosiveGrenade HIGH_EXPLOSIVE_GRENADE = new HighExplosiveGrenade();
	public static final BlindnessGrenade BLINDNESS_GRENADE = new BlindnessGrenade();
	public static final SmokeGrenade SMOKE_GRENADE = new SmokeGrenade();
	
	// Melee
	public static final Knife KNIFE = new Knife();
	public static final GoldenKnife GOLDEN_KNIFE = new GoldenKnife();
	
	static {
		// Pistols
		addWeapon(DESERT_EAGLE);
		
		// Heavy weapons (shotguns and MGs)
		addWeapon(NOVA);
		
		// SMGs
		addWeapon(MP7);
		
		// Rifles
		
		
		// Grenades
		addWeapon(HIGH_EXPLOSIVE_GRENADE);
		addWeapon(BLINDNESS_GRENADE);
		addWeapon(SMOKE_GRENADE);
		
		// Melee
		addWeapon(KNIFE);
		addWeapon(GOLDEN_KNIFE);
	}
	
	public static Weapon getWeaponByItemStack(ItemStack itemStack) {
		if (itemStack == null) return null;
		return ids.get(itemStack.getTypeId());
	}
	
	public static Weapon getWeaponByName(String name) {
		if (name == null) return null;
		return names.get(name);
	}
	
	public static Weapon getWeaponByID(int typeID) {
		return ids.get(typeID);
	}
	
	public static Weapon[] getWeapons() {
		return weapons.toArray(new Weapon[0]);
	}
	
	private static void addWeapon(Weapon w) {
		ids.put(w.getItemStack().getTypeId(), w);
		names.put(w.getName(), w);
		weapons.add(w);
	}
}
