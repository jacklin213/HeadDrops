/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.timolia.headdrops.cmds.head;
import de.timolia.headdrops.cmds.headdrops;
import de.timolia.headdrops.cmds.headinfo;
import de.timolia.headdrops.cmds.mobhead;
import de.timolia.headdrops.cmds.myhead;

public class HeadDrops extends JavaPlugin implements Listener {

    private Metrics m;
    public static final String PREFIX = ChatColor.GOLD + "(HeadDrops) " + ChatColor.RESET;
    public static File dataFolder;
    public static boolean updateAvailable = false;

    public void onEnable() {
        this.saveDefaultConfig();

        getCommand("head").setExecutor(new head());
        getCommand("headdrops").setExecutor(new headdrops(this));
        getCommand("myhead").setExecutor(new myhead());
        getCommand("headinfo").setExecutor(new headinfo());
        getCommand("mobhead").setExecutor(new mobhead());

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        dataFolder = getDataFolder();

        // stats and update checking
        try {
            m = new Metrics(this);
            m.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (getConfig().getBoolean("checkForUpdates"))
            UpdateChecker.start(this);
        else
            log("Update-Checking disabled! Change the config.yml to activate it.");
    }

    public void onDisable() {

    }

    public void reload() {
        this.reloadConfig();
        if (!getConfig().getBoolean("checkForUpdates") && UpdateChecker.task != null) {
            UpdateChecker.task.cancel();
            UpdateChecker.task = null;
            log("UpdateChecker stopped");
        } else if (getConfig().getBoolean("checkForUpdates") && UpdateChecker.task == null)
            UpdateChecker.start(this);
    }

    public static void log(String tx) {
        Logger.getLogger("Minecraft").info("[HeadDrops] " + tx);
    }

}