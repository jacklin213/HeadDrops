/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.timolia.headdrops.HeadDrops;

public final class headdrops implements CommandExecutor {

	private HeadDrops instance;
	private String prefix = HeadDrops.PREFIX;

	public headdrops(HeadDrops instance) {
		this.instance = instance;
	}

	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, String[] args) {
		if (!sender.hasPermission("headdrops.admin")) {
			sender.sendMessage(prefix + "You don't have permission!");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(prefix + "Wrong argument count!");
			sender.sendMessage(prefix + "/headdrops <reload>");
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			instance.reload();
			sender.sendMessage(prefix + "The Configuration has been reloaded!");
		} else {
			sender.sendMessage(prefix + "Wrong arguments!");
			sender.sendMessage(prefix + "/headdrops <reload>");
		}

		return true;
	}

}