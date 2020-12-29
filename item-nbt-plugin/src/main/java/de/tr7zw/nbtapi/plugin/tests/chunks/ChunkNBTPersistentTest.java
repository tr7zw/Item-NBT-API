package de.tr7zw.nbtapi.plugin.tests.chunks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import de.tr7zw.changeme.nbtapi.NBTChunk;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class ChunkNBTPersistentTest implements Test {

	@Override
	public void test() throws Exception {
		if(MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_16_R2.getVersionId())return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				if (world.getLoadedChunks().length > 1) {
					Chunk chunk = world.getLoadedChunks()[0];
					NBTChunk comp = new NBTChunk(chunk);
					NBTCompound persistentData = comp.getPersistentDataContainer();
					persistentData.removeKey("Foo");
					if(persistentData.hasKey("Foo")) {
						throw new NbtApiException("Unable to remove key from Chunk!");
					}
					persistentData.setString("Foo", "Bar");
					if (!new NBTChunk(chunk).getPersistentDataContainer().getString("Foo").equals("Bar")) {
						throw new NbtApiException("Custom Data did not save to the Chunk!");
					}
				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTChunks!", ex);
			}
		}
	}

}
