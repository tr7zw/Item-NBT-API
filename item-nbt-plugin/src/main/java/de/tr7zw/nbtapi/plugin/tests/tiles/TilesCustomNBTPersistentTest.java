package de.tr7zw.nbtapi.plugin.tests.tiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class TilesCustomNBTPersistentTest implements Test {

    @Override
    public void test() throws Exception {
        if (MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_14_R1.getVersionId())
            return;
        if (MinecraftVersion.isFoliaPresent()) {
            return;
        }
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 250,
                        world.getSpawnLocation().getBlockZ());
                if (world.isChunkLoaded(block.getX() >> 4, block.getZ() >> 4) && block.getType() == Material.AIR) {
                    block.setType(Material.CHEST);
                    NBT.modifyPersistentData(block.getState(), persistentData -> {
                        persistentData.setString("Foo", "Bar");
                    });
                    NBT.getPersistentData(block.getState(), persistentData -> {
                        if (!persistentData.getString("Foo").equals("Bar")) {
                            block.setType(Material.AIR);
                            throw new NbtApiException("Custom Data did not save to the Tile!");
                        }
                        return null;
                    });
                    block.setType(Material.AIR);
                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTTiles!", ex);
            }
        }
    }

}
