package me.KiwiLetsPlay.KiwiField.item.weapon.grenade;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.item.ItemType;
import me.KiwiLetsPlay.KiwiField.util.ItemFactory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
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
		new AreaSmoker(i.getLocation());
	}
	
	@Override
	public int getFuseLenght() {
		return 40;
	}
	
	@Override
	public int getTimesBuyable() {
		return 1;
	}
	
	private class AreaSmoker extends BukkitRunnable {
		
		private Location l;
		private int life;
		
		AreaSmoker(Location loc) {
			l = loc;
			runTaskTimer(KiwiField.getInstance(), 1, 3);
		}
		
		@Override
		public void run() {
			++life;
			if (life > 100) {
				cancel();
				return;
			}
			
			Vector offset = new Vector(1.8, 1.0, 1.8);
			Packet3FParticle particlePacket = new Packet3FParticle(ParticleEffect.HUGE_EXPLOSION, 5, l, offset);
			ProtocolLibrary.getProtocolManager().broadcastServerPacket(particlePacket.getHandle());
		}
	}
}
