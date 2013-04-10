/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.timolia.headdrops.HeadDrops;
import de.timolia.headdrops.SkullManager;

public final class myhead implements CommandExecutor {

	private static final String prefix = HeadDrops.PREFIX;

	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + "This command can only be executed ingame!");
			return true;
		}

		final Player p = (Player) sender;

		if (!p.hasPermission("headdrops.myhead")) {
			p.sendMessage(prefix + "You don't have permission!");
			return true;
		}

		p.getInventory().addItem(SkullManager.getSkinnedHead(p.getName()));
		p.sendMessage(prefix + "Here is your head");

		return true;
	}

}