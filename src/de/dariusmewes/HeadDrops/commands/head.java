/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.dariusmewes.HeadDrops.HeadDrops;

public class head implements CommandExecutor {

	private String prefix = HeadDrops.PREFIX;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(prefix + "This command can only be executed ingame!");
				return true;
			}

			Player p = (Player) sender;

			if (!p.hasPermission("headdrops.head")) {
				p.sendMessage(prefix + "You don't have permission!");
				return true;
			}

			p.getInventory().addItem(HeadDrops.setSkin(new ItemStack(Material.SKULL_ITEM, 1, (byte) 3), args[0]));
			p.sendMessage(prefix + "You got one head of " + args[0]);
			
		} else if (args.length == 2) {
			if (!sender.hasPermission("headdrops.other")) {
				sender.sendMessage(prefix + "You don't have permission!");
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage(prefix + args[0] + " is not online!");
			} else {
				target.getInventory().addItem(HeadDrops.setSkin(new ItemStack(Material.SKULL_ITEM, 1, (byte) 3), args[1]));
				sender.sendMessage(prefix + target.getName() + " got one head of " + args[1]);
			}

		} else {
			sender.sendMessage(prefix + "Wrong argument count!");
			sender.sendMessage(prefix + "/head [player] <name of head>");
		}

		return true;
	}
	
}