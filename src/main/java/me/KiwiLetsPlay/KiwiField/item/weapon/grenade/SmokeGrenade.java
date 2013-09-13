package me.KiwiLetsPlay.KiwiField.item.weapon.grenade;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.comphenix.packetwrapper.Packet3FParticle;
import com.comphenix.packetwrapper.Packet3FParticle.ParticleEffect;
import com.comphenix.protocol.ProtocolLibrary;

public class SmokeGrenade implements Grenade {
	
	// GameItem
	@Override
	public String getName() {
		return "Smoke Grenade";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.GRENADE;
	}
	
	@Override
	public ItemStack getItemStack() {
		return ItemFactory.getItem(this, Material.STICK);
	}
	
	// Weapon
	@Override
	public double getDamage() {
		return 0.0;
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
	public int getKillReward() {
		return 0;
	}
	
	@Override
	public int getInventorySlot() {
		return 3;
	}
	
	// Buyable
	@Override
	public int getPrice() {
		return 300;
	}
	
	// Grenade
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
	public int getTimesBuyable() {
		return 1;
	}
	
	private class AreaSmoker implements Runnable {
		
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
				
				Vector offset = new Vector(1.8, 1.0, 1.8);
				Packet3FParticle particlePacket = new Packet3FParticle(ParticleEffect.HUGE_EXPLOSION, 5, l, offset);
				ProtocolLibrary.getProtocolManager().broadcastServerPacket(particlePacket.getHandle());
			}
		}
	}
}
