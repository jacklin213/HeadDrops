/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops.commands;

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
			sender.sendMessage(prefix + "debug");
		} else {
			sender.sendMessage(prefix + "Wrong arguments!");
			sender.sendMessage(prefix + "/headdrops <reload/check/update>");
		}

		return true;
	}

}