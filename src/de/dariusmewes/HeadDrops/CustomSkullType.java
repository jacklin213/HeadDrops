/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

public enum CustomSkullType {

	BLAZE("_Luna00_", "Blaze Head"), SPIDER("Kelevra_V", "Spider Head"), ENDERMAN("Violit", "Enderman Head");

	private String skin;
	private String displayName;

	private CustomSkullType(String skin, String displayName) {
		this.skin = skin;
		this.displayName = displayName;
	}

	public String getSkinName() {
		return this.skin;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public static CustomSkullType forSkinName(String skinName) {
		for (CustomSkullType t : values())
			if (t.getSkinName().equalsIgnoreCase(skinName))
				return t;

		return null;
	}

}