package me.KiwiLetsPlay.KiwiField.game;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.KiwiField;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Game {
	
	private GameType gameType;
	private StatsUtil statsTracker;
	
	private HashMap<String, Long> spawnProtection;
	
	public Game(Player[] players, GameType type) {
		gameType = type;
		statsTracker = new StatsUtil(players, type);
		
		spawnProtection = new HashMap<String, Long>();
	}
	
	public StatsUtil getStatsTracker() {
		return statsTracker;
	}
	
	public GameType getType() {
		return gameType;
	}
	
	public boolean isFriendlyFireEnabled() {
		return gameType.isFriendlyFireEnabled();
	}
	
	public boolean hasMoneySystem() {
		return gameType.hasMoneySystem();
	}
	
	public boolean hasWeaponShop() {
		return gameType.hasWeaponShop();
	}
	
	public boolean isSpawnProtected(Player player) {
		Long val = spawnProtection.get(player.getName());
		if (val == null) {
			return false;
		} else {
			return val > System.currentTimeMillis();
		}
	}
	
	public void setSpawnProtected(Player player, boolean value) {
		if (value) {
			spawnProtection.put(player.getName(), System.currentTimeMillis() + 20000);
			Bukkit.getScheduler().runTaskLater(KiwiField.getInstance(), new InvisibilityAdder(player), 1);
		} else {
			spawnProtection.remove(player.getName());
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}
}

class InvisibilityAdder implements Runnable {
	
	Player p;
	
	InvisibilityAdder(Player player) {
		p = player;
	}
	
	@Override
	public void run() {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 1));
	}
}
