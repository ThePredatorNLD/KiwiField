package me.KiwiLetsPlay.KiwiField.weapon.grenade;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HighExplosiveGrenade implements Grenade {
	
	@Override
	public String getName() {
		return "HE-Grenade";
	}
	
	@Override
	public ItemStack getItemStack() {
		ItemStack is = new ItemStack(Material.CLAY_BALL, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		is.setItemMeta(im);
		return is;
	}
	
	@Override
	public double getDamage() {
		return 80;
	}
	
	@Override
	public int getFiringCooldown() {
		return 1000;
	}
	
	@Override
	public void playFiringSound(Player p) {
		// TODO
	}
	
	@Override
	public void explode(Item i) {
		if (!(i.hasMetadata("shooter"))) return;
		Player shooter = (Player) i.getMetadata("shooter").get(0).value();
		
		List<Entity> entities = i.getNearbyEntities(8, 3, 8);
		for (Entity entity : entities) {
			if (!(entity instanceof LivingEntity)) continue;
			LivingEntity le = (LivingEntity) entity;
			
			double dist = le.getLocation().distanceSquared(i.getLocation()) / 3;
			double dmg = getDamage() / (dist + 1);
			
			if (dist > 25) return;
			
			if (le instanceof Player) {
				Player p = (Player) le;
				if (p.getInventory().getHelmet() != null) {
					dmg *= 0.9;
				}
				if (p.getInventory().getChestplate() != null) {
					dmg *= 0.9;
				}
			}
			
			le.setNoDamageTicks(0);
			if (le.getHealth() <= dmg) {
				if (le != shooter) {
					le.damage(0, shooter);
					le.setNoDamageTicks(0);
				}
				EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(i, le, DamageCause.PROJECTILE, dmg);
				le.setLastDamageCause(event);
			}
			le.damage(dmg);
		}
		
		i.getWorld().playSound(i.getLocation(), Sound.EXPLODE, 0.75f, 0.1f);
		i.getWorld().playEffect(i.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
		i.getWorld().playEffect(i.getLocation(), Effect.SMOKE, 4);
	}
	
	@Override
	public long getFuseLenght() {
		return 55;
	}
}
