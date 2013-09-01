package me.KiwiLetsPlay.KiwiField.weapon.gun.smg;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.KiwiLetsPlay.KiwiField.weapon.gun.Ammunition;
import me.KiwiLetsPlay.KiwiField.weapon.gun.Gun;

public class MP7 implements Gun {
	
	@Override
	public String getName() {
		return "MP7";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.SLIME_BALL, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET.toString() + ChatColor.BOLD.toString() + "SMG");
		im.setLore(lore);
		is.setItemMeta(im);
		Ammunition.setItemMeta(is, getAmmoCapacity(), getBackupAmmoCapacity());
		return is;
	}
	
	@Override
	public double getDamage() {
		return 5;
	}
	
	@Override
	public int getFiringCooldown() {
		return 90;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.getWorld().playSound(p.getLocation(), Sound.PISTON_EXTEND, 0.5f, 2);
	}
	
	@Override
	public int getAmmoCapacity() {
		return 30;
	}
	
	@Override
	public int getBackupAmmoCapacity() {
		return 120;
	}
	
	@Override
	public double getBaseRecoil() {
		return 6;
	}
	
	@Override
	public double getRecoilModifier() {
		return 0.5;
	}
	
	@Override
	public double getBulletSpeed() {
		return 10;
	}
	
	@Override
	public int getReloadTime() {
		return 3133;
	}
	
	@Override
	public boolean isAutomatic() {
		return true;
	}
	
	@Override
	public boolean isArmorPiercing() {
		return false;
	}
}
