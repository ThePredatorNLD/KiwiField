package me.KiwiLetsPlay.KiwiField.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.item.weapon.grenade.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.heavy.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.pistol.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.smg.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.melee.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Items {
	
	private static final HashMap<Integer, Item> ids = new HashMap<Integer, Item>();
	private static final HashMap<String, Item> names = new HashMap<String, Item>();
	private static final List<Item> items = new ArrayList<Item>();
	
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
		addItem(DESERT_EAGLE);
		
		// Heavy weapons (shotguns and MGs)
		addItem(NOVA);
		
		// SMGs
		addItem(MP7);
		
		// Rifles
		
		
		// Grenades
		addItem(HIGH_EXPLOSIVE_GRENADE);
		addItem(BLINDNESS_GRENADE);
		addItem(SMOKE_GRENADE);
		
		// Melee
		addItem(KNIFE);
		addItem(GOLDEN_KNIFE);
	}
	
	public static Weapon getWeaponByPlayer(Player player) {
		ItemStack itemStack = player.getItemInHand();
		if (itemStack == null) return null;
		Item i = ids.get(itemStack.getTypeId());
		if (!(i instanceof Weapon)) return null;
		return (Weapon) i;
	}
	
	public static Item getItemByItemStack(ItemStack itemStack) {
		if (itemStack == null) return null;
		return ids.get(itemStack.getTypeId());
	}
	
	public static Item getItemByName(String name) {
		if (name == null) return null;
		return names.get(name);
	}
	
	public static Item getItemByID(int typeID) {
		return ids.get(typeID);
	}
	
	public static Weapon[] getWeapons() {
		return items.toArray(new Weapon[0]);
	}
	
	private static void addItem(Item i) {
		ids.put(i.getItemStack().getTypeId(), i);
		names.put(i.getName(), i);
		items.add(i);
	}
}
