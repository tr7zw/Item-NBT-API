package de.tr7zw.nbtapi.plugin.tests.tiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
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
                if (block.getType() == Material.AIR) {
                    block.setType(Material.CHEST);
                    NBTTileEntity comp = new NBTTileEntity(block.getState());
                    NBTCompound persistentData = comp.getPersistentDataContainer();
                    persistentData.setString("Foo", "Bar");
                    if (!new NBTTileEntity(block.getState()).getPersistentDataContainer().getString("Foo")
                            .equals("Bar")) {
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
