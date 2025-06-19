package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import java.util.Objects;
import java.util.Optional;

import de.tr7zw.changeme.nbtapi.NBTReflectionUtil;
import de.tr7zw.changeme.nbtapi.NbtApiException;

public class CodecHelper {

    public static Object convertItemStackToNbt(Object itemStack) {
        Object result = null;
        try {
            // FIXME caching, this has 0 exception handling
            result = NBTReflectionUtil.itemstack_codec.encodeStart(NBTReflectionUtil.nbtRegistryOps, itemStack);
            Objects.requireNonNull(result);
            return ((Optional<Object>)result.getClass().getMethod("result").invoke(result)).get();
        } catch (Exception e) {
            throw new NbtApiException("Failed to convert ItemStack to NBT. " + result + " " + itemStack, e);
        }
    }
    
    public static Object convertNbtToItemStack(Object nbt) {
        Object result = null;
        try {
            // FIXME caching, this has 0 exception handling
            result = NBTReflectionUtil.itemstack_codec.parse(NBTReflectionUtil.nbtRegistryOps, nbt);
            Objects.requireNonNull(result);
            return ((Optional<Object>)result.getClass().getMethod("result").invoke(result)).get();
        } catch (Exception e) {
            throw new NbtApiException("Failed to convert NBT to ItemStack. " + result + " " + nbt, e);
        }
    }
    
}
