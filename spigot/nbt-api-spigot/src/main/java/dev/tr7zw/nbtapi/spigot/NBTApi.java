package dev.tr7zw.nbtapi.spigot;

import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTCompoundAccessor;
import dev.tr7zw.nbtapi.handler.EntityHandler;
import dev.tr7zw.nbtapi.handler.ItemHandler;
import dev.tr7zw.nbtapi.spigot.handler.PersistentDataContainerAccess;
import dev.tr7zw.nbtapi.spigot.impl.NBTItemStack;

public class NBTApi implements ItemHandler<ItemStack, NBTItemStack>, EntityHandler<Entity>, PersistentDataContainerAccess {

    public static final NBTApi nbtApi = new NBTApi();
    
    private NBTApi() {
        
    }

    @Override
    public NBTCompound getEntity(Entity entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modifyEntity(Entity entity, Consumer<NBTCompound> consumer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <T> T parseEntity(Entity entity, Function<NBTCompoundAccessor, T> function) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NBTItemStack getItemStack(ItemStack item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T parseItemStack(ItemStack item, Function<NBTCompoundAccessor, T> function) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modifyItemStack(ItemStack item, Consumer<NBTCompound> consumer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <T> T modifyEntity(Entity entity, Function<NBTCompound, T> consumer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T modifyItemStack(ItemStack item, Function<NBTCompound, T> consumer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modifyEntityPersistent(Entity entity, Consumer<NBTCompound> consumer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <T> T modifyEntityPersistent(Entity entity, Function<NBTCompound, T> consumer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T parseEntityPersistent(Entity entity, Function<NBTCompoundAccessor, T> function) {
        // TODO Auto-generated method stub
        return null;
    }


}
