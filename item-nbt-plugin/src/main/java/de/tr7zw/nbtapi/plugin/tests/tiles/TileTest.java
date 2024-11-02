package de.tr7zw.nbtapi.plugin.tests.tiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class TileTest implements Test {

    @Override
    public void test() throws Exception {
        if (MinecraftVersion.isFoliaPresent()) {
            return;
        }
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 254,
                        world.getSpawnLocation().getBlockZ());
                if (world.isChunkLoaded(block.getX() >> 4, block.getZ() >> 4) && block.getType() == Material.AIR) {
                    block.setType(Material.CHEST);
                    if (MinecraftVersion.isNewerThan(MinecraftVersion.MC1_21_R1)) {
                        // 1.21 changed the lock logic. So just try to get/set data, dont check for now
                        NBT.modify(block.getState(), nbt -> {
                            nbt.setString("foo", "bar");
                        });
                        block.setType(Material.AIR);
                        return;
                    }
                    NBTTileEntity tile = new NBTTileEntity(block.getState());
                    if (!MinecraftVersion.isNewerThan(MinecraftVersion.MC1_17_R1)) {
                        if (tile.getInteger("y") != 254) {
                            block.setType(Material.AIR);
                            throw new NbtApiException("The Tile Y pos wasn't correct!");
                        }
                    }
                    tile.setString("Lock", "test");
                    if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3) && !tile.hasTag("Lock")
                            && !"test".equals(tile.getString("test"))) {
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
