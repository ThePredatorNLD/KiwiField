package me.KiwiLetsPlay.KiwiField.item;

public enum ItemType {
	MELEE("Melee weapon"),
	PISTOL("Pistol"),
	HEAVY("Heavy weapon"),
	SMG("SMG"),
	RIFLE("Rifle"),
	GRENADE("Grenade"),
	EQUIPMENT("Equipment");
	
	private final String name;
	
	private ItemType(String n) {
		name = n;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
