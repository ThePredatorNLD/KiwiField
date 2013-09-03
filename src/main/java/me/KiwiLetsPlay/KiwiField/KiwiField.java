package me.KiwiLetsPlay.KiwiField;

import me.KiwiLetsPlay.KiwiField.game.Game;
import me.KiwiLetsPlay.KiwiField.game.GameType;
import me.KiwiLetsPlay.KiwiField.shop.WeaponShopListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class KiwiField extends JavaPlugin {
	
	private static KiwiField plugin;
	private static Game game;
	
	private KiwiListener kl;
	
	public KiwiField() {
		plugin = this;
	}
	
	public static KiwiField getInstance() {
		return plugin;
	}
	
	public static Game getCurrentGame() {
		return game;
	}
	
	public void onEnable() {
		// Subject to change
		game = new Game(Bukkit.getOnlinePlayers(), GameType.DEATHMATCH);
		// TEMP
		for (Player p : Bukkit.getOnlinePlayers()) {
			game.getStatsTracker().setChatColor(p, ChatColor.GREEN);
		}
		Bukkit.getScheduler().runTaskTimer(this, new NoGravityUtil(), 1, 1);
		kl = new KiwiListener();
		Bukkit.getPluginManager().registerEvents(kl, this);
		if (game.hasWeaponShop()) {
			Bukkit.getPluginManager().registerEvents(new WeaponShopListener(), this);
		}
	}
	
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	// Unused, to test flexibility of the current implementation
	public void switchGame(Game newGame) {
		// Disable everything
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		
		// Clean up map
		World w = Bukkit.getWorlds().get(0);
		for (Entity e : w.getEntitiesByClasses(Projectile.class, Animals.class, Monster.class)) {
			e.remove();
		}
		
		// Set game
		game = newGame;
		
		// Restart listeners
		Bukkit.getScheduler().runTaskTimer(this, new NoGravityUtil(), 1, 1);
		Bukkit.getPluginManager().registerEvents(kl, this);
		if (newGame.hasWeaponShop()) {
			Bukkit.getPluginManager().registerEvents(new WeaponShopListener(), this);
		}
	}
}
