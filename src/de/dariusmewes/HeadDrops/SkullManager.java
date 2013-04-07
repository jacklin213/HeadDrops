/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public final class SkullManager {

	private SkullManager() {

	}

	public static ItemStack getCustomSkull(CustomSkullType t) {
		final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(t.getSkinName());
		meta.setDisplayName(ChatColor.RESET + t.getDisplayName());
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getSkinnedHead(String nick) {
		final ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		final SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(nick);
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