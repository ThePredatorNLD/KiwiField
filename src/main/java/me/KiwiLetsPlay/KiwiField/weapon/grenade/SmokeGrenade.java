package me.KiwiLetsPlay.KiwiField.weapon.grenade;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.KiwiListener;
import me.KiwiLetsPlay.KiwiField.weapon.grenade.util.FireworkEffectPlayer;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmokeGrenade implements Grenade {
	
	@Override
	public String getName() {
		return "Smoke Grenade";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.STICK, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		is.setItemMeta(im);
		return is;
	}
	
	@Override
	public double getDamage() {
		return 0;
	}
	
	@Override
	public int getFiringCooldown() {
		return 1000;
	}
	
	@Override
	public void playFiringSound(Player p) {
		p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1f, 1f);
	}
	
	@Override
	public void explode(Item i, KiwiListener kl) {
		AreaSmoker as = new AreaSmoker(i.getLocation());
		Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), as, 3);
	}
	
	@Override
	public long getFuseLenght() {
		return 40;
	}
}

class AreaSmoker implements Runnable {
	
	private Location l;
	private int life;
	
	AreaSmoker(Location loc) {
		l = loc;
	}
	
	@Override
	public void run() {
		++life;
		if (life < 90) {
			Location l2 = l.clone();
			l2.add(Math.random() * 3d - 1.5, Math.random() * 2d + 0.5d, Math.random() * 3d - 1.5);
			Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), this, 3);
			FireworkEffectPlayer fep = new FireworkEffectPlayer();
			FireworkEffect fe = FireworkEffect.builder().trail(true).with(Type.BALL_LARGE).withColor(Color.BLACK).build();
			try {
				fep.playFirework(l2.getWorld(), l2, fe);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}