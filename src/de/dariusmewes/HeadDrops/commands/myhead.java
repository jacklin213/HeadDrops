/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.dariusmewes.HeadDrops.HeadDrops;

public class myhead implements CommandExecutor {

	private static final String prefix = HeadDrops.PREFIX;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + "This command can only be executed ingame!");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("headdrops.myhead")) {
			p.sendMessage(prefix + "You don't have permission!");
			return true;
		}

		p.getInventory().addItem(HeadDrops.setSkin(new ItemStack(Material.SKULL_ITEM, 1, (byte) 3), p.getName()));
		p.sendMessage(prefix + "Here is your head");

		return true;
	}

}