package me.KiwiLetsPlay.KiwiField.game;

public enum GameType {
	DEATHMATCH(false, true, false, false),
	FREE_FOR_ALL(false, true, false, false),
	ARMS_RACE(false, false, false, true),
	DEMOLITION(true, false, false, true),
	BOMB_DEFUSAL(false, true, true, true),
	HOSTAGE_RESCUE(false, true, true, true),
	CLASSICAL_BOMB_DEFUSAL(true, true, true, true),
	CLASSICAL_HOSTAGE_RESCUE(true, true, true, true);
	
	private boolean friendlyFire;
	private boolean weaponShop;
	private boolean moneySystem;
	private boolean useSecondaryAmmo;
	
	private GameType(boolean friendlyFire, boolean weaponShop, boolean moneySystem, boolean useSecondaryAmmo) {
		this.friendlyFire = friendlyFire;
		this.weaponShop = weaponShop;
		this.moneySystem = moneySystem;
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
	
	public boolean useSecondaryAmmo() {
		return useSecondaryAmmo;
	}
}
