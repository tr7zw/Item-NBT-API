package de.tr7zw.changeme.nbtapi.data.proxy;

import de.tr7zw.changeme.nbtapi.handler.NBTHandlers;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import de.tr7zw.changeme.nbtapi.wrapper.NBTProxy;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget.Type;

public interface NBTItemMeta extends NBTProxy {

    @Override
    default void init() {
        registerHandler(ReadableNBT.class, NBTHandlers.STORE_READABLE_TAG);
        registerHandler(ReadWriteNBT.class, NBTHandlers.STORE_READWRITE_TAG);
    }

    @NBTTarget(type = Type.GET, value = "CustomModelData")
    public int getCustomModelData();

    @NBTTarget(type = Type.SET, value = "CustomModelData")
    public void setCustomModelData(int customModelData);

    @NBTTarget(type = Type.GET, value = "Unbreakable")
    public boolean isUnbreakable();

    @NBTTarget(type = Type.SET, value = "Unbreakable")
    public void setUnbreakable(boolean unbreakable);

    public ReadWriteNBT getBlockStateTag();

    public void setBlockStateTag(ReadableNBT blockState);

    @NBTTarget(type = Type.GET, value = "display")
    public DisplayData getDisplayData();

    public interface DisplayData extends NBTProxy {

        @NBTTarget(type = Type.SET, value = "Name")
        public void setRawName(String rawName);

        @NBTTarget(type = Type.GET, value = "Name")
        public String getRawName();

    }

}
