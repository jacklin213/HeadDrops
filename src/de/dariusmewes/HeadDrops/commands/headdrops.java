/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.dariusmewes.HeadDrops.HeadDrops;
import de.dariusmewes.HeadDrops.UpdateChecker;

public class headdrops implements CommandExecutor {

	private HeadDrops instance;
	private String prefix = HeadDrops.PREFIX;

	public headdrops(HeadDrops instance) {
		this.instance = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("headdrops.admin")) {
			sender.sendMessage(prefix + "You don't have permission!");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(prefix + "Wrong argument count!");
			sender.sendMessage(prefix + "/headdrops <reload/check/update>");
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			instance.reload();
			sender.sendMessage(prefix + "The Configuration has been reloaded!");
		} else if (args[0].equalsIgnoreCase("check")) {
			sender.sendMessage(prefix + "Checking...");
			if (UpdateChecker.check())
				sender.sendMessage(prefix + "A new version is available! Type \"/headdrops update\" and restart afterwards to get it.");
			else
				sender.sendMessage(prefix + "The installed version is the most recent.");

		} else if (args[0].equalsIgnoreCase("update")) {
			if (HeadDrops.updateAvailable) {
				sender.sendMessage(prefix + "Downloading update...");

				try {
					File file = new File(HeadDrops.dataFolder + File.separator + "newVersion.jar");
					if (file.exists())
						file.delete();

					file.createNewFile();

					BufferedInputStream i = new BufferedInputStream(new URL(UpdateChecker.VURL).openStream());
					BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file));
					byte[] buffer = new byte[1024];
					int cache = 0;

					while ((cache = i.read(buffer, 0, 1024)) >= 0)
						o.write(buffer, 0, cache);

					i.close();
					o.close();
				} catch (Exception e) {
					HeadDrops.log("An error occured while downloading the update:");
					e.printStackTrace();
					sender.sendMessage(prefix + "An error occured while downloading the update!");
				}

				sender.sendMessage(prefix + "The update has been downloaded. Please restart the server to load the new version.");
			} else
				sender.sendMessage(prefix + "A possible update hasn't been recognized yet. Maybe you should do \"/headdrops check\"");

		} else {
			sender.sendMessage(prefix + "Wrong arguments!");
			sender.sendMessage(prefix + "/headdrops <reload/check/update>");
		}

		return true;
	}

}