package de.tr7zw.changeme.nbtapi.utils;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTReflectionUtil;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

public class DataFixerUtil {

    // https://minecraft.wiki/w/Data_version
    public static final int VERSION1_12R1 = 1343;
    public static final int VERSION1_16R1 = 2586;
    public static final int VERSION1_17R1 = 2730;
    public static final int VERSION1_18R1 = 2975;
    public static final int VERSION1_19R1 = 3120;
    public static final int VERSION1_19R3 = 3337;
    public static final int VERSION1_20R1 = 3465;
    public static final int VERSION1_20R2 = 3578;
    public static final int VERSION1_20R3 = 3700;
    public static final int VERSION1_20R4 = 3837;
    public static final int VERSION1_21R1 = 3953;
    public static final int VERSION1_21R2 = 4080;
    public static final int VERSION1_21R3 = 4189;
    public static final int VERSION1_21R4 = 4323;
    public static final int VERSION1_21R5 = 4435;
    // There was a mixup between version numbers and revisions, kept for compatibility, use the above revision numbers
    @Deprecated
    public static final int VERSION1_12_2 = 1343;
    @Deprecated
    public static final int VERSION1_16_5 = 2586;
    @Deprecated
    public static final int VERSION1_17_1 = 2730;
    @Deprecated
    public static final int VERSION1_18_2 = 2975;
    @Deprecated
    public static final int VERSION1_19_2 = 3120;
    @Deprecated
    public static final int VERSION1_19_4 = 3337;
    @Deprecated
    public static final int VERSION1_20_1 = 3465;
    @Deprecated
    public static final int VERSION1_20_2 = 3578;
    @Deprecated
    public static final int VERSION1_20_4 = 3700;
    @Deprecated
    public static final int VERSION1_20_5 = 3837;
    // the 1.21 versions use Revision instead of patch versions, unlike the other above. kept for compatibility
    @Deprecated
    public static final int VERSION1_21 = 3953;
    @Deprecated
    public static final int VERSION1_21_2 = 4080;
    @Deprecated
    public static final int VERSION1_21_3 = 4189;
    @Deprecated
    public static final int VERSION1_21_4 = 4323;
    @Deprecated
    public static final int VERSION1_21_5 = 4435;

    @SuppressWarnings("unchecked")
    public static Object fixUpRawItemData(Object nbt, int fromVersion, int toVersion)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        DataFixer dataFixer = (DataFixer) ReflectionMethod.GET_DATAFIXER.run(null);
        TypeReference itemStackReference = (TypeReference) ReflectionUtil.getMappedField(ClassWrapper.NMS_REFERENCES.getClazz(),"net.minecraft.util.datafix.fixes.References#ITEM_STACK").get(null);
        DynamicOps<Object> nbtOps = (DynamicOps<Object>) ReflectionUtil.getMappedField(ClassWrapper.NMS_NBTOPS.getClazz(), "net.minecraft.nbt.NbtOps#INSTANCE").get(null);
        Dynamic<Object> fixed = dataFixer.update(itemStackReference, new Dynamic<Object>(nbtOps, nbt), fromVersion,
                toVersion);
        return fixed.getValue();
    }

    public static ReadWriteNBT fixUpItemData(ReadWriteNBT nbt, int fromVersion, int toVersion)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return NBT.wrapNMSTag(fixUpRawItemData(
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
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R5)) {
            return VERSION1_21R5;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R4)) {
            return VERSION1_21R4;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R3)) {
            return VERSION1_21R3;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R2)) {
            return VERSION1_21R2;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R1)) {
            return VERSION1_21R1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
            return VERSION1_20R4;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R3)) {
            return VERSION1_20R3;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R1)) {
            return VERSION1_20R1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_19_R3)) {
            return VERSION1_19R3;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_19_R1)) {
            return VERSION1_19R1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_18_R1)) {
            return VERSION1_18R1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_17_R1)) {
            return VERSION1_17R1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_16_R1)) {
            return VERSION1_16R1;
        } else if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_12_R1)) {
            return VERSION1_12R1;
        }
        throw new NbtApiException(
                "Trying to update data *to* a version before 1.12.2? Something is probably going wrong, contact the plugin author.");
    }

}
