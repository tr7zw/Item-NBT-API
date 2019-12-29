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

	protected static void checkForUpdates() throws Exception{
			URL url = new URL(REQUEST_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", USER_AGENT);// Set User-Agent

			// If you're not sure if the request will be successful,
			// you need to check the response code and use #getErrorStream if it returned an error code
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);

			// This could be either a JsonArray or JsonObject
			JsonElement element = new JsonParser().parse(reader);
			if (element.isJsonArray()) {
				// Is JsonArray
				JsonArray updates = (JsonArray) element;
				JsonObject latest = (JsonObject) updates.get(updates.size()-1);
				if(!latest.get("name").getAsString().contains(MinecraftVersion.VERSION)){ //Outdated
					MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] The NBT-API at '" + NBTItem.class.getPackage() + "' seems to be outdated!");
					MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Current Version: '" + MinecraftVersion.VERSION + "' Newest Version: " + latest.get("name").getAsString() + "'");
					MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Please update the nbt-api or the plugin that contains the api!");
					
				}else{
					MinecraftVersion.logger.log(Level.INFO, "[NBTAPI] The NBT-API seems to be up-to-date!");
				}
			} else {
				// wut?!
				MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Error when looking for Updates! Got non Json Array: '" + element.toString() + "'");
			}
	}

}
