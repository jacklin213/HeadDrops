/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public final class SkullManager {

	private SkullManager() {

	}

	public static ItemStack getCustomSkull(CustomSkullType type) {
		final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(type.getSkinName());
		meta.setDisplayName(ChatColor.RESET + type.getDisplayName());
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getSkinnedHead(String playerName) {
		final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playerName);
		item.setItemMeta(meta);
		return item;
	}

	public static boolean isSkullCustom(String input) {
		for (CustomSkullType t : CustomSkullType.values()) {
			if (input.equalsIgnoreCase(t.getSkinName()))
				return true;
		}
		return false;
	}

}
