package de.tr7zw.changeme.nbtapi;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;

import de.tr7zw.annotations.FAUtil;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.annotations.AvailableSince;
import de.tr7zw.changeme.nbtapi.utils.annotations.CheckUtil;

/**
 * NBT class to access vanilla tags from TileEntities. TileEntities don't
 * support custom tags. Use the NBTInjector for custom tags. Changes will be
 * instantly applied to the Tile, use the merge method to do many things at
 * once.
 * 
 * @author tr7zw
 *
 */
public class NBTTileEntity extends NBTCompound {

	private final BlockState tile;

	/**
	 * @param tile BlockState from any TileEntity
	 */
	public NBTTileEntity(BlockState tile) {
		super(null, null);
		if (tile == null || (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3) && !tile.isPlaced())) {
			throw new NullPointerException("Tile can't be null/not placed!");
		}
		this.tile = tile;
	}

	@Override
	public Object getCompound() {
	    if(!Bukkit.isPrimaryThread())throw new NbtApiException("BlockEntity NBT needs to be accessed sync!");
		return NBTReflectionUtil.getTileEntityNBTTagCompound(tile);
	}

	@Override
	protected void setCompound(Object compound) {
	    if(!Bukkit.isPrimaryThread())throw new NbtApiException("BlockEntity NBT needs to be accessed sync!");
		NBTReflectionUtil.setTileEntityNBTTagCompound(tile, compound);
	}

	/**
	 * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
	 * available for 1.14+!
	 * 
	 * @return NBTCompound containing the data of the PersistentDataAPI
	 */
	@AvailableSince(version = MinecraftVersion.MC1_14_R1)
	public NBTCompound getPersistentDataContainer() {
		FAUtil.check(this::getPersistentDataContainer, CheckUtil::isAvaliable);
		if (hasTag("PublicBukkitValues")) {
			return getCompound("PublicBukkitValues");
		} else {
			NBTContainer container = new NBTContainer();
			container.addCompound("PublicBukkitValues").setString("__nbtapi",
					"Marker to make the PersistentDataContainer have content");
			mergeCompound(container);
			return getCompound("PublicBukkitValues");
		}
	}

}
