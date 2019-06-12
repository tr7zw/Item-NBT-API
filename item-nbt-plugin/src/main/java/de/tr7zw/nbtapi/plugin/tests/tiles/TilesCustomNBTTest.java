package de.tr7zw.nbtapi.plugin.tests.tiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;
import de.tr7zw.nbtinjector.NBTInjector;

public class TilesCustomNBTTest implements Test{

	@Override
	public void test() throws Exception {
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 255, world.getSpawnLocation().getBlockZ());
				if (block.getType() == Material.AIR) {
					block.setType(Material.CHEST);
					NBTCompound comp = NBTInjector.getNbtData(block.getState());
					comp.setString("Foo", "Bar");
					if(!new NBTTileEntity(block.getState()).asNBTString().contains("__extraData:{Foo:\"Bar\"}")) {
						block.setType(Material.AIR);
						throw new NbtApiException("Custom Data did not save to the Tile!");
					}
					block.setType(Material.AIR);
				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTTiles!", ex);
			}
		}
	}

}
