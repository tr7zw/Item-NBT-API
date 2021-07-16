package dev.tr7zw.nbtapi.plugin.tests.legacy.tiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.nbtapi.NBTTileEntity;
import de.tr7zw.nbtapi.utils.MinecraftVersion;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class TileTest implements Test {

	@Override
	public void test() throws Exception {
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 254,
						world.getSpawnLocation().getBlockZ());
				if (block.getType() == Material.AIR) {
					block.setType(Material.CHEST);
					NBTTileEntity tile = new NBTTileEntity(block.getState());
					if(tile.getInteger("y") != 254) {
						block.setType(Material.AIR);
						throw new NBTApiException("The Tile Y pos wasn't correct!");
					}
					tile.setString("Lock", "test");
					if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3) && !tile.hasKey("Lock") && !"test".equals(tile.getString("test"))) {
						block.setType(Material.AIR);
						throw new NBTApiException("The Lock wasn't successfully set.");
					}
					block.setType(Material.AIR);
				}
			} catch (Exception ex) {
				throw new NBTApiException("Wasn't able to use NBTTiles!", ex);
			}
		}
	}

}
