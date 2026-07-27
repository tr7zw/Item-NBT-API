package de.tr7zw.nbtapi.plugin.tests.chunks;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTType;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

import java.util.List;

public class ChunkNBTPersistentTest implements Test {

    @Override
    public void test() throws Exception {
        if (MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_16_R3.getVersionId())
            return;

        List<World> loadedWorlds = Bukkit.getWorlds();
        if (loadedWorlds.isEmpty())
            return;

        World world = loadedWorlds.get(0);
        Chunk[] loadedChunks = world.getLoadedChunks();
        if (loadedChunks.length == 0)
            return;

        Chunk chunk = loadedChunks[0];
        try {
            NBT.modifyChunkPDC(chunk, nbt -> nbt.setString("Foo", "Bar"));
            if (!NBT.readAndGetChunkPDC(chunk, nbt -> nbt.hasTag("Foo", NBTType.NBTTagString) && "Bar".equals(nbt.getString("Foo")))) {
                throw new NbtApiException("Custom Data did not save to the Chunk!");
            }

            NBT.modifyChunkPDC(chunk, nbt -> nbt.removeKey("Foo"));
            if (NBT.readAndGetChunkPDC(chunk, nbt -> nbt.hasTag("Foo"))) {
                throw new NbtApiException("Unable to remove key from Chunk!");
            }
        } catch (Exception ex) {
            throw new NbtApiException("Wasn't able to use NBTChunks!", ex);
        }
    }

}
