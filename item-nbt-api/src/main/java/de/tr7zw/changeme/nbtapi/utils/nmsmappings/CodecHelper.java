package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import java.util.Objects;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.ReflectionUtil;

public class CodecHelper {

    public static Object convertItemStackToNbt(Object itemStack) {
        try {
            // FIXME caching, this has 0 exception handling
            DynamicOps<Object> nbtOps = (DynamicOps<Object>) ReflectionUtil.getMappedField(ClassWrapper.NMS_NBTOPS.getClazz(), "net.minecraft.nbt.NbtOps#INSTANCE").get(null);
            Codec<Object> codec = (Codec<Object>) ReflectionUtil.getMappedField(ClassWrapper.NMS_ITEMSTACK.getClazz(), "net.minecraft.world.item.ItemStack#CODEC").get(null);
            Object result = codec.encodeStart(nbtOps, itemStack);
            Objects.requireNonNull(result);
            return ((Optional<Object>)result.getClass().getMethod("result").invoke(result)).get();
        } catch (Exception e) {
            throw new NbtApiException("Failed to convert ItemStack to NMS", e);
        }
    }
    
    public static Object convertNbtToItemStack(Object nbt) {
        try {
            // FIXME caching, this has 0 exception handling
            DynamicOps<Object> nbtOps = (DynamicOps<Object>) ReflectionUtil.getMappedField(ClassWrapper.NMS_NBTOPS.getClazz(), "net.minecraft.nbt.NbtOps#INSTANCE").get(null);
            Codec<Object> codec = (Codec<Object>) ReflectionUtil.getMappedField(ClassWrapper.NMS_ITEMSTACK.getClazz(), "net.minecraft.world.item.ItemStack#CODEC").get(null);
            Object result = codec.parse(nbtOps, nbt);
            Objects.requireNonNull(result);
            return ((Optional<Object>)result.getClass().getMethod("result").invoke(result)).get();
        } catch (Exception e) {
            throw new NbtApiException("Failed to convert ItemStack to NMS", e);
        }
    }
    
}
