/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.HeadDrops;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class UpdateChecker {

	private static final String name = "head-drops";
	private static final String infoMsg = "(HeadDrops) A new version is available! Get it at: http://dev.bukkit.org/server-mods/" + name;
	private static PluginDescriptionFile pdf;
	public static BukkitTask task;

	private UpdateChecker() {

	}

	public static void start(final JavaPlugin instance) {
		pdf = instance.getDescription();
		HeadDrops.log("UpdateChecker started");
		task = Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {
			public void run() {
				if (HeadDrops.updateAvailable)
					HeadDrops.log(infoMsg);
				else {
					check();
					if (HeadDrops.updateAvailable)
						HeadDrops.log(infoMsg);
				}
			}
		}, 200L, 144000L);
	}

	public static void check() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL("http://dev.bukkit.org/server-mods/" + name + "/files.rss").openStream());
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String[] data = getTagValue("title", eElement).split(" ");
					String name = data[0];

					if (name.equalsIgnoreCase(pdf.getName())) {
						double v = Double.valueOf(data[2]);
						double installedV = Double.valueOf(pdf.getVersion());
						if (v > installedV)
							HeadDrops.updateAvailable = true;
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

}