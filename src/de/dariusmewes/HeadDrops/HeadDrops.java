/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.dariusmewes.HeadDrops.commands.head;

public class HeadDrops extends JavaPlugin implements Listener {

	public static final String PREFIX = ChatColor.GOLD + "(HeadDrops) " + ChatColor.RESET;
	public static boolean updateAvailable = false;

	public void onEnable() {
		getCommand("head").setExecutor(new head());

		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

		FileConfiguration conf = getConfig();

		conf.addDefault("hostile", 5);
		conf.addDefault("player", 25);
		conf.addDefault("ironanddiamond", true);
		conf.addDefault("axeenabled", true);
		conf.addDefault("zombiedrop", true);
		conf.addDefault("skeletondrop", true);
		conf.addDefault("creeperdrop", true);
		conf.addDefault("playerdrop", true);
		conf.addDefault("dropBlank", false);
		conf.addDefault("permissionCheckMob", false);
		conf.addDefault("permissionCheckPlayer", false);
		conf.addDefault("checkForUpdates", true);
		conf.options().copyDefaults(true);
		saveConfig();

		if (conf.getBoolean("checkForUpdates"))
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