/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops.cmds;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.timolia.headdrops.CustomSkullType;
import de.timolia.headdrops.HeadDrops;
import de.timolia.headdrops.SkullManager;

public final class mobhead implements CommandExecutor {

    private final String prefix = HeadDrops.PREFIX;

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "This command can only be executed ingame!");
            return true;
        }

        final Player p = (Player) sender;

        if (!p.hasPermission("headdrops.head")) {
            p.sendMessage(prefix + "You don't have permission!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(prefix + "Please specify a mob!");
            return true;
        }

        final String mob = args[0];
        if (mob.equalsIgnoreCase("skeleton"))
            p.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (byte) 0));
        else if (mob.equalsIgnoreCase("witherskeleton"))
            p.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (byte) 1));
        else if (mob.equalsIgnoreCase("zombie"))
            p.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
        else if (mob.equalsIgnoreCase("player"))
            p.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (byte) 3));
        else if (mob.equalsIgnoreCase("creeper"))
            p.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 1, (byte) 4));
        else if (mob.equalsIgnoreCase("blaze"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.BLAZE));
        else if (mob.equalsIgnoreCase("enderman"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.ENDERMAN));
        else if (mob.equalsIgnoreCase("slime"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.SLIME));
        else if (mob.equalsIgnoreCase("spider"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.SPIDER));
        else {
            sender.sendMessage(prefix + "Head not found! Please choose from skeleton, witherskeleton, zombie, player, creeper, blaze, enderman, slime and spider");
            return true;
        }

        sender.sendMessage(prefix + "You got the selected head!");

        return true;
    }
}