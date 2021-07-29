package dev.tr7zw.nbtapi.handler;

import java.util.function.Consumer;
import java.util.function.Function;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTCompoundAccessor;

public interface EntityHandler<E> {
    
    public NBTCompound getEntity(E entity);
    
    public void modifyEntity(E entity, Consumer<NBTCompound> consumer);
    
    public <T> T modifyEntity(E entity, Function<NBTCompound, T> consumer);
    
    public <T> T parseEntity(E entity, Function<NBTCompoundAccessor, T> function);
    
}
