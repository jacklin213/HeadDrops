/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadDrops extends JavaPlugin implements Listener {

	public static final String PREFIX = ChatColor.GOLD + "(HeadDrops) " + ChatColor.RESET;
	public static boolean updateAvailable = false;

	public void onEnable() {
		getCommand("head").setExecutor(new HeadCmd());

		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

		getConfig().addDefault("hostile", 5);
		getConfig().addDefault("player", 25);
		getConfig().addDefault("ironanddiamond", true);
		getConfig().addDefault("axeenabled", true);
		getConfig().addDefault("zombiedrop", true);
		getConfig().addDefault("skeletondrop", true);
		getConfig().addDefault("creeperdrop", true);
		getConfig().addDefault("playerdrop", true);
		getConfig().addDefault("checkForUpdates", true);
		getConfig().options().copyDefaults(true);
		saveConfig();

		if (getConfig().getBoolean("checkForUpdates"))
			UpdateChecker.start(this);
	}

	public void onDisable() {

	}

	public static ItemStack setSkin(ItemStack item, String nick) {
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(nick);
		item.setItemMeta(meta);
		return item;
	}
}