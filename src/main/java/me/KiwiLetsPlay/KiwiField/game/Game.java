package me.KiwiLetsPlay.KiwiField.game;

import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.KiwiField;
import me.KiwiLetsPlay.KiwiField.game.handler.GameHandler;
import me.KiwiLetsPlay.KiwiField.game.handler.HandlerArmsRace;
import me.KiwiLetsPlay.KiwiField.game.handler.HandlerBombDefusal;
import me.KiwiLetsPlay.KiwiField.game.handler.HandlerDeathmatch;
import me.KiwiLetsPlay.KiwiField.game.handler.HandlerDemolition;
import me.KiwiLetsPlay.KiwiField.game.handler.HandlerFreeForAll;
import me.KiwiLetsPlay.KiwiField.game.handler.HandlerHostageRescue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Game {
	
	private final GameHandler gameHandler;
	private final GameType gameType;
	private final StatsUtil statsTracker;
	private final HashMap<String, Long> spawnProtection;
	
	public Game(Player[] players, GameType type) {
		switch (type) {
		case DEATHMATCH:
			gameHandler = new HandlerDeathmatch();
			break;
		case FREE_FOR_ALL:
			gameHandler = new HandlerFreeForAll();
			break;
		case ARMS_RACE:
			gameHandler = new HandlerArmsRace();
			break;
		case DEMOLITION:
			gameHandler = new HandlerDemolition();
			break;
		case BOMB_DEFUSAL:
			gameHandler = new HandlerBombDefusal(false);
			break;
		case CLASSICAL_BOMB_DEFUSAL:
			gameHandler = new HandlerBombDefusal(true);
			break;
		case HOSTAGE_RESCUE:
			gameHandler = new HandlerHostageRescue(false);
			break;
		case CLASSICAL_HOSTAGE_RESCUE:
			gameHandler = new HandlerHostageRescue(false);
			break;
		default:
			throw new IllegalArgumentException("Illegal GameType.");
		}
		
		gameType = type;
		statsTracker = new StatsUtil(players, type);
		spawnProtection = new HashMap<String, Long>();
		
		gameHandler.onGameStart();
	}
	
	public GameHandler getHandler() {
		return gameHandler;
	}
	
	public GameType getType() {
		return gameType;
	}
	
	public StatsUtil getStatsTracker() {
		return statsTracker;
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
	
	public int getMoney(Player player) {
		return statsTracker.getMoney(player);
	}
	
	public void setMoney(Player player, int amount) {
		statsTracker.setMoney(player, amount);
	}
	
	public void rewardMoney(Player player, int amount) {
		statsTracker.setMoney(player, statsTracker.getMoney(player) + amount);
	}
	
	public void takeMoney(Player player, int amount) {
		statsTracker.setMoney(player, statsTracker.getMoney(player) - amount);
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
