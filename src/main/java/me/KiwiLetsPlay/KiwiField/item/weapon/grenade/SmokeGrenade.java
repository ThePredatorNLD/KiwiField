package me.KiwiLetsPlay.KiwiField.item.weapon.grenade;

import java.util.ArrayList;

import me.KiwiLetsPlay.KiwiField.KiwiField;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.comphenix.packetwrapper.Packet3FParticle;
import com.comphenix.packetwrapper.Packet3FParticle.ParticleEffect;
import com.comphenix.protocol.ProtocolLibrary;

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
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET.toString() + ChatColor.BOLD.toString() + "Grenade");
		im.setLore(lore);
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
	public void explode(Item i) {
		AreaSmoker as = new AreaSmoker(i.getLocation());
		Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), as, 3);
	}
	
	@Override
	public int getFuseLenght() {
		return 40;
	}
	
	@Override
	public int getPrice() {
		return 300;
	}
	
	@Override
	public int getTimesBuyable() {
		return 1;
	}
	
	@Override
	public int getKillReward() {
		return 0;
	}
	
	@Override
	public int getInventorySlot() {
		return 3;
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
//			FireworkEffectPlayer fep = new FireworkEffectPlayer();
//			FireworkEffect fe = FireworkEffect.builder().trail(true).with(Type.BALL_LARGE).withColor(Color.BLACK).build();
//			try {
//				fep.playFirework(l2.getWorld(), l2, fe);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			Vector offset = new Vector(1.8, 1.0, 1.8);
			Packet3FParticle particlePacket = new Packet3FParticle(ParticleEffect.HUGE_EXPLOSION, 5, l, offset);
			ProtocolLibrary.getProtocolManager().broadcastServerPacket(particlePacket.getHandle());
		}
	}
}
