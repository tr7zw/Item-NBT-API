package de.tr7zw.changeme.nbtapi;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableItemNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import de.tr7zw.changeme.nbtapi.wrapper.NBTProxy;
import de.tr7zw.changeme.nbtapi.wrapper.ProxyBuilder;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBTList;

/**
 * General utility class for a clean and simple nbt access.
 * 
 * @author tr7zw
 *
 */
public class NBT {

    private NBT() {
        // No instances of NBT. Utility class
    }

    /**
     * Get a read only instance of the items NBT. This method is slightly slower
     * than calling NBT.get due to having to create a copy of the ItemStack, but
     * allows context free access to the data.
     * 
     * @param item
     * @return
     */
    public static ReadableNBT readNbt(ItemStack item) {
        return new NBTItem(item.clone(), false, true, false);
    }

    /**
     * It takes an ItemStack, and a function that takes a ReadableNBT and returns a
     * generic type T. It then returns the result of the function applied to a new
     * NBTItem
     * 
     * @param item   The itemstack you want to get the NBT from
     * @param getter A function that takes a ReadableNBT and returns a value of type
     *               T.
     * @return The function is being returned.
     */
    public static <T> T get(ItemStack item, Function<ReadableItemNBT, T> getter) {
        NBTItem nbt = new NBTItem(item, false, true, false);
        T ret = getter.apply(nbt);
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        nbt.setClosed();
        return ret;
    }

    /**
     * It takes an ItemStack, and a Consumer that takes a ReadableNBT. Applies the
     * Consumer on the NBT of the item
     * 
     * @param item The itemstack you want to get the NBT from
     */
    public static void get(ItemStack item, Consumer<ReadableItemNBT> getter) {
        NBTItem nbt = new NBTItem(item, false, true, false);
        getter.accept(nbt);
        nbt.setClosed();
    }

    /**
     * It takes an entity and a function that takes a ReadableNBT and returns a
     * generic type T, and returns the result of the function
     * 
     * @param entity The entity to get the NBT from
     * @param getter A function that takes a ReadableNBT and returns a value.
     * @return The NBTEntity class is being returned.
     */
    public static <T> T get(Entity entity, Function<ReadableNBT, T> getter) {
        NBTEntity nbt = new NBTEntity(entity, true);
        T ret = getter.apply(nbt);
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        nbt.setClosed();
        return ret;
    }

    /**
     * It takes an Entity, and a Consumer that takes a ReadableNBT. Applies the
     * Consumer on the NBT of the Entity
     * 
     * @param entity The entity to get the NBT from
     */
    public static void get(Entity entity, Consumer<ReadableNBT> getter) {
        NBTEntity nbt = new NBTEntity(entity, true);
        getter.accept(nbt);
        nbt.setClosed();
    }

    /**
     * It takes a block state and a function that takes a readable NBT and returns a
     * value of type T. It then returns the value of the function applied to a new
     * NBTTileEntity created from the block state
     * 
     * @param blockState The block state of the block you want to get the NBT from.
     * @param getter     A function that takes a ReadableNBT and returns a value of
     *                   type T.
     * @return The return type is the same as the type of the getter function.
     */
    public static <T> T get(BlockState blockState, Function<ReadableNBT, T> getter) {
        NBTTileEntity nbt = new NBTTileEntity(blockState, true);
        T ret = getter.apply(nbt);
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        nbt.setClosed();
        return ret;
    }

    /**
     * It takes an BlockEntity, and a Consumer that takes a ReadableNBT. Applies the
     * Consumer on the NBT of the BlockEntity
     * 
     * @param blockState The block state of the block you want to get the NBT from.
     */
    public static void get(BlockState blockState, Consumer<ReadableNBT> getter) {
        NBTTileEntity nbt = new NBTTileEntity(blockState, true);
        getter.accept(nbt);
        nbt.setClosed();
    }

    /**
     * It takes an entity and a function that takes a ReadableNBT and returns a
     * generic type T, and returns the result of the function, applied to the
     * entities persistent data container
     * 
     * @param entity The entity to get the data from
     * @param getter A function that takes a ReadableNBT and returns a value of type
     *               T.
     * @return The return type is T, which is a generic type.
     */
    public static <T> T getPersistentData(Entity entity, Function<ReadableNBT, T> getter) {
        T ret = getter.apply(new NBTEntity(entity).getPersistentDataContainer());
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        return ret;
    }

    /**
     * It takes a block entity and a function that takes a ReadableNBT and returns
     * a generic type T, and returns the result of the function, applied to the
     * block entities persistent data container
     * 
     * @param blockState The block state of the block you want to get the data from.
     * @param getter     A function that takes a ReadableNBT and returns a value of
     *                   type T.
     * @return The value of the NBT tag.
     */
    public static <T> T getPersistentData(BlockState blockState, Function<ReadableNBT, T> getter) {
        T ret = getter.apply(new NBTTileEntity(blockState).getPersistentDataContainer());
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        return ret;
    }

    /**
     * It takes an ItemStack, applies a function to its NBT, and returns the result
     * of the function
     * 
     * @param item     The item you want to modify
     * @param function The function that will be applied to the item.
     * @return The return value of the function.
     */
    public static <T> T modify(ItemStack item, Function<ReadWriteItemNBT, T> function) {
        NBTItem nbti = new NBTItem(item, false, false, true);
        T val = function.apply(nbti);
        nbti.finalizeChanges();
        if (val instanceof ReadableNBT || val instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        nbti.setClosed();
        return val;
    }

    /**
     * It takes an ItemStack and a Consumer&lt;ReadWriteNBT&gt;, and then applies
     * the Consumer to the ItemStacks NBT
     * 
     * @param item     The item you want to modify
     * @param consumer The consumer that will be used to modify the NBT.
     */
    public static void modify(ItemStack item, Consumer<ReadWriteItemNBT> consumer) {
        NBTItem nbti = new NBTItem(item, false, false, true);
        consumer.accept(nbti);
        nbti.finalizeChanges();
        nbti.setClosed();
    }

    /**
     * It takes an entity and a function that takes a ReadWriteNBT and returns a
     * generic type T. It then returns the result of the function
     * 
     * @param entity   The entity to modify
     * @param function The function that will be called.
     * @return The return type is the same as the return type of the function.
     */
    public static <T> T modify(Entity entity, Function<ReadWriteNBT, T> function) {
        NBTEntity nbtEnt = new NBTEntity(entity);
        NBTContainer cont = new NBTContainer(nbtEnt.getCompound());
        T ret = function.apply(cont);
        nbtEnt.setCompound(cont.getCompound());
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        nbtEnt.setClosed();
        return ret;
    }

    /**
     * It takes an entity and a function that takes a ReadWriteNBT and applies the
     * function to the entity
     * 
     * @param entity   The entity to modify
     * @param consumer The consumer that will be called with the NBTEntity.
     */
    public static void modify(Entity entity, Consumer<ReadWriteNBT> consumer) {
        NBTEntity nbtEnt = new NBTEntity(entity);
        NBTContainer cont = new NBTContainer(nbtEnt.getCompound());
        consumer.accept(cont);
        nbtEnt.setCompound(cont.getCompound());
        nbtEnt.setClosed();
    }

    /**
     * It takes an entity and a function that takes a ReadWriteNBT from the entities
     * persistent data and returns a generic type T. It then returns the result of
     * the function
     * 
     * @param entity   The entity to modify the data of.
     * @param function The function that will be called.
     * @return The return type is the same as the return type of the function.
     */
    public static <T> T modifyPersistentData(Entity entity, Function<ReadWriteNBT, T> function) {
        T ret = function.apply(new NBTEntity(entity).getPersistentDataContainer());
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        return ret;
    }

    /**
     * It allows you to modify the persistent data of an entity without any return
     * value
     * 
     * @param entity   The entity to modify
     * @param consumer The consumer that will be used to modify the persistent data.
     */
    public static void modifyPersistentData(Entity entity, Consumer<ReadWriteNBT> consumer) {
        consumer.accept(new NBTEntity(entity).getPersistentDataContainer());
    }

    /**
     * It takes a block state and a function that takes a ReadWriteNBT and returns a
     * generic type T. It then returns the result of the function
     * 
     * @param blockState The blockstate you want to modify
     * @param function   The function that will be called.
     * @return The return type is the same as the return type of the function.
     */
    public static <T> T modify(BlockState blockState, Function<ReadWriteNBT, T> function) {
        NBTTileEntity blockEnt = new NBTTileEntity(blockState);
        NBTContainer cont = new NBTContainer(blockEnt.getCompound());
        T ret = function.apply(cont);
        blockEnt.setCompound(cont.getCompound());
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        blockEnt.setClosed();
        return ret;
    }

    /**
     * It takes a block state and a consumer, and then it creates a new
     * NBTTileEntity object with the block state, and then it passes that
     * NBTTileEntity object to the consumer
     * 
     * @param blockState The blockstate you want to modify
     * @param consumer   A Consumer&lt;ReadWriteNBT&gt;. This is a function that
     *                   takes a ReadWriteNBT and does something with it.
     */
    public static void modify(BlockState blockState, Consumer<ReadWriteNBT> consumer) {
        NBTTileEntity blockEnt = new NBTTileEntity(blockState);
        NBTContainer cont = new NBTContainer(blockEnt.getCompound());
        consumer.accept(cont);
        blockEnt.setCompound(cont.getCompound());
        blockEnt.setClosed();
    }

    /**
     * It takes a block state and a function that takes a ReadWriteNBT of the block
     * entities persistent data and returns a generic type T. It then returns the
     * result of the function
     * 
     * @param blockState The block state of the block you want to modify.
     * @param function   The function that will be called to modify the NBT data.
     * @return The return type is the same as the return type of the function.
     */
    public static <T> T modifyPersistentData(BlockState blockState, Function<ReadWriteNBT, T> function) {
        T ret = function.apply(new NBTTileEntity(blockState).getPersistentDataContainer());
        if (ret instanceof ReadableNBT || ret instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        return ret;
    }

    /**
     * It takes a block state and a consumer, and then it calls the consumer with
     * the persistent data container of the block entity
     * 
     * @param blockState The block state of the block you want to modify.
     * @param consumer   A Consumer&lt;ReadWriteNBT&gt;. This is a function that
     *                   takes a ReadWriteNBT and does something with it.
     */
    public static void modifyPersistentData(BlockState blockState, Consumer<ReadWriteNBT> consumer) {
        consumer.accept(new NBTTileEntity(blockState).getPersistentDataContainer());
    }

    /**
     * It converts a GameProfile object to a ReadWriteNBT object
     * 
     * @param profile The GameProfile to convert to NBT
     * @return A ReadWriteNBT object.
     */
    public static ReadWriteNBT gameProfileToNBT(GameProfile profile) {
        return NBTGameProfile.toNBT(profile);
    }

    /**
     * It takes a NBT compound and returns a GameProfile
     * 
     * @param compound The NBT tag to read the GameProfile from.
     * @return A GameProfile object.
     */
    public static GameProfile gameProfileFromNBT(ReadableNBT compound) {
        return NBTGameProfile.fromNBT((NBTCompound) compound);
    }

    /**
     * It converts an ItemStack to a ReadWriteNBT object
     * 
     * @param itemStack The item stack you want to convert to NBT.
     * @return A ReadWriteNBT object.
     */
    public static ReadWriteNBT itemStackToNBT(ItemStack itemStack) {
        return NBTItem.convertItemtoNBT(itemStack);
    }

    /**
     * It converts a ReadableNBT object into an ItemStack
     * 
     * @param compound The NBT tag to convert to an ItemStack
     * @return An ItemStack
     */
    @Nullable
    public static ItemStack itemStackFromNBT(ReadableNBT compound) {
        return NBTItem.convertNBTtoItem((NBTCompound) compound);
    }

    /**
     * It converts an array of ItemStacks into a ReadWriteNBT object
     * 
     * @param itemStacks The ItemStack[] you want to convert to NBT
     * @return An NBTItem object.
     */
    public static ReadWriteNBT itemStackArrayToNBT(ItemStack[] itemStacks) {
        return NBTItem.convertItemArraytoNBT(itemStacks);
    }

    /**
     * It converts a ReadableNBT object into an array of ItemStacks
     * 
     * @param compound The NBT tag to convert to an ItemStack array.
     * @return An array of ItemStacks.
     */
    @Nullable
    public static ItemStack[] itemStackArrayFromNBT(ReadableNBT compound) {
        return NBTItem.convertNBTtoItemArray((NBTCompound) compound);
    }

    /**
     * Create a new NBTContainer object and return it.
     * 
     * @return A new instance of the NBTContainer class.
     */
    public static ReadWriteNBT createNBTObject() {
        return new NBTContainer();
    }

    /**
     * It takes a nbt json string, and returns a ReadWriteNBT object
     * 
     * @param nbtString The NBT string to parse.
     * @return A new NBTContainer object.
     */
    public static ReadWriteNBT parseNBT(String nbtString) {
        return new NBTContainer(nbtString);
    }

    /**
     * Create a read only proxy class for NBT, given an annotated interface.
     * 
     * @param <T>
     * @param item
     * @param wrapper
     * @return
     */
    public static <T extends NBTProxy> T readNbt(ItemStack item, Class<T> wrapper) {
        return new ProxyBuilder<>(new NBTItem(item, false, true, false), wrapper).readOnly().build();
    }

    /**
     * Create a read only proxy class for NBT, given an annotated interface.
     * 
     * @param <T>
     * @param entity
     * @param wrapper
     * @return
     */
    public static <T extends NBTProxy> T readNbt(Entity entity, Class<T> wrapper) {
        return new ProxyBuilder<>(new NBTEntity(entity, true), wrapper).readOnly().build();
    }

    /**
     * Create a read only proxy class for NBT, given an annotated interface.
     * 
     * @param <T>
     * @param blockState
     * @param wrapper
     * @return
     */
    public static <T extends NBTProxy> T readNbt(BlockState blockState, Class<T> wrapper) {
        return new ProxyBuilder<>(new NBTTileEntity(blockState, true), wrapper).readOnly().build();
    }

    /**
     * It takes an ItemStack, applies a function to its NBT wrapped in a proxy, and
     * returns the result of the function
     * 
     * @param item     The item you want to
     * @param wrapper  The target Proxy class
     * @param function The function that will be applied to the item.
     * @return The return value of the function.
     */
    public static <T, X extends NBTProxy> T modify(ItemStack item, Class<X> wrapper, Function<X, T> function) {
        NBTItem nbti = new NBTItem(item, false, false, true);
        T val = function.apply(new ProxyBuilder<>(nbti, wrapper).build());
        nbti.finalizeChanges();
        if (val instanceof ReadableNBT || val instanceof ReadableNBTList<?>) {
            throw new NbtApiException("Tried returning part of the NBT to outside of the NBT scope!");
        }
        nbti.setClosed();
        return val;
    }

    /**
     * It takes an ItemStack, applies a function to its NBT wrapped in a proxy.
     * 
     * @param item     The item you want to modify
     * @param wrapper  The target Proxy class
     * @param consumer The consumer that will be used to modify the NBT.
     */
    public static <X extends NBTProxy> void modify(ItemStack item, Class<X> wrapper, Consumer<X> consumer) {
        NBTItem nbti = new NBTItem(item, false, false, true);
        consumer.accept(new ProxyBuilder<>(nbti, wrapper).build());
        nbti.finalizeChanges();
        nbti.setClosed();
    }

    /**
     * It takes an entity and a function to modify the entity via the proxy
     * 
     * @param entity   The entity to modify
     * @param wrapper  The target Proxy class
     * @param consumer The consumer that will be called with the proxy.
     */
    public static <X extends NBTProxy> void modify(Entity entity, Class<X> wrapper, Consumer<X> consumer) {
        NBTEntity nbtEnt = new NBTEntity(entity);
        NBTContainer cont = new NBTContainer(nbtEnt.getCompound());
        consumer.accept(new ProxyBuilder<>(cont, wrapper).build());
        nbtEnt.setCompound(cont.getCompound());
        cont.setClosed();
    }

    /**
     * It takes an entity and a function to modify the entity via the proxy
     * 
     * @param entity   The entity to modify
     * @param wrapper  The target Proxy class
     * @param function The Function that will be called with the proxy.
     * @return The return value of the function.
     */
    public static <T, X extends NBTProxy> T modify(Entity entity, Class<X> wrapper, Function<X, T> function) {
        NBTEntity nbtEnt = new NBTEntity(entity);
        NBTContainer cont = new NBTContainer(nbtEnt.getCompound());
        T val = function.apply(new ProxyBuilder<>(cont, wrapper).build());
        nbtEnt.setCompound(cont.getCompound());
        cont.setClosed();
        return val;
    }

    /**
     * It takes an block entity and a function to modify the entity via the proxy
     * 
     * @param blockState The blockstate you want to modify
     * @param wrapper    The target Proxy class
     * @param consumer   The Consumer that will be called.
     */
    public static <X extends NBTProxy> void modify(BlockState blockState, Class<X> wrapper, Consumer<X> consumer) {
        NBTTileEntity blockEnt = new NBTTileEntity(blockState);
        NBTContainer cont = new NBTContainer(blockEnt.getCompound());
        consumer.accept(new ProxyBuilder<>(cont, wrapper).build());
        blockEnt.setCompound(cont);
        cont.setClosed();
    }

    /**
     * It takes an block entity and a function to modify the entity via the proxy
     * 
     * @param blockState The blockstate you want to modify
     * @param wrapper    The target Proxy class
     * @param function   The function that will be called.
     * @return The return value of the function.
     */
    public static <T, X extends NBTProxy> T modify(BlockState blockState, Class<X> wrapper, Function<X, T> function) {
        NBTTileEntity blockEnt = new NBTTileEntity(blockState);
        NBTContainer cont = new NBTContainer(blockEnt.getCompound());
        T val = function.apply(new ProxyBuilder<>(cont, wrapper).build());
        blockEnt.setCompound(cont);
        cont.setClosed();
        return val;
    }

}
