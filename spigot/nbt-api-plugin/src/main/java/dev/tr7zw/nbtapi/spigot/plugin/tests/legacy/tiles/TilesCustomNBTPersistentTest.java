package dev.tr7zw.nbtapi.spigot.plugin.tests.legacy.tiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTTileEntity;
import de.tr7zw.nbtapi.utils.MinecraftVersion;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.spigot.plugin.tests.Test;

public class TilesCustomNBTPersistentTest implements Test {

	@Override
	public void test() throws Exception {
		if(MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_14_R1.getVersionId())return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 255,
						world.getSpawnLocation().getBlockZ());
				if (block.getType() == Material.AIR) {
					block.setType(Material.CHEST);
					NBTTileEntity comp = new NBTTileEntity(block.getState());
					NBTCompound persistentData = comp.getPersistentDataContainer();
					persistentData.setString("Foo", "Bar");
					if (!new NBTTileEntity(block.getState()).getPersistentDataContainer().getString("Foo").equals("Bar")) {
						block.setType(Material.AIR);
						throw new NBTApiException("Custom Data did not save to the Tile!");
					}
					block.setType(Material.AIR);
				}
			} catch (Exception ex) {
				throw new NBTApiException("Wasn't able to use NBTTiles!", ex);
			}
		}
	}

}
