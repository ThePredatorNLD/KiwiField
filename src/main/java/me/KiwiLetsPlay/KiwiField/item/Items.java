package me.KiwiLetsPlay.KiwiField.item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.item.equipment.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.Weapon;
import me.KiwiLetsPlay.KiwiField.item.weapon.grenade.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.heavy.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.pistol.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.gun.smg.*;
import me.KiwiLetsPlay.KiwiField.item.weapon.melee.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Items {
	
	private static final HashMap<Integer, GameItem> ids = new HashMap<Integer, GameItem>();
	private static final HashMap<String, GameItem> names = new HashMap<String, GameItem>();
	private static final List<GameItem> items = new ArrayList<GameItem>();
	
	// Pistols
	public static final DesertEagle DESERT_EAGLE = new DesertEagle();
	
	// Heavy weapons (shotguns and MGs)
	public static final Nova NOVA = new Nova();
	
	// SMGs
	public static final MP7 MP7 = new MP7();
	
	// Rifles
	
	
	// Equipment
	public static final Kevlar KEVLAR_VEST = new Kevlar();
	public static final KevlarAndHelmet KEVLAR_AND_HELMET = new KevlarAndHelmet();
	
	// Grenades
	public static final HighExplosiveGrenade HIGH_EXPLOSIVE_GRENADE = new HighExplosiveGrenade();
	public static final BlindnessGrenade BLINDNESS_GRENADE = new BlindnessGrenade();
	public static final SmokeGrenade SMOKE_GRENADE = new SmokeGrenade();
	
	// Melee
	public static final Knife KNIFE = new Knife();
	public static final GoldenKnife GOLDEN_KNIFE = new GoldenKnife();
	
	// Shop groups
	public static final Buyable[] SHOP_PISTOLS = {DESERT_EAGLE};
	public static final Buyable[] SHOP_HEAVY_WEAPONS = {NOVA};
	public static final Buyable[] SHOP_SMGS = {MP7};
	public static final Buyable[] SHOP_RIFLES = {};
	public static final Buyable[] SHOP_EQUIPMENT = {KEVLAR_VEST, KEVLAR_AND_HELMET};
	public static final Buyable[] SHOP_GRENADES = {BLINDNESS_GRENADE, HIGH_EXPLOSIVE_GRENADE, SMOKE_GRENADE};
	
	static {
		for (Field f : Items.class.getDeclaredFields()) {
			Object o;
			try {
				o = f.get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			if (o instanceof Buyable) {
				addItem((Buyable) o);
			}
		}
	}
	
	public static Weapon getWeaponByPlayer(Player player) {
		ItemStack itemStack = player.getItemInHand();
		if (itemStack == null) return null;
		GameItem i = ids.get(itemStack.getTypeId());
		if (!(i instanceof Weapon)) return null;
		return (Weapon) i;
	}
	
	public static GameItem getItemByItemStack(ItemStack itemStack) {
		if (itemStack == null) return null;
		return ids.get(itemStack.getTypeId());
	}
	
	public static GameItem getItemByName(String name) {
		if (name == null) return null;
		return names.get(name);
	}
	
	public static GameItem getItemByID(int typeID) {
		return ids.get(typeID);
	}
	
	public static Weapon[] getWeapons() {
		return items.toArray(new Weapon[0]);
	}
	
	private static void addItem(GameItem i) {
		ids.put(i.getItemStack().getTypeId(), i);
		names.put(i.getName(), i);
		items.add(i);
	}
}
