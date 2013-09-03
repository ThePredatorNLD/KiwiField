package me.KiwiLetsPlay.KiwiField.game;

public enum GameType {
	DEATHMATCH(false, true, false),
	FREE_FOR_ALL(false, true, false),
	ARMS_RACE(false, false, false),
	DEMOLITION(true, false, false),
	BOMB_DEFUSAL(false, true, true),
	HOSTAGE_RESCUE(false, true, true),
	CLASSICAL_BOMB_DEFUSAL(true, true, true),
	CLASSICAL_HOSTAGE_RESCUE(true, true, true);
	
	private boolean friendlyFire;
	private boolean weaponShop;
	private boolean moneySystem;
	
	private GameType(boolean friendlyFire, boolean weaponShop, boolean moneySystem) {
		this.friendlyFire = friendlyFire;
	}
	
	public boolean isFriendlyFireEnabled() {
		return friendlyFire;
	}
	
	public boolean hasMoneySystem() {
		return moneySystem;
	}
	
	public boolean hasWeaponShop() {
		return weaponShop;
	}
}
