package de.tr7zw.itemnbtapi;

import org.bukkit.block.BlockState;

public class NBTTileEntity extends NBTCompound{

    private final BlockState tile;

    public NBTTileEntity(BlockState tile) {
        super(null, null);
        this.tile = tile;
    }

    protected Object getCompound() {
        return NBTReflectionUtil.getTileEntityNBTTagCompound(tile);
    }

    protected void setCompound(Object tag) {
        NBTReflectionUtil.setTileEntityNBTTagCompound(tile, tag);
    }


}
