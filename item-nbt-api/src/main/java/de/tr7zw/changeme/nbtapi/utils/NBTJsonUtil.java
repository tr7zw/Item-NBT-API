package de.tr7zw.changeme.nbtapi.utils;

import java.util.Optional;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.MojangToMapping;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

public class NBTJsonUtil {

    /**
     * 1.20.3+ only. Used to convert items into Json, used in Chat Hover Components.
     * 
     * @param itemStack
     * @return
     * @throws NbtApiException
     */
    @SuppressWarnings("unchecked")
    public static JsonElement itemStackToJson(ItemStack itemStack) throws NbtApiException {
        try {
            Codec<Object> itemStackCodec = (Codec<Object>) ClassWrapper.NMS_ITEMSTACK.getClazz()
                    .getField(MojangToMapping.getMapping().get("net.minecraft.world.item.ItemStack#CODEC")).get(null);
            Object stack = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, itemStack);
            DataResult<JsonElement> result = itemStackCodec.encode(stack, JsonOps.INSTANCE,
                    JsonOps.INSTANCE.emptyMap());
            Optional<JsonElement> opt = (Optional<JsonElement>) result.getClass().getMethod("result").invoke(result);
            return opt.orElse(null);
        } catch (Exception ex) {
            throw new NbtApiException("Error trying to get Json of an ItemStack.", ex);
        }
    }

}
