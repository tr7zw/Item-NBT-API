package de.tr7zw.changeme.nbtapi;

import org.bukkit.block.BlockState;

public class NBTTileEntity extends NBTCompound {

    private final BlockState tile;

    public NBTTileEntity(BlockState tile) {
        super(null, null);
        this.tile = tile;
    }

    @Override
    public Object getCompound() {
        return NBTReflectionUtil.getTileEntityNBTTagCompound(tile);
    }

    @Override
    protected void setCompound(Object compound) {
        NBTReflectionUtil.setTileEntityNBTTagCompound(tile, compound);
    }

}
