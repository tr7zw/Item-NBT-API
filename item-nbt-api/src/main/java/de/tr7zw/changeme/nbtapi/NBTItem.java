package de.tr7zw.changeme.nbtapi;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * NBT class to access vanilla/custom tags on ItemStacks.
 * 
 * @author tr7zw
 *
 */
public class NBTItem extends NBTCompound {

	private ItemStack bukkitItem;

	/**
	 * Constructor for NBTItems. The ItemStack will be cloned!<br/>
	 * Use {@link NBTItem#getItem()} to get the changed ItemStack
	 * 
	 * @param item
	 */
	public NBTItem(ItemStack item) {
		this(item, true);
	}
	
	/**
	 * Constructor for NBTItems. The ItemStack will be cloned if copy is true.
	 * 
	 * @param item
	 * @param copy
	 */
	public NBTItem(ItemStack item, boolean copy) {
		super(null, null);
		if (item == null) {
			throw new NullPointerException("ItemStack can't be null!");
		}
		bukkitItem = (copy ? item.clone() : item);
	}
	
	@Override
	public Object getCompound() {
		return NBTReflectionUtil.getItemRootNBTTagCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, bukkitItem));
	}

	@Override
	protected void setCompound(Object compound) {
		Object stack = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, bukkitItem);
		ReflectionMethod.ITEMSTACK_SET_TAG.run(stack, compound);
		bukkitItem = (ItemStack) ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, stack);
	}

	/**
	 * @return The modified ItemStack
	 */
	public ItemStack getItem() {
		return bukkitItem;
	}

	protected void setItem(ItemStack item) {
		bukkitItem = item;
	}

	/**
	 * This may return true even when the NBT is empty.
	 * 
	 * @return Does the ItemStack have a NBTCompound.
	 */
	public boolean hasNBTData() {
		return getCompound() != null;
	}

	/**
	 * Helper method that converts {@link ItemStack} to {@link NBTContainer} with
	 * all it's data like Material, Damage, Amount and Tags.
	 * 
	 * @param item
	 * @return Standalone {@link NBTContainer} with the Item's data
	 */
	public static NBTContainer convertItemtoNBT(ItemStack item) {
		return NBTReflectionUtil.convertNMSItemtoNBTCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, item));
	}

	/**
	 * Helper method to do the inverse to "convertItemtoNBT". Creates an
	 * {@link ItemStack} using the {@link NBTCompound}
	 * 
	 * @param comp
	 * @return ItemStack using the {@link NBTCompound}'s data
	 */
	public static ItemStack convertNBTtoItem(NBTCompound comp) {
		return (ItemStack) ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null,
				NBTReflectionUtil.convertNBTCompoundtoNMSItem(comp));
	}

}
