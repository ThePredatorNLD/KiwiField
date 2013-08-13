package me.KiwiLetsPlay.KiwiField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class KiwiField extends JavaPlugin {
	
	private HashMap<String, UnitClass> classes = new HashMap<String, UnitClass>();
	private ScoreboardManager manager;
	private Scoreboard board;
	
	private static KiwiField plugin;
	
	public KiwiField() {
		plugin = this;
	}
	
	public void onDisable() {
		System.out.println("[KiwiField] Plugin deaktiviert");
	}
	
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new KiwiListener(this), this);
		System.out.println("[KiwiField] Plugin aktiviert");
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getMainScoreboard();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		boolean erfolg = false;
		
		if (cmd.getName().equalsIgnoreCase("kclass")) {
			if (args.length == 2) {
				Player player = this.getServer().getPlayer(args[1]);
				
				List<String> mg = new ArrayList<String>();
				mg.add(ChatColor.GRAY + "MP5");
				ItemStack machinegun = setName(new ItemStack(Material.SLIME_BALL), "Machine Gun", mg);
				List<String> gr = new ArrayList<String>();
				gr.add(ChatColor.GRAY + "M67");
				ItemStack granade = setName(new ItemStack(Material.CLAY_BALL), "Granade", gr);
				List<String> hr = new ArrayList<String>();
				hr.add(ChatColor.GRAY + "M1911");
				ItemStack handgun = setName(new ItemStack(Material.FLINT), "Handgun", hr);
				List<String> sn = new ArrayList<String>();
				sn.add(ChatColor.GRAY + "M98B");
				ItemStack sniper = setName(new ItemStack(Material.BLAZE_ROD), "Sniper", sn);
				List<String> rl = new ArrayList<String>();
				rl.add(ChatColor.GRAY + "RPG-7V2");
				ItemStack rocket = setName(new ItemStack(Material.BONE), "Rocket Launcher", rl);
				List<String> mk = new ArrayList<String>();
				mk.add(ChatColor.GRAY + "Medikit");
				ItemStack medi = setName(new ItemStack(Material.IRON_INGOT), "Medikit", mk);
				List<String> ak = new ArrayList<String>();
				ak.add(ChatColor.GRAY + "Ammokit");
				ItemStack ammo = setName(new ItemStack(Material.GOLD_INGOT), "Ammokit", mk);
				
				if (args[0].equals("assault")) {
					player.getInventory().clear();
					classes.put(player.getName(), UnitClass.ASSAULT);
					
					ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
					LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
					lam.setColor(Color.fromRGB(59, 101, 76));
					lhelmet.setItemMeta(lam);
					
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lbm = (LeatherArmorMeta) lchest.getItemMeta();
					lbm.setColor(Color.fromRGB(59, 101, 76));
					lchest.setItemMeta(lbm);
					
					ItemStack lpans = new ItemStack(Material.LEATHER_LEGGINGS, 1);
					LeatherArmorMeta lcm = (LeatherArmorMeta) lpans.getItemMeta();
					lcm.setColor(Color.fromRGB(59, 101, 76));
					lpans.setItemMeta(lcm);
					
					ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta ldm = (LeatherArmorMeta) lboots.getItemMeta();
					ldm.setColor(Color.fromRGB(59, 101, 76));
					lboots.setItemMeta(ldm);
					
					player.getInventory().setHelmet(lhelmet);
					player.getInventory().setChestplate(lchest);
					player.getInventory().setLeggings(lpans);
					player.getInventory().setBoots(lboots);
					player.getInventory().setItem(0, machinegun);
					player.getInventory().setItem(1, granade);
					player.getInventory().setItem(2, handgun);
					player.getInventory().setItem(3, medi);
				}
				
				if (args[0].equals("engineer")) {
					player.getInventory().clear();
					classes.put(player.getName(), UnitClass.ENGINEER);
					
					ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
					LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
					lam.setColor(Color.fromRGB(84, 84, 84));
					lhelmet.setItemMeta(lam);
					
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lbm = (LeatherArmorMeta) lchest.getItemMeta();
					lbm.setColor(Color.fromRGB(84, 84, 84));
					lchest.setItemMeta(lbm);
					
					ItemStack lpans = new ItemStack(Material.LEATHER_LEGGINGS, 1);
					LeatherArmorMeta lcm = (LeatherArmorMeta) lpans.getItemMeta();
					lcm.setColor(Color.fromRGB(84, 84, 84));
					lpans.setItemMeta(lcm);
					
					ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta ldm = (LeatherArmorMeta) lboots.getItemMeta();
					ldm.setColor(Color.fromRGB(84, 84, 84));
					lboots.setItemMeta(ldm);
					
					player.getInventory().setHelmet(lhelmet);
					player.getInventory().setChestplate(lchest);
					player.getInventory().setLeggings(lpans);
					player.getInventory().setBoots(lboots);
					player.getInventory().setItem(0, machinegun);
					player.getInventory().setItem(1, granade);
					player.getInventory().setItem(2, handgun);
					player.getInventory().setItem(3, rocket);
				}
				
				if (args[0].equals("supporter")) {
					player.getInventory().clear();
					classes.put(player.getName(), UnitClass.SUPPORTER);
					
					ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
					LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
					lam.setColor(Color.fromRGB(60, 50, 40));
					lhelmet.setItemMeta(lam);
					
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lbm = (LeatherArmorMeta) lchest.getItemMeta();
					lbm.setColor(Color.fromRGB(60, 50, 40));
					lchest.setItemMeta(lbm);
					
					ItemStack lpans = new ItemStack(Material.LEATHER_LEGGINGS, 1);
					LeatherArmorMeta lcm = (LeatherArmorMeta) lpans.getItemMeta();
					lcm.setColor(Color.fromRGB(60, 50, 40));
					lpans.setItemMeta(lcm);
					
					ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta ldm = (LeatherArmorMeta) lboots.getItemMeta();
					ldm.setColor(Color.fromRGB(60, 50, 40));
					lboots.setItemMeta(ldm);
					
					player.getInventory().setHelmet(lhelmet);
					player.getInventory().setChestplate(lchest);
					player.getInventory().setLeggings(lpans);
					player.getInventory().setBoots(lboots);
					player.getInventory().setItem(0, machinegun);
					player.getInventory().setItem(1, granade);
					player.getInventory().setItem(2, handgun);
					player.getInventory().setItem(3, ammo);
				}
				
				if (args[0].equals("recon")) {
					player.getInventory().clear();
					classes.put(player.getName(), UnitClass.RECON);
					
					ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
					LeatherArmorMeta lam = (LeatherArmorMeta) lhelmet.getItemMeta();
					lam.setColor(Color.fromRGB(30, 30, 30));
					lhelmet.setItemMeta(lam);
					
					ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
					LeatherArmorMeta lbm = (LeatherArmorMeta) lchest.getItemMeta();
					lbm.setColor(Color.fromRGB(30, 30, 30));
					lchest.setItemMeta(lbm);
					
					ItemStack lpans = new ItemStack(Material.LEATHER_LEGGINGS, 1);
					LeatherArmorMeta lcm = (LeatherArmorMeta) lpans.getItemMeta();
					lcm.setColor(Color.fromRGB(30, 30, 30));
					lpans.setItemMeta(lcm);
					
					ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
					LeatherArmorMeta ldm = (LeatherArmorMeta) lboots.getItemMeta();
					ldm.setColor(Color.fromRGB(30, 30, 30));
					lboots.setItemMeta(ldm);
					
					player.getInventory().setHelmet(lhelmet);
					player.getInventory().setChestplate(lchest);
					player.getInventory().setLeggings(lpans);
					player.getInventory().setBoots(lboots);
					player.getInventory().setItem(0, machinegun);
					player.getInventory().setItem(1, granade);
					player.getInventory().setItem(2, handgun);
					player.getInventory().setItem(3, sniper);
				}
			}
			erfolg = true;
		}
		
		if (cmd.getName().equalsIgnoreCase("m")) {
			if (args.length == 0) {
				if (board.getTeam("Red") != null && board.getTeam("Blue") != null) {
					Player[] players = sender.getServer().getOnlinePlayers();
					String senderteam = null;
					if (board.getTeam("Red").getPlayers().contains(sender)) {
						senderteam = "Red";
					}
					if (board.getTeam("Blue").getPlayers().contains(sender)) {
						senderteam = "Blue";
					}
					for (int p = 0; p < players.length; p++) {
						if (board.getTeam(senderteam).getPlayers().contains(players[p])) {
							if (classes.get(players[p].getName()) == UnitClass.ASSAULT) {
								if (players[p].getName() != sender.getName()) {
									players[p].sendMessage(ChatColor.BLUE + "Player " + ChatColor.WHITE
											+ sender.getName() + ChatColor.BLUE + " needs a medic!");
								}
							}
						}
					}
				}
			}
			erfolg = true;
		}
		
		if (cmd.getName().equalsIgnoreCase("a")) {
			if (args.length == 0) {
				if (board.getTeam("Red") != null && board.getTeam("Blue") != null) {
					Player[] players = sender.getServer().getOnlinePlayers();
					String senderteam = null;
					if (board.getTeam("Red").getPlayers().contains(sender)) {
						senderteam = "Red";
					}
					if (board.getTeam("Blue").getPlayers().contains(sender)) {
						senderteam = "Blue";
					}
					for (int p = 0; p < players.length; p++) {
						if (board.getTeam(senderteam).getPlayers().contains(players[p])) {
							if (classes.get(players[p].getName()) == UnitClass.ENGINEER) {
								if (players[p].getName() != sender.getName()) {
									players[p].sendMessage(ChatColor.BLUE + "Player " + ChatColor.WHITE
											+ sender.getName() + ChatColor.BLUE + " needs ammo!");
								}
							}
						}
					}
				}
			}
			erfolg = true;
		}
		
		return erfolg;
	}
	
	public static KiwiField getInstance() {
		return plugin;
	}
	
	private ItemStack setName(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
