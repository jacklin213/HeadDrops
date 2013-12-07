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
	private String readurl = "https://raw.github.com/EX0/HeadDrops/master/version.txt";
	private BukkitTask task;

	public UpdateChecker(HeadDrops plugin) {
		this.plugin = plugin;
	}

	public void start() {
		if (this.task == null)
			this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
				public void run() {
					check();
				}
			}, 200, 864000);
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
					double currentVersion = 0;
					String[] args = line.split(" ");
					try {
						version = Double.parseDouble(args[0]);
						currentVersion = Double.parseDouble(plugin.getDescription().getVersion());
					} catch (Exception e) {
						HeadDrops.log("Update-Checking has failed...", true);
						e.printStackTrace();
					}

					if (version > currentVersion) {
						String additionalMsg = "";
						if (args.length > 1)
							for (int i = 1; i < args.length; i++)
								additionalMsg += args[i] + " ";

						HeadDrops.log("A new version is available: " + version);
						if (!additionalMsg.equalsIgnoreCase(""))
							HeadDrops.log("Additional information: " + additionalMsg);

						plugin.updateAvailable = true;
					}
				}

				br.close();
			} catch (IOException e) {
				HeadDrops.log("The UpdateChecker URL is invalid! Please let me know!", true);
			}
		}
	}

}
