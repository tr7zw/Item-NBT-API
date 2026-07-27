package de.tr7zw.changeme.nbtapi;

import org.bukkit.Chunk;

import de.tr7zw.changeme.nbtapi.utils.CheckUtil;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

/**
 * Helper class to store NBT data to {@link Chunk}'s PDC (persistent data container).
 *
 * @deprecated use methods in {@link NBT} class to read/modify chunk's nbt
 */
@Deprecated
public class NBTChunk {

    private final Chunk chunk;

    public NBTChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
     * available for 1.16.4+!
     * 
     * @return NBTCompound containing the data of the PersistentDataAPI
     */
    public NBTCompound getPersistentDataContainer() {
        CheckUtil.assertAvailable(MinecraftVersion.MC1_16_R3);
        return new NBTPersistentDataContainer(chunk.getPersistentDataContainer());
    }

}
