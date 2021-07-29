package dev.tr7zw.nbtapi.handler;

import java.util.function.Consumer;
import java.util.function.Function;

import dev.tr7zw.nbtapi.INBTItemStack;
import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTCompoundAccessor;

public interface ItemHandler<I, W extends INBTItemStack<I>> {
    
    public W getItemStack(I item);
    
    public void modifyItemStack(I item, Consumer<NBTCompound> consumer);
    
    public <T> T modifyItemStack(I item, Function<NBTCompound, T> consumer);
    
    public <T> T parseItemStack(I item, Function<NBTCompoundAccessor, T> function);
    
}
