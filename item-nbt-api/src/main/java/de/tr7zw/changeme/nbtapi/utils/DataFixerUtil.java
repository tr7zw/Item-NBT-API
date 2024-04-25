package de.tr7zw.changeme.nbtapi.utils;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTReflectionUtil;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.MojangToMapping;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

public class DataFixerUtil {

    public static final int VERSION1_12_2 = 1343;
    public static final int VERSION1_16_5 = 2586;
    public static final int VERSION1_17_1 = 2730;
    public static final int VERSION1_18_2 = 2975;
    public static final int VERSION1_19_2 = 3120;
    public static final int VERSION1_19_4 = 3337;
    public static final int VERSION1_20_1 = 3465;
    public static final int VERSION1_20_2 = 3578;
    public static final int VERSION1_20_4 = 3700;
    public static final int VERSION1_20_5 = 3837;

    @SuppressWarnings("unchecked")
    public static Object fixUpRawItemData(Object nbt, int fromVersion, int toVersion)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        DataFixer dataFixer = (DataFixer) ReflectionMethod.GET_DATAFIXER.run(null);
        TypeReference itemStackReference = (TypeReference) ClassWrapper.NMS_REFERENCES.getClazz()
                .getField(MojangToMapping.getMapping().get("net.minecraft.util.datafix.fixes.References#ITEM_STACK"))
                .get(null);
        DynamicOps<Object> nbtOps = (DynamicOps<Object>) ClassWrapper.NMS_NBTOPS.getClazz()
                .getField(MojangToMapping.getMapping().get("net.minecraft.nbt.NbtOps#INSTANCE")).get(null);
        Dynamic<Object> fixed = dataFixer.update(itemStackReference, new Dynamic<Object>(nbtOps, nbt), fromVersion,
                toVersion);
        return fixed.getValue();
    }

    public static ReadWriteNBT fixUpItemData(ReadWriteNBT nbt, int fromVersion, int toVersion)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return new NBTContainer(fixUpRawItemData(
                NBTReflectionUtil.getToCompount(((NBTCompound) nbt).getCompound(), ((NBTCompound) nbt)), fromVersion,
                toVersion));
    }

    /**
     * This method will return the closest or exact version number of the current
     * server. Only contains versions people will reasonably use and will fail with
     * an exception, when the target version is before 1.12.2. (Assuming no one will
     * update 1.8 items to 1.11, if so, provide the version numbers to the converter
     * method directly)
     * 
     * @return
     */
    public static int getCurrentVersion() {
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
            return VERSION1_20_5;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R3)) {
            return VERSION1_20_4;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R1)) {
            return VERSION1_20_1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_19_R3)) {
            return VERSION1_19_4;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_19_R1)) {
            return VERSION1_19_2;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_18_R1)) {
            return VERSION1_18_2;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_17_R1)) {
            return VERSION1_17_1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_16_R1)) {
            return VERSION1_16_5;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_12_R1)) {
            return VERSION1_12_2;
        }
        throw new NbtApiException(
                "Trying to update data *to* a version before 1.12.2? Something is probably going wrong, contact the plugin author.");
    }

}
