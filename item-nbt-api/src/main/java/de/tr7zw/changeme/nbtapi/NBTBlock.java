package de.tr7zw.changeme.nbtapi;

import java.util.Set;

import org.bukkit.block.Block;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Helper class to store NBT data to Block Locations. Use getData() to get the
 * NBT instance. Important notes:
 * 
 * - Non BlockEntities can not have NBT data. This stores the data to the chunk
 * instead!
 * 
 * - The data is really just on the location. If the block gets
 * broken/changed/exploded/moved etc., the data is still on that location!
 * 
 * @author tr7zw
 *
 */
public class NBTBlock {

    private final Block block;
    private final NBTChunk nbtChunk;

    public NBTBlock(Block block) {
        this.block = block;
        if (!MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_16_R3)) {
            throw new NbtApiException("NBTBlock is only working for 1.16.4+!");
        }
        nbtChunk = new NBTChunk(block.getChunk());
    }

    public NBTCompound getData() {
        return new NBTBlockCompound(nbtChunk.getPersistentDataContainer(), getBlockKey());
    }

    private String getBlockKey() {
        return block.getX() + "_" + block.getY() + "_" + block.getZ();
    }

    private static final class NBTBlockCompound extends NBTCompound {

        private final NBTCompound persistentDataContainer;
        private final String blockKey;

        private NBTBlockCompound(NBTCompound persistentDataContainer, String blockKey) {
            super(null, null);
            this.persistentDataContainer = persistentDataContainer;
            this.blockKey = blockKey;
        }

        @Override
        protected Object getResolvedObject() {
            NBTCompound data = getExistingData();
            if (data == null) {
                return null;
            }
            return data.getResolvedObject();
        }

        @Override
        public Object getCompound() {
            NBTCompound data = getExistingData();
            if (data == null) {
                return null;
            }
            return data.getResolvedObject();
        }

        @Override
        protected void prepareWrite() {
            getOrCreateData();
        }

        @Override
        protected void setCompound(Object compound) {
            if (compound == null || isEmptyCompound(compound)) {
                removeData();
                return;
            }
            persistentDataContainer.getOrCreateCompound("blocks").set(blockKey, compound);
        }

        private NBTCompound getExistingData() {
            NBTCompound blocks = persistentDataContainer.getCompound("blocks");
            if (blocks == null) {
                return null;
            }
            return blocks.getCompound(blockKey);
        }

        private NBTCompound getOrCreateData() {
            return persistentDataContainer.getOrCreateCompound("blocks").getOrCreateCompound(blockKey);
        }

        private void removeData() {
            NBTCompound blocks = persistentDataContainer.getCompound("blocks");
            if (blocks == null) {
                return;
            }
            blocks.removeKey(blockKey);
            if (blocks.getKeys().isEmpty()) {
                persistentDataContainer.removeKey("blocks");
            }
        }

        private boolean isEmptyCompound(Object compound) {
            @SuppressWarnings("unchecked")
            Set<String> keys = (Set<String>) ReflectionMethod.COMPOUND_GET_KEYS.run(compound);
            return keys.isEmpty();
        }
    }

}
