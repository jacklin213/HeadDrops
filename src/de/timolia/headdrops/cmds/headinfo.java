/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops.cmds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.timolia.headdrops.HeadDrops;

public class headinfo implements CommandExecutor {

	private static List<String> active = new ArrayList<String>();
	private String prefix = HeadDrops.PREFIX;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("headdrops.headinfo")) {
			sender.sendMessage(prefix + "You don't have permission!");
			return true;
		}

		final Player p;
		final boolean self;

		if (args.length >= 1) {
			p = Bukkit.getPlayer(args[0]);
			self = false;
			if (p == null) {
				sender.sendMessage(prefix + "The player " + args[0] + " was not found!");
				return true;
			}
		} else {
			if (sender instanceof Player) {
				p = (Player) sender;
				self = true;
			} else {
				sender.sendMessage(prefix + "This command can only be executed from ingame");
				return true;
			}
		}

		if (active.contains(p.getName())) {
			active.remove(p.getName());
			sender.sendMessage(prefix + "You disabled headinfo for " + (self ? "yourself" : p.getName()) + ".");
		} else {
			active.add(p.getName());
			sender.sendMessage(prefix + "You enabled headinfo for " + (self ? "yourself" : p.getName()) + ".");
		}

		return true;
	}

	public static boolean isActive(Player p) {
		return active.contains(p.getName());
	}

}