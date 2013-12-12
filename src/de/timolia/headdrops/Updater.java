/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.timolia.headdrops;

import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Updater {

    private Plugin plugin;
    private String versionName;
    private String versionLink;

    private URL url; // Connecting to RSS

    private int id = -1; // Project's Curse ID
    private static final String TITLE_VALUE = "name"; // Gets remote file's title
    private static final String LINK_VALUE = "downloadUrl"; // Gets remote file's download link
    private static final String QUERY = "/servermods/files?projectIds="; // Path to GET
    private static final String HOST = "https://api.curseforge.com"; // Slugs will be appended to this to get to the project's RSS feed

    private static final String[] NO_UPDATE_TAG = {"-DEV", "-PRE", "-SNAPSHOT"}; // If the version number contains one of these, don't update.
    private static final int BYTE_SIZE = 1024; // Used for downloading files

    public Updater(final Plugin plugin, final int id, final File file, final boolean autoDownload) {
        this.plugin = plugin;
        this.id = id;

        try {
            this.url = new URL(Updater.HOST + Updater.QUERY + id);
        } catch (final MalformedURLException e) {
            logError("The project ID provided for updating, " + id + " is invalid.");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (read()) {
                    try {
                        Double installedVersion = Double.valueOf(plugin.getDescription().getVersion());
                        Double remoteVersion = Double.valueOf(versionName.substring(1));

                        if (remoteVersion > installedVersion) {
                            log("New version has been found: " + versionName);
                            if (autoDownload)
                                saveFile(new File(plugin.getDataFolder().getParent(), plugin.getServer().getUpdateFolder()), plugin.getDescription().getName() + ".jar", versionLink);
                            else
                                log("Get it at: " + versionLink);
                        } else
                            log("You have the newest version installed");
                    } catch (NumberFormatException e) {
                        logWarning("Could not compare versions.");
                        return;
                    }
                }
            }
        }).start();
    }

    private boolean read() {
        try {
            final URLConnection conn = this.url.openConnection();
            conn.setConnectTimeout(5000);
            conn.addRequestProperty("User-Agent", "Head-Drops (by pizzafreak08)");
            conn.setDoOutput(true);

            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final String response = reader.readLine();

            final JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.size() == 0) {
                logWarning("The updater could not find any files for the project id " + this.id);
                return false;
            }

            this.versionName = (String) ((JSONObject) array.get(array.size() - 1)).get(Updater.TITLE_VALUE);
            this.versionLink = (String) ((JSONObject) array.get(array.size() - 1)).get(Updater.LINK_VALUE);

            return true;
        } catch (final IOException e) {
            logWarning("The updater could not contact dev.bukkit.org for updating.");
            logWarning("The site may be experiencing temporary downtime.");
            e.printStackTrace();
            return false;
        }
    }

    private void saveFile(File folder, String file, String host) {
        if (!folder.exists())
            folder.mkdir();

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            // Download the file
            final URL url = new URL(host);
            final int fileLength = url.openConnection().getContentLength();
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(folder.getAbsolutePath() + "/" + file);

            final byte[] data = new byte[Updater.BYTE_SIZE];
            int count;
            log("About to download a new update: " + this.versionName);
            long downloaded = 0;
            while ((count = in.read(data, 0, Updater.BYTE_SIZE)) != -1) {
                downloaded += count;
                fout.write(data, 0, count);
                final int percent = (int) ((downloaded * 100) / fileLength);
                if ((percent % 10) == 0)
                    log("Downloading update: " + percent + "% of " + fileLength + " bytes.");
            }

            log("Finished updating.");
        } catch (final Exception ex) {
            logWarning("The auto-updater tried to download a new update, but was unsuccessful.");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (final Exception ex) {

            }
        }
    }

    private void log(String message) {
        plugin.getLogger().info("Updater: " + message);
    }

    private void logWarning(String message) {
        plugin.getLogger().warning("Updater: " + message);
    }

    private void logError(String message) {
        plugin.getLogger().severe("Updater: " + message);
    }

}
