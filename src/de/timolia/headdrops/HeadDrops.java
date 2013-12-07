/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.timolia.headdrops.cmds.head;
import de.timolia.headdrops.cmds.headdrops;
import de.timolia.headdrops.cmds.headinfo;
import de.timolia.headdrops.cmds.mobhead;
import de.timolia.headdrops.cmds.myhead;

public class HeadDrops extends JavaPlugin implements Listener {

	private Metrics m;
	public static final String PREFIX = ChatColor.GOLD + "(HeadDrops) " + ChatColor.RESET;
	public static File dataFolder;
	private UpdateChecker updater;

	boolean updateAvailable = false;

	public void onEnable() {
		initConfig();

		getCommand("head").setExecutor(new head());
		getCommand("headdrops").setExecutor(new headdrops(this));
		getCommand("myhead").setExecutor(new myhead());
		getCommand("headinfo").setExecutor(new headinfo());
		getCommand("mobhead").setExecutor(new mobhead());

		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

		dataFolder = getDataFolder();

		// stats and update checking
		try {
			m = new Metrics(this);
			m.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		updater = new UpdateChecker(this);
		updater.start();
	}

	public void onDisable() {
		updater.stop();
	}

	public void reload() {
		updater.stop();
		this.reloadConfig();
		(updater = new UpdateChecker(this)).start();
	}

	public static void log(String text) {
		HeadDrops.log(text, false);
	}

	public static void log(String text, boolean error) {
		if (error)
			Logger.getLogger("Minecraft").severe("[HeadDrops] " + text);
		else
			Logger.getLogger("Minecraft").info("[HeadDrops] " + text);
	}

	private void initConfig() {
		// @formatter:off
		
		String[] parameter = {
				"dropBlank",
				"permissionCheckMob",
				"permissionCheckPlayer",
				"update-checker",
				"chance.blaze", 
				"chance.cave_spider", 
				"chance.chicken", 
				"chance.cow", 
				"chance.creeper", 
				"chance.enderman", 
				"chance.ghast", 
				"chance.iron_golem", 
				"chance.magma_cube", 
				"chance.mushroom_cow", 
				"chance.ocelot", 
				"chance.pig", 
				"chance.pig_zombie", 
				"chance.sheep", 
				"chance.skeleton", 
				"chance.slime", 
				"chance.spider", 
				"chance.squid", 
				"chance.villager", 
				"chance.wither", 
				"chance.player", 
				"chance.zombie"
		};
							
		Object[] defaultValue = {
				false, 
				false, 
				false, 
				true, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5, 
				5
		};
		
		// @formatter:on

		for (int i = 0; i < parameter.length; i++) {
			getConfig().addDefault(parameter[i], defaultValue[i]);
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
