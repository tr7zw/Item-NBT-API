package de.tr7zw.nbtapi.plugin.tests.entities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class EntityTest implements Test {

	@Override
	public void test() throws Exception {
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
					NBTEntity nbte = new NBTEntity(
							world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next());
					nbte.setString("INVALIDEKEY", "test");
				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTEntities!");
			}
			try {
				Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 255,
						world.getSpawnLocation().getBlockZ());
				if (block.getType() == Material.AIR) {
					block.setType(Material.CHEST);
					NBTTileEntity tile = new NBTTileEntity(block.getState());
					tile.setString("Lock", "test");
					if (!tile.asNBTString().contains("Lock:\"test\"")) {
						block.setType(Material.AIR);
						throw new NbtApiException("The Lock wasn't successfully set.");
					}
					block.setType(Material.AIR);
				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTTiles!", ex);
			}
		}
	}

}
