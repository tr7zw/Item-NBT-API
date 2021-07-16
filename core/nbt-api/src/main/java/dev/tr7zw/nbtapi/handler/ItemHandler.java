package dev.tr7zw.nbtapi.handler;

import dev.tr7zw.nbtapi.INBTItemStack;
import dev.tr7zw.nbtapi.INBTItemStackAccessor;

public interface ItemHandler<I, H extends INBTItemStackAccessor<I>, W extends INBTItemStack<I>> {

    public H getItemStackAccessor(I item);
    
    public W getItemStack(I item);
    
}
