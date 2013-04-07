/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.dariusmewes.HeadDrops.commands.head;
import de.dariusmewes.HeadDrops.commands.headdrops;
import de.dariusmewes.HeadDrops.commands.headinfo;
import de.dariusmewes.HeadDrops.commands.myhead;

public class HeadDrops extends JavaPlugin implements Listener {

	private Metrics m;
	public static final String PREFIX = ChatColor.GOLD + "(HeadDrops) " + ChatColor.RESET;
	public static File dataFolder;
	public static boolean updateAvailable = false;

	public void onEnable() {
		getCommand("head").setExecutor(new head());
		getCommand("headdrops").setExecutor(new headdrops(this));
		getCommand("myhead").setExecutor(new myhead());
		getCommand("headinfo").setExecutor(new headinfo());

		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

		dataFolder = getDataFolder();

		// config
		FileConfiguration conf = getConfig();
		conf.addDefault("ironanddiamond", true);
		conf.addDefault("axeenabled", true);
		conf.addDefault("zombiedrop", 5);
		conf.addDefault("skeletondrop", 5);
		conf.addDefault("creeperdrop", 5);
		conf.addDefault("blazedrop", 5);
		conf.addDefault("playerdrop", 25);
		conf.addDefault("dropBlank", false);
		conf.addDefault("permissionCheckMob", false);
		conf.addDefault("permissionCheckPlayer", false);
		conf.addDefault("checkForUpdates", true);
		conf.options().copyDefaults(true);
		saveConfig();

		// stats and update checking
		try {
			m = new Metrics(this);
			m.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (conf.getBoolean("checkForUpdates"))
			UpdateChecker.start(this);
		else
			log("Update-Checking disabled! Change the config.yml to activate it.");
	}

	public void onDisable() {

	}

	public void reload() {
		this.reloadConfig();
		if (!getConfig().getBoolean("checkForUpdates") && UpdateChecker.task != null) {
			UpdateChecker.task.cancel();
			UpdateChecker.task = null;
			log("UpdateChecker stopped");
		} else if (getConfig().getBoolean("checkForUpdates") && UpdateChecker.task == null)
			UpdateChecker.start(this);
	}

	public static void log(String tx) {
		Logger.getLogger("Minecraft").info("[HeadDrops] " + tx);
	}

}