package de.tr7zw.changeme.nbtapi.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.tr7zw.changeme.nbtapi.NBTItem;

/**
 * This class uses the Spiget API to check for updates
 *
 */
public class VersionChecker {

	private static final String USER_AGENT = "nbt-api Version check";
	private static final String REQUEST_URL = "https://api.spiget.org/v2/resources/7939/versions?size=100";
	public static boolean hideOk = false;

	protected static void checkForUpdates() throws Exception {
		URL url = new URL(REQUEST_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("User-Agent", USER_AGENT);// Set
																// User-Agent

		// If you're not sure if the request will be successful,
		// you need to check the response code and use #getErrorStream if it
		// returned an error code
		InputStream inputStream = connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream);

		// This could be either a JsonArray or JsonObject
		JsonElement element = new JsonParser().parse(reader);
		if (element.isJsonArray()) {
			// Is JsonArray
			JsonArray updates = (JsonArray) element;
			JsonObject latest = (JsonObject) updates.get(updates.size() - 1);
			int versionDifference = getVersionDifference(latest.get("name").getAsString());
			if (versionDifference == -1) { // Outdated
				MinecraftVersion.getLogger().log(Level.WARNING,
						"[NBTAPI] The NBT-API located at '" + NBTItem.class.getPackage() + "' seems to be outdated!");
				MinecraftVersion.getLogger().log(Level.WARNING, "[NBTAPI] Current Version: '" + MinecraftVersion.VERSION
						+ "' Newest Version: " + latest.get("name").getAsString() + "'");
				MinecraftVersion.getLogger().log(Level.WARNING,
						"[NBTAPI] Please update the NBTAPI or the plugin that contains the api(nag the mod author when the newest release has an old version, not the NBTAPI dev)!");

			} else if (versionDifference == 0) {
			    if(!hideOk)
			        MinecraftVersion.getLogger().log(Level.INFO, "[NBTAPI] The NBT-API seems to be up-to-date!");
			} else if (versionDifference == 1) {
				MinecraftVersion.getLogger().log(Level.INFO, "[NBTAPI] The NBT-API at '" + NBTItem.class.getPackage()
						+ "' seems to be a future Version, not yet released on Spigot/CurseForge! This is not an error!");
				MinecraftVersion.getLogger().log(Level.INFO, "[NBTAPI] Current Version: '" + MinecraftVersion.VERSION
						+ "' Newest Version: " + latest.get("name").getAsString() + "'");
			}
		} else {
			// wut?!
			MinecraftVersion.getLogger().log(Level.WARNING,
					"[NBTAPI] Error when looking for Updates! Got non Json Array: '" + element.toString() + "'");
		}
	}

	// -1 = we are outdated
	// 0 = up to date
	// 1 = using a future version
	// This method is only able to compare the Format 0.0.0(-SNAPSHOT)
	private static int getVersionDifference(String version) {
		String current = MinecraftVersion.VERSION;
		if (current.equals(version))
			return 0;
		String pattern = "\\.";
		if (current.split(pattern).length != 3 || version.split(pattern).length != 3)
			return -1;
		int curMaj = Integer.parseInt(current.split(pattern)[0]);
		int curMin = Integer.parseInt(current.split(pattern)[1]);
		String curPatch = current.split(pattern)[2];
		int relMaj = Integer.parseInt(version.split(pattern)[0]);
		int relMin = Integer.parseInt(version.split(pattern)[1]);
		String relPatch = version.split(pattern)[2];
		if (curMaj < relMaj)
			return -1;
		if (curMaj > relMaj)
			return 1;
		if (curMin < relMin)
			return -1;
		if (curMin > relMin)
			return 1;
		int curPatchN = Integer.parseInt(curPatch.split("-")[0]);
		int relPatchN = Integer.parseInt(relPatch.split("-")[0]);
		if (curPatchN < relPatchN)
			return -1;
		if (curPatchN > relPatchN)
			return 1;
		if (!relPatch.contains("-") && curPatch.contains("-"))
			return -1; // Release has no - but we do = We use a Snapshot of the
						// release
		if (relPatch.contains("-") && curPatch.contains("-"))
			return 0; // Release and cur are Snapshots/alpha/beta
		return 1;
	}

}
