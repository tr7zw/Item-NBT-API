package de.tr7zw.changeme.nbtapi.utils;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTReflectionUtil;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.MojangToMapping;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

public class DataFixerUtil {

    public static final int VERSION1_20_4 = 3817; // 1.20.4
    public static final int VERSION1_20_5 = 3825; // 1.20.5

    @SuppressWarnings("unchecked")
    public static Object fixUpRawItemData(Object nbt, int fromVersion, int toVersion)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        DataFixer dataFixer = (DataFixer) ReflectionMethod.GET_DATAFIXER.run(null);
        TypeReference itemStackReference = (TypeReference) ClassWrapper.NMS_REFERENCES.getClazz()
                .getField(MojangToMapping.getMapping().get("net.minecraft.util.datafix.fixes.References#ITEM_STACK"))
                .get(null);
        DynamicOps<Object> nbtOps = (DynamicOps<Object>) ClassWrapper.NMS_NBTOPS.getClazz()
                .getField(MojangToMapping.getMapping().get("net.minecraft.nbt.NbtOps#INSTANCE")).get(null);
        Dynamic<Object> fixed = dataFixer.update(itemStackReference, new Dynamic<Object>(nbtOps, nbt), fromVersion, toVersion);
        return fixed.getValue();
    }
    
    public static NBTCompound fixUpItemData(NBTCompound nbt, int fromVersion, int toVersion)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return new NBTContainer(fixUpRawItemData(NBTReflectionUtil.getToCompount(nbt.getCompound(), nbt), fromVersion, toVersion));
    }

}
