package me.KiwiLetsPlay.KiwiField.item.weapon.gun;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.KiwiLetsPlay.KiwiField.item.Items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Ammunition {
	
	private final ItemStack itemStack;
	private final int primary;
	private final int secondary;
	
	public Ammunition(ItemStack is) {
		itemStack = is;
		Gun g = (Gun) Items.getWeaponByItemStack(is);
		primary = g.getAmmoCapacity();
		secondary = g.getBackupAmmoCapacity();
	}
	
	private Ammunition(ItemStack is, int p, int s) {
		itemStack = is;
		primary = p;
		secondary = s;
	}
	
	public int getPrimaryAmmo() {
		return primary;
	}
	
	public int getBackupAmmo() {
		return secondary;
	}
	
	public boolean shoot() {
		if (primary > 0) {
			// TODO: Set durability
			setItemMeta(itemStack, primary - 1, secondary);
			return true;
		}
		return false;
	}
	
	public void reload(Player p) {
		Gun g = (Gun) Items.getWeaponByItemStack(itemStack);
		int loadable = getReloadableAmmo();
		if (g instanceof SingleLoader) {
			loadable = Math.min(loadable, 1);
		}
		p.setLevel(secondary - loadable);
		setItemMeta(itemStack, primary + loadable, secondary - loadable);
		p.setItemInHand(itemStack);
		// TODO: Set durability
	}
	
	public int getReloadableAmmo() {
		Gun g = (Gun) Items.getWeaponByItemStack(itemStack);
		return Math.min(secondary, g.getAmmoCapacity() - primary);
	}
	
	public static Ammunition fromItemStack(ItemStack is) {
		String lore = is.getItemMeta().getLore().get(1);
		
		Matcher m = Pattern.compile(".*: ([0-9]+) / ([0-9]+)").matcher(lore);
		if (m.matches()) {
			int p = Integer.valueOf(m.group(1));
			int s = Integer.valueOf(m.group(2));
			return new Ammunition(is, p, s);
		}
		return null;
	}
	
	public static void setItemMeta(ItemStack itemStack, int primary, int secondary) {
		itemStack.setAmount(Math.max(1, primary));
		// TODO: Set durability.
		StringBuilder lore = new StringBuilder();
		ItemMeta meta = itemStack.getItemMeta();
		lore.append("Ammo: ").append(primary).append(" / ").append(secondary);
		List<String> lores = meta.getLore();
		if (lores.size() > 1) {
			lores.set(1, lore.toString());
		} else {
			lores.add(1, lore.toString());
		}
		meta.setLore(lores);
		itemStack.setItemMeta(meta);
	}
}
