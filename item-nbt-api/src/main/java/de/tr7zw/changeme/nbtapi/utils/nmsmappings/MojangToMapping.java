package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import java.util.HashMap;
import java.util.Map;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

/**
 * Temporary solution to hold Mojang to unmapped Spigot mappings.
 * Note years later: nothing is more permanent than a temporary solution.
 * 
 * @author tr7zw
 *
 */
public class MojangToMapping {

    @SuppressWarnings("serial")
    private static Map<String, String> MC1_18R1 = new HashMap<String, String>() {

        {
            put("net.minecraft.nbt.CompoundTag#contains(java.lang.String)", "e");
            put("net.minecraft.nbt.CompoundTag#getCompound(java.lang.String)", "p");
            put("net.minecraft.nbt.CompoundTag#getList(java.lang.String,int)", "c");
            put("net.minecraft.nbt.CompoundTag#putByteArray(java.lang.String,byte[])", "a");
            put("net.minecraft.nbt.CompoundTag#getDouble(java.lang.String)", "k");
            put("net.minecraft.nbt.CompoundTag#putDouble(java.lang.String,double)", "a");
            put("net.minecraft.nbt.CompoundTag#getByteArray(java.lang.String)", "m");
            put("net.minecraft.nbt.CompoundTag#putInt(java.lang.String,int)", "a");
            put("net.minecraft.nbt.CompoundTag#getIntArray(java.lang.String)", "n");
            put("net.minecraft.nbt.CompoundTag#remove(java.lang.String)", "r");
            put("net.minecraft.nbt.CompoundTag#get(java.lang.String)", "c");
            put("net.minecraft.nbt.CompoundTag#merge(net.minecraft.nbt.CompoundTag)", "a");
            put("net.minecraft.nbt.CompoundTag#putBoolean(java.lang.String,boolean)", "a");
            put("net.minecraft.nbt.CompoundTag#getTagType(java.lang.String)", "d");
            put("net.minecraft.nbt.CompoundTag#putLong(java.lang.String,long)", "a");
            put("net.minecraft.nbt.CompoundTag#putLongArray(java.lang.String,long[])", "a");
            put("net.minecraft.nbt.CompoundTag#getLongArray(java.lang.String)", "o");
            put("net.minecraft.nbt.CompoundTag#getString(java.lang.String)", "l");
            put("net.minecraft.nbt.CompoundTag#getInt(java.lang.String)", "h");
            put("net.minecraft.nbt.CompoundTag#putString(java.lang.String,java.lang.String)", "a");
            put("net.minecraft.nbt.CompoundTag#put(java.lang.String,net.minecraft.nbt.Tag)", "a");
            put("net.minecraft.nbt.CompoundTag#getByte(java.lang.String)", "f");
            put("net.minecraft.nbt.CompoundTag#putIntArray(java.lang.String,int[])", "a");
            put("net.minecraft.nbt.CompoundTag#getShort(java.lang.String)", "g");
            put("net.minecraft.nbt.CompoundTag#putByte(java.lang.String,byte)", "a");
            put("net.minecraft.nbt.CompoundTag#getAllKeys()", "d");
            put("net.minecraft.nbt.CompoundTag#putUUID(java.lang.String,java.util.UUID)", "a");
            put("net.minecraft.nbt.CompoundTag#putShort(java.lang.String,short)", "a");
            put("net.minecraft.nbt.CompoundTag#getLong(java.lang.String)", "i");
            put("net.minecraft.nbt.CompoundTag#putFloat(java.lang.String,float)", "a");
            put("net.minecraft.nbt.CompoundTag#getBoolean(java.lang.String)", "q");
            put("net.minecraft.nbt.CompoundTag#getUUID(java.lang.String)", "a");
            put("net.minecraft.nbt.CompoundTag#getFloat(java.lang.String)", "j");
            put("net.minecraft.nbt.ListTag#addTag(int,net.minecraft.nbt.Tag)", "b");
            put("net.minecraft.nbt.ListTag#setTag(int,net.minecraft.nbt.Tag)", "a");
            put("net.minecraft.nbt.ListTag#getString(int)", "j");
            put("net.minecraft.nbt.ListTag#remove(int)", "remove");
            put("net.minecraft.nbt.ListTag#getCompound(int)", "a");
            put("net.minecraft.nbt.ListTag#size()", "size");
            put("net.minecraft.nbt.ListTag#get(int)", "get");
            put("net.minecraft.nbt.NbtIo#readCompressed(java.io.InputStream)", "a");
            put("net.minecraft.nbt.NbtIo#writeCompressed(net.minecraft.nbt.CompoundTag,java.io.OutputStream)", "a");
            put("net.minecraft.nbt.NbtUtils#readGameProfile(net.minecraft.nbt.CompoundTag)", "a");
            put("net.minecraft.nbt.NbtUtils#writeGameProfile(net.minecraft.nbt.CompoundTag,com.mojang.authlib.GameProfile)",
                    "a");
            put("net.minecraft.nbt.TagParser#parseTag(java.lang.String)", "a");
            put("net.minecraft.world.entity.Entity#getEncodeId()", "bk");
            put("net.minecraft.world.entity.Entity#load(net.minecraft.nbt.CompoundTag)", "g");
            put("net.minecraft.world.entity.Entity#saveWithoutId(net.minecraft.nbt.CompoundTag)", "f");
            put("net.minecraft.world.item.ItemStack#setTag(net.minecraft.nbt.CompoundTag)", "c");
            put("net.minecraft.world.item.ItemStack#getTag()", "s");
            put("net.minecraft.world.item.ItemStack#save(net.minecraft.nbt.CompoundTag)", "b");
            put("net.minecraft.world.level.block.entity.BlockEntity#saveWithId()", "n");
            put("net.minecraft.world.level.block.entity.BlockEntity#getBlockState()", "q");
            put("net.minecraft.world.level.block.entity.BlockEntity#load(net.minecraft.nbt.CompoundTag)", "a");
            put("net.minecraft.server.level.ServerLevel#getBlockEntity(net.minecraft.core.BlockPos)", "c_");
        }

    };

    @SuppressWarnings("serial")
    private static Map<String, String> MC1_18R2 = new HashMap<String, String>() {

        {
            putAll(MC1_18R1);

            put("net.minecraft.world.item.ItemStack#getTag()", "t");
        }
    };

    @SuppressWarnings("serial")
    private static Map<String, String> MC1_19R1 = new HashMap<String, String>() {

        {
            putAll(MC1_18R2);

            put("net.minecraft.world.item.ItemStack#getTag()", "u");
        }

    };

    @SuppressWarnings("serial")
    private static Map<String, String> MC1_19R2 = new HashMap<String, String>() {

        {
            putAll(MC1_19R1);

            put("net.minecraft.nbt.CompoundTag#getAllKeys()", "e");
        }

    };

    @SuppressWarnings("serial")
    private static Map<String, String> MC1_20R1 = new HashMap<String, String>() {

        {
            putAll(MC1_19R2);

            put("net.minecraft.world.entity.Entity#getEncodeId()", "br");
            put("net.minecraft.world.item.ItemStack#getTag()", "v");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_20R2 = new HashMap<String, String>() {

        {
            putAll(MC1_20R1);

            put("net.minecraft.world.entity.Entity#getEncodeId()", "bu");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_20R3 = new HashMap<String, String>() {

        {
            putAll(MC1_20R2);

            put("net.minecraft.nbt.NbtIo#readCompressed(java.io.InputStream,net.minecraft.nbt.NbtAccounter)", "a");
            put("net.minecraft.nbt.NbtAccounter#unlimitedHeap()", "a");
            put("net.minecraft.world.entity.Entity#getEncodeId()", "bw");
            put("net.minecraft.world.level.block.entity.BlockEntity#saveWithId()", "p");
            put("net.minecraft.world.level.block.entity.BlockEntity#getBlockState()", "r");
            put("net.minecraft.world.item.ItemStack#CODEC", "a");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_20R4 = new HashMap<String, String>() {

        {
            putAll(MC1_20R3);

            put("net.minecraft.world.entity.Entity#getEncodeId()", "bC");
            put("net.minecraft.world.level.block.entity.BlockEntity#getBlockState()", "n");
            put("net.minecraft.core.component.DataComponents#CUSTOM_DATA", "b");
            put("net.minecraft.core.component.DataComponentHolder#get(net.minecraft.core.component.DataComponentType)", "a");
            put("net.minecraft.world.item.component.CustomData#copyTag()", "c");
            put("net.minecraft.world.item.ItemStack#set(net.minecraft.core.component.DataComponentType,java.lang.Object)", "b");
            put("net.minecraft.world.item.ItemStack#save(net.minecraft.core.HolderLookup$Provider)", "a");
            put("net.minecraft.server.MinecraftServer#registryAccess()", "bc");
            put("net.minecraft.world.item.ItemStack#parseOptional(net.minecraft.core.HolderLookup$Provider,net.minecraft.nbt.CompoundTag)", "a");
            put("net.minecraft.world.level.block.entity.BlockEntity#saveWithId(net.minecraft.core.HolderLookup$Provider)", "c");
            put("net.minecraft.world.level.block.entity.BlockEntity#loadWithComponents(net.minecraft.nbt.CompoundTag,net.minecraft.core.HolderLookup$Provider)", "c");
            put("net.minecraft.util.datafix.DataFixers#getDataFixer()", "a");
            put("net.minecraft.util.datafix.fixes.References#ITEM_STACK", "t");
            put("net.minecraft.nbt.NbtOps#INSTANCE", "a");
            put("net.minecraft.world.item.ItemStack#CODEC", "b");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_21R1 = new HashMap<String, String>() {

        {
            putAll(MC1_20R4);

            put("net.minecraft.world.entity.Entity#getEncodeId()", "bD");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_21R2 = new HashMap<String, String>() {

        {
            putAll(MC1_21R1);

            put("net.minecraft.server.MinecraftServer#registryAccess()", "ba");
            put("net.minecraft.world.entity.Entity#getEncodeId()", "bK");
            put("net.minecraft.world.level.block.entity.BlockEntity#getBlockState()", "m");
            put("net.minecraft.world.item.ItemStack#CODEC", "a");
        }

    };

    @SuppressWarnings("serial")
    private static Map<String, String> MC1_21R3 = new HashMap<String, String>() {

        {
            putAll(MC1_21R2);

            put("net.minecraft.world.item.component.CustomData#copyTag()", "d");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_21R4 = new HashMap<String, String>() {

        {
            putAll(MC1_21R3);

            put("net.minecraft.nbt.CompoundTag#getShort(java.lang.String)", "d");
            put("net.minecraft.nbt.CompoundTag#getString(java.lang.String)", "i");
            put("net.minecraft.nbt.CompoundTag#contains(java.lang.String)", "b");
            put("net.minecraft.nbt.CompoundTag#getDouble(java.lang.String)", "h");
            put("net.minecraft.nbt.CompoundTag#getByteArray(java.lang.String)", "j");
            put("net.minecraft.nbt.CompoundTag#getFloat(java.lang.String)", "g");
            put("net.minecraft.nbt.CompoundTag#get(java.lang.String)", "a");
            put("net.minecraft.nbt.CompoundTag#getLong(java.lang.String)", "f");
            put("net.minecraft.nbt.CompoundTag#getLongArray(java.lang.String)", "l");
            put("net.minecraft.nbt.CompoundTag#getInt(java.lang.String)", "e");
            put("net.minecraft.nbt.CompoundTag#getIntArray(java.lang.String)", "k");
            put("net.minecraft.nbt.CompoundTag#getCompound(java.lang.String)", "m");
            put("net.minecraft.nbt.CompoundTag#getByte(java.lang.String)", "c");
            put("net.minecraft.nbt.ListTag#getString(int)", "m");
            put("net.minecraft.world.entity.Entity#saveWithoutId(net.minecraft.nbt.CompoundTag)", "h");
            put("net.minecraft.world.entity.Entity#getEncodeId()", "bI");
            put("net.minecraft.world.entity.Entity#load(net.minecraft.nbt.CompoundTag)", "i");
            put("net.minecraft.nbt.Tag#getType()", "c");
            put("net.minecraft.nbt.TagType#getName()", "a");
            put("net.minecraft.nbt.CompoundTag#getList(java.lang.String)", "o");
            put("net.minecraft.world.item.ItemStack#parse(net.minecraft.core.HolderLookup$Provider,net.minecraft.nbt.Tag)", "a");
            put("net.minecraft.world.item.ItemStack#CODEC", "b");
        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_21R5 = new HashMap<String, String>() {

        {
            putAll(MC1_21R3);
            put("net.minecraft.core.HolderLookup$Provider#createSerializationContext(com.mojang.serialization.DynamicOps)", "a");
            put("net.minecraft.nbt.CompoundTag#getByteArray(java.lang.String)", "j");
            put("net.minecraft.nbt.CompoundTag#getDouble(java.lang.String)", "h");
            put("net.minecraft.nbt.CompoundTag#keySet()", "e");
            put("net.minecraft.nbt.CompoundTag#getLong(java.lang.String)", "f");
            put("net.minecraft.nbt.CompoundTag#getList(java.lang.String)", "o");
            put("net.minecraft.nbt.CompoundTag#getInt(java.lang.String)", "e");
            put("net.minecraft.nbt.CompoundTag#getCompound(java.lang.String)", "m");
            put("net.minecraft.nbt.CompoundTag#getByte(java.lang.String)", "c");
            put("net.minecraft.nbt.CompoundTag#getShort(java.lang.String)", "d");
            put("net.minecraft.nbt.CompoundTag#getIntArray(java.lang.String)", "k");
            put("net.minecraft.nbt.CompoundTag#get(java.lang.String)", "a");
            put("net.minecraft.nbt.CompoundTag#getString(java.lang.String)", "i");
            put("net.minecraft.nbt.CompoundTag#getFloat(java.lang.String)", "g");
            put("net.minecraft.nbt.CompoundTag#getLongArray(java.lang.String)", "l");
            put("net.minecraft.nbt.CompoundTag#contains(java.lang.String)", "b");
            put("net.minecraft.nbt.ListTag#getString(int)", "m");
            put("net.minecraft.nbt.Tag#getType()", "c");
            put("net.minecraft.nbt.TagParser#parseCompoundFully(java.lang.String)", "a");
            put("net.minecraft.nbt.TagType#getName()", "a");
            put("net.minecraft.world.entity.Entity#load(net.minecraft.world.level.storage.ValueInput)", "e");
            put("net.minecraft.world.entity.Entity#getEncodeId()", "bN");
            put("net.minecraft.world.entity.Entity#saveWithoutId(net.minecraft.world.level.storage.ValueOutput)", "d");
            put("net.minecraft.world.level.block.entity.BlockEntity#saveWithId(net.minecraft.world.level.storage.ValueOutput)", "d");
            put("net.minecraft.world.level.block.entity.BlockEntity#loadWithComponents(net.minecraft.world.level.storage.ValueInput)", "b");
            put("net.minecraft.world.level.storage.TagValueInput#create(net.minecraft.util.ProblemReporter,net.minecraft.core.HolderLookup$Provider,net.minecraft.nbt.CompoundTag)", "a");
            put("net.minecraft.world.level.storage.TagValueOutput#createWithContext(net.minecraft.util.ProblemReporter,net.minecraft.core.HolderLookup$Provider)", "a");
            put("net.minecraft.world.level.storage.TagValueOutput#buildResult()", "b");
            put("net.minecraft.world.item.ItemStack#CODEC", "b");
            put("net.minecraft.util.ProblemReporter#DISCARDING", "a");
            put("net.minecraft.util.datafix.fixes.References#ITEM_STACK", "u");

        }

    };
    
    @SuppressWarnings("serial")
    private static Map<String, String> MC1_21R6 = new HashMap<String, String>() {

        {
            putAll(MC1_21R5);

            put("net.minecraft.server.MinecraftServer#registryAccess()", "bg");
            put("net.minecraft.world.entity.Entity#load(net.minecraft.world.level.storage.ValueInput)", "d");
            put("net.minecraft.world.entity.Entity#getEncodeId()", "bW");
            put("net.minecraft.world.item.component.CustomData#copyTag()", "b");
            put("net.minecraft.world.level.block.entity.BlockEntity#getBlockState()", "o");
            put("net.minecraft.util.datafix.fixes.References#ITEM_STACK", "v");

        }

    };

    public static Map<String, String> getMapping() {
        switch (MinecraftVersion.getVersion()) {
        case MC1_21_R6:
            return MC1_21R6;
        case MC1_21_R5:
            return MC1_21R5;
        case MC1_21_R4:
            return MC1_21R4;
        case MC1_21_R3:
            return MC1_21R3;
        case MC1_21_R2:
            return MC1_21R2;
        case MC1_21_R1:
            return MC1_21R1;
        case MC1_20_R4:
            return MC1_20R4;
        case MC1_20_R3:
            return MC1_20R3;
        case MC1_20_R2:
            return MC1_20R2;
        case MC1_20_R1:
            return MC1_20R1;
        case MC1_19_R3:
            return MC1_19R2;
        case MC1_19_R2:
            return MC1_19R2;
        case MC1_19_R1:
            return MC1_19R1;
        case MC1_18_R2:
            return MC1_18R2;
        case MC1_18_R1:
            return MC1_18R1;
        case UNKNOWN:
            return MC1_20R2; // assume it's a future version, so try the latest known mappings
        default:
            // this should never happen, unless a version is forgotten here(like 1.19R3 which uses the 1.19R2 mappings)
            throw new NbtApiException("No fitting mapping found for version " + MinecraftVersion.getVersion() + ". This is a bug!");
        }
    }

}
