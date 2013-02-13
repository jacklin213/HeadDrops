package de.dariusmewes.HeadDrops.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.dariusmewes.HeadDrops.HeadDrops;

public class headinfo implements CommandExecutor {

	private String prefix = HeadDrops.PREFIX;
	public static List<String> enabled = new ArrayList<String>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("headdrops.info")) {
			sender.sendMessage(prefix + "You don't have permission!");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + "This command can only be executed ingame!");
			return true;
		}

		Player p = (Player) sender;
		if (enabled.contains(p.getName())) {
			enabled.remove(p.getName());
			sender.sendMessage(prefix + "Heads will no longer show their name!");
		} else {
			enabled.add(p.getName());
			sender.sendMessage(prefix + "Heads will show their name upon Punch!");
		}

		return true;
	}

}