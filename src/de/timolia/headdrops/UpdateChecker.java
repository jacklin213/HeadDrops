/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class UpdateChecker {

	private HeadDrops plugin;
	private String currentVersion;
	private String readurl = "https://raw.github.com/EX0/HeadDrops/master/version.txt";
	private BukkitTask task;

	public UpdateChecker(HeadDrops plugin) {
		this.plugin = plugin;
		currentVersion = plugin.getDescription().getVersion();
	}

	public void start() {
		if (this.task == null)
			this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
				public void run() {
					check();
				}
			}, 2000, 864000);
		HeadDrops.log("The Update-Checker has been started");
	}

	public void stop() {
		if (this.task != null) {
			this.task.cancel();
			HeadDrops.log("The Update-Checker has been stopped...");
		}
	}

	public void check() {
		if (plugin.getConfig().getBoolean("update-checker")) {
			try {
				HeadDrops.log("Checking for a new version...");
				URL url = new URL(readurl);
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				if ((line = br.readLine()) != null) {
					double version = 0;
					try {
						version = Double.parseDouble(line.split(" ")[0]);
					} catch (Exception e) {
						HeadDrops.log("Update-Checking has failed...", true);
						e.printStackTrace();
					}

					HeadDrops.log("Found version: " + version);
				}
				br.close();
			} catch (IOException e) {
				HeadDrops.log("The UpdateChecker URL is invalid! Please let me know!", true);
			}
		}
	}

}
