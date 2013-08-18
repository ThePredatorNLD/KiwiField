package me.KiwiLetsPlay.KiwiField;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class FakePlayer implements OfflinePlayer {
	
	private String displayName;
	
	public FakePlayer(String name) {
		displayName = name;
	}
	
	@Override
	public boolean isOp() {
		return false;
	}
	
	@Override
	public void setOp(boolean arg0) {}
	
	@Override
	public Map<String, Object> serialize() {
		return null;
	}
	
	@Override
	public Location getBedSpawnLocation() {
		return null;
	}
	
	@Override
	public long getFirstPlayed() {
		return 0;
	}
	
	@Override
	public long getLastPlayed() {
		return 0;
	}
	
	@Override
	public String getName() {
		return displayName;
	}
	
	@Override
	public Player getPlayer() {
		return null;
	}
	
	@Override
	public boolean hasPlayedBefore() {
		return true;
	}
	
	@Override
	public boolean isBanned() {
		return false;
	}
	
	@Override
	public boolean isOnline() {
		return false;
	}
	
	@Override
	public boolean isWhitelisted() {
		return true;
	}
	
	@Override
	public void setBanned(boolean banned) {}
	
	@Override
	public void setWhitelisted(boolean value) {}
	
}
