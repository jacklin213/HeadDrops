/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class UpdateChecker {

	public static BukkitTask task;
	private static final String VURL = "https://dl.dropbox.com/u/56892130/TPL/Versions.txt";
	private static PluginDescriptionFile pdf;
	private static final String infoMsg = "(HeadDrops) A new version is available! Get it at: http://dev.bukkit.org/server-mods/head-drops or type /headdrops update";

	public static void start(JavaPlugin instance) {
		pdf = instance.getDescription();
		HeadDrops.log("UpdateChecker started");
		task = Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {
			public void run() {
				if (HeadDrops.updateAvailable)
					HeadDrops.log(infoMsg);
				else
					check();
			}
		}, 200L, 144000L);
	}

	public static boolean check() {
		URL url;
		try {
			url = new URL(VURL);
		} catch (MalformedURLException e) {
			return false;
		}

		Map<String, String> data = new HashMap<String, String>();
		try {
			InputStream in = url.openStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = read.readLine()) != null) {
				String[] lineData = line.split(" ");
				data.put(lineData[0], lineData[1]);
			}
		} catch (IOException e) {
			return false;
		}

		String vS = data.get(pdf.getName());
		double v, currentV;
		try {
			v = Double.valueOf(vS);
			currentV = Double.valueOf(pdf.getVersion());
		} catch (NumberFormatException e) {
			return false;
		}

		if (v > currentV) {
			HeadDrops.log(infoMsg);
			HeadDrops.updateAvailable = true;
			return true;
		} else
			return false;
	}

}