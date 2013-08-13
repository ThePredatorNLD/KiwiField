package me.KiwiLetsPlay.KiwiField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.KiwiLetsPlay.KiwiField.KiwiField;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class KiwiListener implements Listener {
	
	private KiwiField plugin;
	
	public KiwiListener(KiwiField plugin) {
		this.plugin = plugin;
	}
	
	HashMap<String, Integer> waitMapA = new HashMap<String, Integer>();
	HashMap<String, Integer> waitMapB = new HashMap<String, Integer>();
	HashMap<String, Integer> waitMapC = new HashMap<String, Integer>();
	HashMap<String, Integer> waitMapD = new HashMap<String, Integer>();
	HashMap<String, Integer> waitMapE = new HashMap<String, Integer>();
	HashMap<String, Integer> waitMapF = new HashMap<String, Integer>();
	
	HashMap<String, Integer> uwMG = new HashMap<String, Integer>();
	HashMap<String, Integer> uwHG = new HashMap<String, Integer>();
	HashMap<String, Integer> uwSN = new HashMap<String, Integer>();
	HashMap<String, Integer> uwGR = new HashMap<String, Integer>();
	HashMap<String, Integer> uwRL = new HashMap<String, Integer>();
	HashMap<String, Integer> headshot = new HashMap<String, Integer>();
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final Player player = event.getPlayer();
			if (player.getItemInHand() != null) {
				if (player.getItemInHand().hasItemMeta()) {
					if (player.getItemInHand().getItemMeta().hasLore()) {
						if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "MP5")) {
							Projectile proj = player.launchProjectile(Snowball.class);
							player.getWorld().playSound(player.getLocation(), Sound.PISTON_EXTEND, 0.5f, 2);
							Vector vec = player.getLocation().getDirection();
							int speed = 10;
							proj.setVelocity(new Vector(vec.getX() * speed * 3.5, vec.getY() * speed * 3.5, vec.getZ()
									* speed * 3.5));
							proj.setVelocity(proj.getVelocity().multiply(0.1));
							proj.setMetadata("mg", new FixedMetadataValue(plugin, true));
						}
						
						if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "M1911")) {
							if (!waitMapA.containsKey(player.getName())) {
								Projectile proj = player.launchProjectile(Snowball.class);
								player.getWorld().playSound(player.getLocation(), Sound.LAVA_POP, 0.5f, 0.1f);
								player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.5f, 2);
								Vector vec = player.getLocation().getDirection();
								int speed = 10;
								proj.setVelocity(new Vector(vec.getX() * speed * 3.5, vec.getY() * speed * 3.5,
										vec.getZ() * speed * 3.5));
								proj.setVelocity(proj.getVelocity().multiply(0.1));
								proj.setMetadata("hg", new FixedMetadataValue(plugin, true));
								waitMapA.put(player.getName(), 1);
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									
									public void run() {
										waitMapA.remove(player.getName());
									}
								}, 7L);
							}
						}
						
						if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "M98B")) {
							if (!waitMapB.containsKey(player.getName())) {
								Projectile proj = player.launchProjectile(Snowball.class);
								player.getWorld().playSound(player.getLocation(), Sound.FIRE_IGNITE, 0.5f, 0.1f);
								
								Vector vec = player.getLocation().getDirection();
								int speed = 10;
								proj.setVelocity(new Vector(vec.getX() * speed * 3.5, vec.getY() * speed * 3.5,
										vec.getZ() * speed * 3.5));
								proj.setVelocity(proj.getVelocity().multiply(0.1));
								proj.setMetadata("sn", new FixedMetadataValue(plugin, true));
								waitMapB.put(player.getName(), 1);
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									
									public void run() {
										player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.5f, 2);
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											
											public void run() {
												player.getWorld().playSound(player.getLocation(), Sound.PISTON_RETRACT, 0.5f, 2);
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													
													public void run() {
														waitMapB.remove(player.getName());
													}
												}, 20L);
											}
										}, 10L);
									}
								}, 10L);
							}
						}
						
						if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "M67")) {
							if (!waitMapC.containsKey(player.getName())) {
								List<String> gr = new ArrayList<String>();
								gr.add(ChatColor.GRAY + "M67");
								ItemStack m = setName(new ItemStack(Material.CLAY_BALL), "Granade", gr);
								if (player.getInventory().contains(Material.CLAY_BALL)) {
									player.getInventory().removeItem(m);
									player.updateInventory();
									player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.2f, 20);
									waitMapC.put(player.getName(), 1);
									event.setCancelled(true);
									plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										
										public void run() {
											Projectile snowball = player.launchProjectile(Snowball.class);
											snowball.setVelocity(snowball.getVelocity().multiply(0.5));
											
											player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 0.2f, 0.1f);
											
											snowball.setMetadata("granade", new FixedMetadataValue(plugin, true));
											
											plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												
												public void run() {
													waitMapC.remove(player.getName());
												}
											}, 7L);
										}
									}, 7L);
									
								}
							}
						}
						
						if (player.getItemInHand() != null) {
							if (player.getItemInHand().hasItemMeta()) {
								if (player.getItemInHand().getItemMeta().hasLore()) {
									if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY
											+ "RPG-7V2")) {
										if (!waitMapD.containsKey(player.getName())) {
											Projectile proj = player.launchProjectile(Snowball.class);
											player.getWorld().playSound(player.getLocation(), Sound.WITHER_SHOOT, 0.5f, 1.5f);
											player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.5f, 2);
											Vector vec = player.getLocation().getDirection();
											int speed = 5;
											proj.setVelocity(new Vector(vec.getX() * speed * 3.5, vec.getY() * speed
													* 4, vec.getZ() * speed * 3.5));
											proj.setVelocity(proj.getVelocity().multiply(0.1));
											proj.setMetadata("rl", new FixedMetadataValue(plugin, true));
											waitMapD.put(player.getName(), 1);
											plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												
												public void run() {
													player.getWorld().playSound(player.getLocation(), Sound.PISTON_RETRACT, 0.5f, 1.8f);
													plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
														
														public void run() {
															player.getWorld().playSound(player.getLocation(), Sound.CLICK, 0.5f, 2);
															plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
																
																public void run() {
																	waitMapD.remove(player.getName());
																}
															}, 10L);
														}
													}, 20L);
												}
											}, 20L);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof org.bukkit.entity.Snowball) {
			final Entity snowballH = event.getEntity();
			if (snowballH.hasMetadata("granade")) {
				snowballH.getWorld().playSound(snowballH.getLocation(), Sound.DIG_STONE, 1, 2);
				final Item grsno = snowballH.getWorld().dropItem(snowballH.getLocation(), new ItemStack(
						Material.CLAY_BALL, 1));
				grsno.setMetadata("ubrot", new FixedMetadataValue(plugin, true));
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					public void run() {
						
						List<Entity> entitys = snowballH.getNearbyEntities(3, 3, 3);
						for (int i = 0; i < entitys.size(); i++) {
							final Entity entity = snowballH.getNearbyEntities(3, 3, 3).get(i);
							if (entity instanceof Player) {
								Projectile proj = (Projectile) snowballH;
								if (((Damageable) entity).getHealth() <= 15 && entity != proj.getShooter()) {
									((Damageable) entity).damage(15, proj.getShooter());
									Player player = (Player) entity;
									// Sou läuft das!
									if (uwMG.containsKey(player.getName())) {
										uwMG.remove(player.getName());
									}
									if (uwHG.containsKey(player.getName())) {
										uwHG.remove(player.getName());
									}
									if (uwSN.containsKey(player.getName())) {
										uwSN.remove(player.getName());
									}
									if (uwRL.containsKey(player.getName())) {
										uwRL.remove(player.getName());
									}
									uwGR.put(player.getName(), 1);
								} else {
									Player player = (Player) entity;
									((Damageable) entity).damage(15);
									if (uwMG.containsKey(player.getName())) {
										uwMG.remove(player.getName());
									}
									if (uwHG.containsKey(player.getName())) {
										uwHG.remove(player.getName());
									}
									if (uwSN.containsKey(player.getName())) {
										uwSN.remove(player.getName());
									}
									if (uwRL.containsKey(player.getName())) {
										uwRL.remove(player.getName());
									}
									uwGR.put(player.getName(), 1);
								}
							}
						}
						grsno.remove();
						grsno.getWorld().playSound(grsno.getLocation(), Sound.EXPLODE, 10, 1);
						grsno.getWorld().playEffect(grsno.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
						
					}
				}, 50L);
			}
			
			if (snowballH.hasMetadata("rl")) {
				snowballH.getWorld().playEffect(snowballH.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
				snowballH.getWorld().playSound(snowballH.getLocation(), Sound.EXPLODE, 10, 1);
				List<Entity> entitys = snowballH.getNearbyEntities(3, 3, 3);
				for (int i = 0; i < entitys.size(); i++) {
					final Entity entity = snowballH.getNearbyEntities(3, 3, 3).get(i);
					if (entity instanceof Player) {
						Projectile proj = (Projectile) snowballH;
						if (((Damageable) entity).getHealth() <= 6 && entity != proj.getShooter()) {
							((Damageable) entity).damage(6, proj.getShooter());
							Player player = (Player) entity;
							if (uwMG.containsKey(player.getName())) {
								uwMG.remove(player.getName());
							}
							if (uwHG.containsKey(player.getName())) {
								uwHG.remove(player.getName());
							}
							if (uwSN.containsKey(player.getName())) {
								uwSN.remove(player.getName());
							}
							if (uwGR.containsKey(player.getName())) {
								uwGR.remove(player.getName());
							}
							uwRL.put(player.getName(), 1);
						} else {
							((Damageable) entity).damage(6);
							Player player = (Player) entity;
							if (uwMG.containsKey(player.getName())) {
								uwMG.remove(player.getName());
							}
							if (uwHG.containsKey(player.getName())) {
								uwHG.remove(player.getName());
							}
							if (uwSN.containsKey(player.getName())) {
								uwSN.remove(player.getName());
							}
							if (uwGR.containsKey(player.getName())) {
								uwGR.remove(player.getName());
							}
							uwRL.put(player.getName(), 1);
						}
					}
				}
			}
			
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickUp(PlayerPickupItemEvent event) {
		if (event.getItem().hasMetadata("ubrot") || event.getItem().hasMetadata("medikit")
				|| event.getItem().hasMetadata("ammokit")) {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerThrowMedikit(PlayerDropItemEvent event) {
		
		if (event.getItemDrop().getItemStack().getType() == Material.SLIME_BALL) {
			event.setCancelled(true);
		}
		if (event.getItemDrop().getItemStack().getType() == Material.CLAY_BALL) {
			event.setCancelled(true);
		}
		if (event.getItemDrop().getItemStack().getType() == Material.FLINT) {
			event.setCancelled(true);
		}
		if (event.getItemDrop().getItemStack().getType() == Material.BLAZE_ROD) {
			event.setCancelled(true);
		}
		if (event.getItemDrop().getItemStack().getType() == Material.BONE) {
			event.setCancelled(true);
		}
		
		if (event.getItemDrop().getItemStack().getType() == Material.IRON_INGOT) {
			if (!waitMapE.containsKey(event.getPlayer().getName())) {
				event.getItemDrop().setMetadata("medikit", new FixedMetadataValue(plugin, true));
				final Item medikit = event.getItemDrop();
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 0.5f, 2);
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.DIG_GRAVEL, 0.5f, 2);
				
				waitMapE.put(event.getPlayer().getName(), 1);
				
				List<Entity> entitys = medikit.getNearbyEntities(3, 3, 3);
				for (int i = 0; i < entitys.size(); i++) {
					final Entity entity = medikit.getNearbyEntities(3, 3, 3).get(i);
					if (entity instanceof Player) {
						((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
						((Player) entity).setFoodLevel(20);
					}
				}
				
				final Player playerM = event.getPlayer();
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					public void run() {
						medikit.remove();
						waitMapE.remove(playerM.getName());
					}
				}, 100L);
				
				List<String> mk = new ArrayList<String>();
				mk.add(ChatColor.GRAY + "Medikit");
				ItemStack medi = setName(new ItemStack(Material.IRON_INGOT), "Medikit", mk);
				
				event.getPlayer().getInventory().setItem(3, medi);
				event.getPlayer().updateInventory();
			} else {
				event.setCancelled(true);
			}
		}
		
		if (event.getItemDrop().getItemStack().getType() == Material.GOLD_INGOT) {
			if (!waitMapF.containsKey(event.getPlayer().getName())) {
				event.getItemDrop().setMetadata("ammokit", new FixedMetadataValue(plugin, true));
				final Item medikit = event.getItemDrop();
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 0.5f, 2);
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.DIG_WOOD, 0.5f, 1);
				
				waitMapF.put(event.getPlayer().getName(), 1);
				
				List<Entity> entitys = medikit.getNearbyEntities(3, 3, 3);
				for (int i = 0; i < entitys.size(); i++) {
					final Entity entity = medikit.getNearbyEntities(3, 3, 3).get(i);
					if (entity instanceof Player) {
						Player player = (Player) entity;
						List<String> gr = new ArrayList<String>();
						gr.add(ChatColor.GRAY + "M67");
						ItemStack granade = setName(new ItemStack(Material.CLAY_BALL), "Granade", gr);
						player.getInventory().setItem(1, granade);
						if (player.getInventory().getHelmet() != null) {
							player.getInventory().getHelmet().setDurability((short) 0);
						}
						if (player.getInventory().getChestplate() != null) {
							player.getInventory().getChestplate().setDurability((short) 0);
						}
						if (player.getInventory().getLeggings() != null) {
							player.getInventory().getLeggings().setDurability((short) 0);
						}
						if (player.getInventory().getBoots() != null) {
							player.getInventory().getBoots().setDurability((short) 0);
						}
					}
				}
				
				final Player playerM = event.getPlayer();
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					public void run() {
						medikit.remove();
						waitMapF.remove(playerM.getName());
					}
				}, 100L);
				List<String> ak = new ArrayList<String>();
				ak.add(ChatColor.GRAY + "Ammokit");
				ItemStack ammo = setName(new ItemStack(Material.GOLD_INGOT), "Ammokit", ak);
				
				event.getPlayer().getInventory().setItem(3, ammo);
				event.getPlayer().updateInventory();
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		boolean KillFail = false;
		
		if (event.getDeathMessage().contains("fell out of the world")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getName() + ChatColor.WHITE
					+ " fiel über Bord");
		}
		if (event.getDeathMessage().contains("died")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getName() + ChatColor.WHITE + " [SELFKILL]");
		}
		if (event.getDeathMessage().contains("using Machine Gun")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
					+ " [MP5] " + ChatColor.RED + event.getEntity().getDisplayName());
		}
		if (event.getDeathMessage().contains("using Rocket Launcher")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
					+ " [RPG-7V2] " + ChatColor.RED + event.getEntity().getDisplayName());
		}
		if (event.getDeathMessage().contains("using Handgun")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
					+ " [M1911] " + ChatColor.RED + event.getEntity().getDisplayName());
		}
		if (event.getDeathMessage().contains("using Sniper")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
					+ " [M98B] " + ChatColor.RED + event.getEntity().getDisplayName());
		}
		if (event.getDeathMessage().contains("using Granade")) {
			KillFail = true;
			event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
					+ " [M67 Granade] " + ChatColor.RED + event.getEntity().getDisplayName());
		}
		
		if (KillFail == false) {
			String heads = "";
			if (headshot.containsKey(event.getEntity().getName())) {
				heads = "<>";
			}
			
			if (uwMG.containsKey(event.getEntity().getName())) {
				event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
						+ " [MP5] " + heads + " " + ChatColor.RED + event.getEntity().getDisplayName());
				uwMG.remove(event.getEntity().getName());
				if (headshot.containsKey(event.getEntity().getName())) {
					headshot.remove(event.getEntity().getName());
				}
			}
			
			if (uwHG.containsKey(event.getEntity().getName())) {
				event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
						+ " [M1911] " + heads + " " + ChatColor.RED + event.getEntity().getDisplayName());
				uwHG.remove(event.getEntity().getName());
				if (headshot.containsKey(event.getEntity().getName())) {
					headshot.remove(event.getEntity().getName());
				}
			}
			
			if (uwSN.containsKey(event.getEntity().getName())) {
				event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
						+ " [M98B] " + heads + " " + ChatColor.RED + event.getEntity().getDisplayName());
				uwSN.remove(event.getEntity().getName());
				if (headshot.containsKey(event.getEntity().getName())) {
					headshot.remove(event.getEntity().getName());
				}
			}
			
			if (uwGR.containsKey(event.getEntity().getName())) {
				event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
						+ " [M67 Granade] " + heads + " " + ChatColor.RED + event.getEntity().getDisplayName());
				uwGR.remove(event.getEntity().getName());
			}
			
			if (uwRL.containsKey(event.getEntity().getName())) {
				event.setDeathMessage("" + ChatColor.GREEN + event.getEntity().getKiller().getName() + ChatColor.WHITE
						+ " [RPG-7V2] " + heads + " " + ChatColor.RED + event.getEntity().getDisplayName());
				uwRL.remove(event.getEntity().getName());
			}
		}
		
		List<String> gr = new ArrayList<String>();
		gr.add(ChatColor.GRAY + "M67");
		ItemStack granade = setName(new ItemStack(Material.CLAY_BALL), "Granade", gr);
		event.getEntity().getInventory().setItem(1, granade);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSnowballHit(EntityDamageByEntityEvent event) {
		if (event.getEntityType() != EntityType.PLAYER) return;
		if (event.getDamager().getType() != EntityType.SNOWBALL) return;
		
		Player player = (Player) event.getEntity();
		
		if (event.getDamager().hasMetadata("mg")) {
			Projectile proj = (Projectile) event.getDamager();
			if (((Damageable) event.getEntity()).getHealth() <= 6 && event.getEntity() != proj.getShooter()) {
				((Damageable) event.getEntity()).damage(6, proj.getShooter());
				if (uwRL.containsKey(player.getName())) {
					uwRL.remove(player.getName());
				}
				if (uwHG.containsKey(player.getName())) {
					uwHG.remove(player.getName());
				}
				if (uwSN.containsKey(player.getName())) {
					uwSN.remove(player.getName());
				}
				if (uwGR.containsKey(player.getName())) {
					uwGR.remove(player.getName());
				}
				uwMG.put(player.getName(), 1);
				if (proj.getLocation().getY() - event.getEntity().getLocation().getY() > 1.35d) {
					headshot.put(((Player) event.getEntity()).getName(), 1);
				}
			} else {
				((Damageable) event.getEntity()).damage(6);
				if (uwRL.containsKey(player.getName())) {
					uwRL.remove(player.getName());
				}
				if (uwHG.containsKey(player.getName())) {
					uwHG.remove(player.getName());
				}
				if (uwSN.containsKey(player.getName())) {
					uwSN.remove(player.getName());
				}
				if (uwGR.containsKey(player.getName())) {
					uwGR.remove(player.getName());
				}
				uwMG.put(player.getName(), 1);
			}
		}
		if (event.getDamager().hasMetadata("hg")) {
			Projectile proj = (Projectile) event.getDamager();
			if (((Damageable) event.getEntity()).getHealth() <= 4 && event.getEntity() != proj.getShooter()) {
				((Damageable) event.getEntity()).damage(4, proj.getShooter());
				if (uwRL.containsKey(player.getName())) {
					uwRL.remove(player.getName());
				}
				if (uwMG.containsKey(player.getName())) {
					uwMG.remove(player.getName());
				}
				if (uwSN.containsKey(player.getName())) {
					uwSN.remove(player.getName());
				}
				if (uwGR.containsKey(player.getName())) {
					uwGR.remove(player.getName());
				}
				uwHG.put(player.getName(), 1);
				if (proj.getLocation().getY() - event.getEntity().getLocation().getY() > 1.35d) {
					headshot.put(((Player) event.getEntity()).getName(), 1);
				}
			} else {
				((Damageable) event.getEntity()).damage(4);
				if (uwRL.containsKey(player.getName())) {
					uwRL.remove(player.getName());
				}
				if (uwMG.containsKey(player.getName())) {
					uwMG.remove(player.getName());
				}
				if (uwSN.containsKey(player.getName())) {
					uwSN.remove(player.getName());
				}
				if (uwGR.containsKey(player.getName())) {
					uwGR.remove(player.getName());
				}
				uwHG.put(player.getName(), 1);
			}
		}
		if (event.getDamager().hasMetadata("sn")) {
			Projectile proj = (Projectile) event.getDamager();
			if (((Damageable) event.getEntity()).getHealth() <= 10 && event.getEntity() != proj.getShooter()) {
				((Damageable) event.getEntity()).damage(10, proj.getShooter());
				if (uwRL.containsKey(player.getName())) {
					uwRL.remove(player.getName());
				}
				if (uwHG.containsKey(player.getName())) {
					uwHG.remove(player.getName());
				}
				if (uwMG.containsKey(player.getName())) {
					uwMG.remove(player.getName());
				}
				if (uwGR.containsKey(player.getName())) {
					uwGR.remove(player.getName());
				}
				uwSN.put(player.getName(), 1);
				if (proj.getLocation().getY() - event.getEntity().getLocation().getY() > 1.35d) {
					headshot.put(((Player) event.getEntity()).getName(), 1);
				}
			} else {
				((Damageable) event.getEntity()).damage(10);
				if (uwRL.containsKey(player.getName())) {
					uwRL.remove(player.getName());
				}
				if (uwHG.containsKey(player.getName())) {
					uwHG.remove(player.getName());
				}
				if (uwMG.containsKey(player.getName())) {
					uwMG.remove(player.getName());
				}
				if (uwGR.containsKey(player.getName())) {
					uwGR.remove(player.getName());
				}
				uwSN.put(player.getName(), 1);
			}
		}
		
	}
	
	private ItemStack setName(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
}
