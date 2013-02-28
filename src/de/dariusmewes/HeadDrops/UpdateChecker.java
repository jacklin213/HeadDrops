/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateChecker {

	public static BukkitTask task;
	private static String XMLURL = "https://dl.dropbox.com/u/56892130/TPL/TManager2.xml";
	private static PluginDescriptionFile pdf;

	public static void start(JavaPlugin instance) {
		pdf = instance.getDescription();
		HeadDrops.log("UpdateChecker started");
		task = Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {
			public void run() {
				if (check()) {
					Logger.getLogger("Minecraft").info("(HeadDrops) A new version is available! Get it at: http://dev.bukkit.org/server-mods/head-drops");
					HeadDrops.updateAvailable = true;
				}
			}
		}, 200L, 144000L);
	}

	public static boolean check() {
		try {
			File xml = new File("plugins" + File.separator + pdf.getName() + File.separator + "temp.xml");
			if (xml.exists())
				xml.delete();

			xml.createNewFile();

			BufferedInputStream input = new BufferedInputStream(new URL(XMLURL).openStream());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(xml));
			byte[] buffer = new byte[1024];
			int i = 0;

			while ((i = input.read(buffer, 0, 1024)) >= 0) {
				output.write(buffer, 0, i);
			}

			input.close();
			output.close();

			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("plugin");

			String name = "";
			String version = "";
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					name = getTagValue("name", eElement);

					if (name.equalsIgnoreCase(pdf.getName())) {
						version = getTagValue("version", eElement);
						break;
					}
				}
			}

			xml.delete();
			double v;
			double installedV;

			try {
				v = Double.valueOf(version);
				installedV = Double.valueOf(pdf.getVersion());
			} catch (Exception e) {
				return false;
			}

			if (v > installedV) {
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}