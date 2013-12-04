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
        else if (mob.equalsIgnoreCase("witherskeleton")
                || mob.equalsIgnoreCase("wither_skeleton"))
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
        else if (mob.equalsIgnoreCase("cavespider")
                || mob.equalsIgnoreCase("cave_spider"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.CAVE_SPIDER));
        else if (mob.equalsIgnoreCase("chicken"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.CHICKEN));
        else if (mob.equalsIgnoreCase("cow"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.COW));
        else if (mob.equalsIgnoreCase("ghast"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.GHAST));
        else if (mob.equalsIgnoreCase("irongolem")
                || mob.equalsIgnoreCase("iron_golem")
                || mob.equalsIgnoreCase("golem"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.IRON_GOLEM));
        else if (mob.equalsIgnoreCase("magmacube")
                || mob.equalsIgnoreCase("magma_cube")
                || mob.equalsIgnoreCase("lavaslime")
                || mob.equalsIgnoreCase("lava_slime"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.MAGMA_CUBE));
        else if (mob.equalsIgnoreCase("mushroomcow")
                || mob.equalsIgnoreCase("mushroom_cow")
                || mob.equalsIgnoreCase("mooshroom"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.MUSHROOM_COW));
        else if (mob.equalsIgnoreCase("ocelot")
                || mob.equalsIgnoreCase("cat"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.OCELOT));
        else if (mob.equalsIgnoreCase("pig"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.PIG));
        else if (mob.equalsIgnoreCase("pigzombie")
                || mob.equalsIgnoreCase("pig_zombie")
                || mob.equalsIgnoreCase("pigman")
                || mob.equalsIgnoreCase("zombiepigman")
                || mob.equalsIgnoreCase("zombie_pigman"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.PIG_ZOMBIE));
        else if (mob.equalsIgnoreCase("sheep"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.SHEEP));
        else if (mob.equalsIgnoreCase("squid"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.SQUID));
        else if (mob.equalsIgnoreCase("villager"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.VILLAGER));
        else if (mob.equalsIgnoreCase("wither"))
            p.getInventory().addItem(SkullManager.getCustomSkull(CustomSkullType.WITHER));
        else {
            sender.sendMessage(prefix + "Invalid head name!");
            return true;
        }

        sender.sendMessage(prefix + "You got the selected head!");

        return true;
    }
}