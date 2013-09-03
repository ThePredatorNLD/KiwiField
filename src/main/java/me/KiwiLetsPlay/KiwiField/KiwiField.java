package me.KiwiLetsPlay.KiwiField;

import me.KiwiLetsPlay.KiwiField.game.Game;
import me.KiwiLetsPlay.KiwiField.game.GameType;
import me.KiwiLetsPlay.KiwiField.shop.WeaponShopListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KiwiField extends JavaPlugin {
	
	private static KiwiField plugin;
	
	public KiwiField() {
		plugin = this;
	}
	
	public static KiwiField getInstance() {
		return plugin;
	}
	
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new KiwiListener(), this);
		pm.registerEvents(new WeaponShopListener(), this);
		
		// Subject to change
		Game g = new Game(Bukkit.getOnlinePlayers(), GameType.DEATHMATCH);
		// TEMP
		for (Player p : Bukkit.getOnlinePlayers()) {
			g.getStatsTracker().setChatColor(p, ChatColor.GREEN);
		}
		
		Bukkit.getScheduler().runTaskTimer(this, new NoGravityUtil(), 1, 1);
	}
	
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
}
