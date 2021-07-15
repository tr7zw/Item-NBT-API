package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * NBT class to access vanilla/custom tags on ItemStacks. This class doesn't
 * autosave to the Itemstack, use getItem to get the changed ItemStack
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTItem extends NBTCompound {

	/**
	 * Constructor for NBTItems. The ItemStack will be cloned!
	 * 
	 * @param item
	 */
	public NBTItem(ItemStack item) {
		this(item, false);
	}
	
	/**
	 * Constructor for NBTItems. The ItemStack will be cloned! If directApply is true,
	 * all changed will be mapped to the original item. Changes to the NBTItem will overwrite changes done
	 * to the original item in that case.
	 * 
	 * @param item
	 * @param directApply
	 */
	public NBTItem(ItemStack item, boolean directApply) {
		super(null, null);
		throw new NotImplementedException();
	}

	@Override
	public Object getCompound() {
	    throw new NotImplementedException();
	}

	@Override
	protected void setCompound(Object compound) {
	    throw new NotImplementedException();
	}

	/**
	 * Apply stored NBT tags to the provided ItemStack.
	 * <p>
	 * Note: This will completely override current item's {@link ItemMeta}.
	 * If you still want to keep the original item's NBT tags, see
	 * {@link #mergeNBT(ItemStack)} and {@link #mergeCustomNBT(ItemStack)}.
	 *
	 * @param item ItemStack that should get the new NBT data
	 */
	public void applyNBT(ItemStack item) {
	    throw new NotImplementedException();
	}

	/**
	 * Merge all NBT tags to the provided ItemStack.
	 *
	 * @param item ItemStack that should get the new NBT data
	 */
	public void mergeNBT(ItemStack item) {
	    throw new NotImplementedException();
	}

	/**
	 * Merge only custom (non-vanilla) NBT tags to the provided ItemStack.
	 *
	 * @param item ItemStack that should get the new NBT data
	 */
	public void mergeCustomNBT(ItemStack item) {
	    throw new NotImplementedException();
	}

	
	/**
	 * True, if the item has any tags now known for this item type.
	 * 
	 * @return true when custom tags are present
	 */
	public boolean hasCustomNbtData() {
	    throw new NotImplementedException();
	}
	
	/**
	 * Remove all custom (non-vanilla) NBT tags from the NBTItem.
	 */
	public void clearCustomNBT() {
	    throw new NotImplementedException();
	}

	/**
	 * @return The modified ItemStack
	 */
	public ItemStack getItem() {
	    throw new NotImplementedException();
	}

	protected void setItem(ItemStack item) {
	    throw new NotImplementedException();
	}

	/**
	 * This may return true even when the NBT is empty.
	 * 
	 * @return Does the ItemStack have a NBTCompound.
	 */
	public boolean hasNBTData() {
	    throw new NotImplementedException();
	}

	/**
	 * Helper method that converts {@link ItemStack} to {@link NBTContainer} with
	 * all it's data like Material, Damage, Amount and Tags.
	 * 
	 * @param item
	 * @return Standalone {@link NBTContainer} with the Item's data
	 */
	public static NBTContainer convertItemtoNBT(ItemStack item) {
	    throw new NotImplementedException();
	}

	/**
	 * Helper method to do the inverse to "convertItemtoNBT". Creates an
	 * {@link ItemStack} using the {@link NBTCompound}
	 * 
	 * @param comp
	 * @return ItemStack using the {@link NBTCompound}'s data
	 */
	public static ItemStack convertNBTtoItem(NBTCompound comp) {
	    throw new NotImplementedException();
	}

	@Override
	protected void saveCompound() {
	    throw new NotImplementedException();
	}

}
