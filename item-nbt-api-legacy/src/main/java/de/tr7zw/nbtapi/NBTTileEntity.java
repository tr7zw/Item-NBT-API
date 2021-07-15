package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.block.BlockState;

import de.tr7zw.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.utils.annotations.AvailableSince;

/**
 * NBT class to access vanilla tags from TileEntities. TileEntities don't
 * support custom tags. Use the NBTInjector for custom tags. Changes will be
 * instantly applied to the Tile, use the merge method to do many things at
 * once.
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class NBTTileEntity extends NBTCompound {

	/**
	 * @param tile BlockState from any TileEntity
	 */
	public NBTTileEntity(BlockState tile) {
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
	 * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
	 * available for 1.14+!
	 * 
	 * @return NBTCompound containing the data of the PersistentDataAPI
	 */
	@AvailableSince(version = MinecraftVersion.MC1_14_R1)
	public NBTCompound getPersistentDataContainer() {
	    throw new NotImplementedException();
	}

}
