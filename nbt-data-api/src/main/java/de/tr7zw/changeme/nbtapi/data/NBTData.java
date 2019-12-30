package de.tr7zw.changeme.nbtapi.data;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.tr7zw.changeme.nbtapi.NbtApiException;

public class NBTData {

	public static WorldData getWorldData(World world) {
		try {
			return new WorldData(world.getWorldFolder());
		} catch (IOException e) {
			throw new NbtApiException("Error loading World Data!", e);
		}
	}

	public static WorldData getWorldData(File worldFolder) {
		try {
			return new WorldData(worldFolder);
		} catch (IOException e) {
			throw new NbtApiException("Error loading World Data!", e);
		}
	}

	public static PlayerData getPlayerData(UUID uuid) {
		for (World world : Bukkit.getWorlds()) {
			File dataFolder = new File(world.getWorldFolder(), "playerdata");
			File playerFile = new File(dataFolder, uuid.toString() + ".dat");
			if (playerFile.exists()) {
				try {
					return new PlayerData(playerFile);
				} catch (IOException e) {
					throw new NbtApiException("Error loading player data!", e);
				}
			}
		}
		return null;
	}

}
