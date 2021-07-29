package dev.tr7zw.nbtapi.spigot.handler;

import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.entity.Entity;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTCompoundAccessor;

public interface PersistentDataContainerAccess {

    public void modifyEntityPersistent(Entity entity, Consumer<NBTCompound> consumer);
    
    public <T> T modifyEntityPersistent(Entity entity, Function<NBTCompound, T> consumer);
    
    public <T> T parseEntityPersistent(Entity entity, Function<NBTCompoundAccessor, T> function);
    
    // Chunks, BlockEntities, Blocks
    
}
