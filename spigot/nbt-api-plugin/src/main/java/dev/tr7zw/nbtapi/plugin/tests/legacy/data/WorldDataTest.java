package dev.tr7zw.nbtapi.plugin.tests.legacy.data;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.tr7zw.changeme.nbtapi.data.NBTData;
import de.tr7zw.changeme.nbtapi.data.WorldData;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class WorldDataTest implements Test{

	@Override
	public void test() throws Exception {
		for(World world : Bukkit.getWorlds()){
			if(!new File(world.getWorldFolder(), "level.dat").exists())continue;
			WorldData data = NBTData.getWorldData(world);
			if(data.getWorldName() == null || data.getSpawnPosition() == null)throw new NBTApiException("Got Null");
		}
	}

}
