package dev.tr7zw.nbtapi;

public interface INBTItemStack<I> extends NBTCompoundAccessor, NBTCompound {

    public I getItemStack();
    
}
