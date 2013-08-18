package me.KiwiLetsPlay.KiwiField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

import me.KiwiLetsPlay.KiwiField.weapon.Weapon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class StatsUtil {
	
	private final List<Player> playerList;
	private final HashMap<String, ChatColor> chatColors;
	private final HashMap<String, Integer> kills;
	private final HashMap<String, Integer> deaths;
	private final HashMap<String, Integer> money;
	
	private final List<String> battleLog;
	// FireNote: What about a client to read and display the log? Maybe a website even?
	
	private final Scoreboard board;
	private final Team invisibilityTeam;
	private final Objective killsObjective;
	private final Objective moneyObjective;
	
	public StatsUtil(Player[] players) {
		playerList = new ArrayList<Player>(Arrays.asList(players));
		chatColors = new HashMap<String, ChatColor>();
		kills = new HashMap<String, Integer>();
		deaths = new HashMap<String, Integer>();
		money = new HashMap<String, Integer>();
		
		battleLog = new LinkedList<String>();
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		invisibilityTeam = board.registerNewTeam("invisibility");
		invisibilityTeam.setCanSeeFriendlyInvisibles(true);
		// TODO: invisibilityTeam.setAllowFriendlyFire(value);
		killsObjective = board.registerNewObjective("kills", "dummy");
		killsObjective.setDisplayName(ChatColor.GREEN + "Kills");
		killsObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		moneyObjective = board.registerNewObjective("money", "dummy");
		moneyObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		
		for (Player p : players) {
			p.setScoreboard(board);
			invisibilityTeam.addPlayer(p);
		}
	}
	
	public void addPlayer(Player p) {
		p.setScoreboard(board);
		invisibilityTeam.addPlayer(p);
		playerList.add(p);
	}
	
	public void setChatColor(Player p, ChatColor color) {
		if (!(playerList.contains(p))) return;
		board.resetScores(getColorFormattedPlayer(p));
		chatColors.put(p.getName(), color);
		
		if (getKills(p) == 0) {
			// Set kills to 1 first so they will appear as 0 kills in the scoreboard.
			killsObjective.getScore(getColorFormattedPlayer(p)).setScore(1);
		}
		killsObjective.getScore(getColorFormattedPlayer(p)).setScore(getKills(p));
		moneyObjective.getScore(p).setScore(getMoney(p));
	}
	
	public void registerWeaponUsed(Player p, Weapon w) {
		registerWeaponUsed(p, w, 1);
	}
	
	public void registerWeaponUsed(Player p, Weapon w, int count) {
		log("WeaponUsed", p, null, w.getName(), count);
	}
	
	public void registerWeaponHit(Player shooter, Player victim, String weapon, double damage, boolean headshot) {
		log("WeaponHit", shooter, victim, weapon, headshot ? -damage : damage);
	}
	
	public void registerPlayerKilled(Player killer, Player victim, String weapon, boolean headshot) {
		int newKills = getKills(killer);
		if (killer == victim) {
			newKills -= 1;
		} else {
			newKills += 1;
		}
		kills.put(killer.getName(), newKills);
		int newDeaths = getDeaths(victim) + 1;
		deaths.put(victim.getName(), newDeaths);
		
		killsObjective.getScore(getColorFormattedPlayer(killer)).setScore(newKills);
		// TODO Money
		
		log("PlayerKilled", killer, victim, weapon, headshot ? 1 : 0);
	}
	
	public double getKillDeathRatio(Player p) {
		int kills = getKills(p);
		int deaths = getDeaths(p);
		if (deaths == 0) deaths = 1;
		return kills / deaths;
	}
	
	private FakePlayer getColorFormattedPlayer(Player p) {
		ChatColor cc = chatColors.containsKey(p.getName()) ? chatColors.get(p.getName()) : ChatColor.WHITE;
		String color = (cc == ChatColor.WHITE || cc == ChatColor.RESET) ? "" : cc.toString();
		String name = p.getName().substring(0, Math.min(p.getName().length(), 16 - color.length()));
		return new FakePlayer(cc + name);
	}
	
	private int getKills(Player p) {
		if (kills.containsKey(p.getName())) {
			return kills.get(p.getName());
		}
		return 0;
	}
	
	private int getDeaths(Player p) {
		if (deaths.containsKey(p.getName())) {
			return deaths.get(p.getName());
		}
		return 0;
	}
	
	private int getMoney(Player p) {
		Integer m = money.get(p);
		return m == null ? 0 : m.intValue();
	}
	
	private void log(String type, Player p1, Player p2, String w, double d) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis()).append(",");
		sb.append(type).append(",");
		sb.append(p1.getName()).append(",");
		if (p2 != null) sb.append(p2.getName());
		sb.append(",");
		sb.append(w).append(",");
		sb.append(d);
		battleLog.add(sb.toString());
	}
}
