package me.KiwiLetsPlay.KiwiField;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class NoGravityUtil implements Runnable {
	
	private static final int LIFETIME = 30;
	
	private static HashMap<Projectile, Vector> velocities;
	private static NoGravityUtil instance;
	
	public NoGravityUtil() {
		velocities = new HashMap<Projectile, Vector>();
		instance = this;
	}
	
	public static NoGravityUtil getInstance() {
		return instance;
	}
	
	@Override
	public void run() {
		Iterator<Projectile> i = velocities.keySet().iterator();
		while (i.hasNext()) {
			Projectile p = i.next();
			if (p.getTicksLived() > LIFETIME) {
				i.remove();
				p.remove();
				continue;
			}
			p.setVelocity(velocities.get(p));
		}
	}
	
	public void register(Projectile p) {
		velocities.put(p, p.getVelocity());
	}
}
