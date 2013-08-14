package me.KiwiLetsPlay.KiwiField;

import org.bukkit.Color;

public enum UnitClass {
	ASSAULT(59, 101, 76),
	SUPPORTER(60, 50, 40),
	RECON(30, 30, 30),
	ENGINEER(84, 84, 84);
	
	private UnitClass(int r, int g, int b) {
		c = Color.fromRGB(r, g, b);
	}
	
	private Color c;
	
	public Color getArmorColor() {
		return c;
	}
	
	public static UnitClass fromString(String s) {
		String i = s.toLowerCase();
		for (UnitClass val : UnitClass.values()) {
			if (val.name().toLowerCase().startsWith(i)) return val;
		}
		return null;
	}
}
