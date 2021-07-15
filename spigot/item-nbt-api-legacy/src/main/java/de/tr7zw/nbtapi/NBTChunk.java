package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Chunk;

import de.tr7zw.annotations.FAUtil;
import de.tr7zw.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.utils.annotations.AvailableSince;
import de.tr7zw.nbtapi.utils.annotations.CheckUtil;

@Deprecated
public class NBTChunk {

	
	public NBTChunk(Chunk chunk) {
	    throw new NotImplementedException();
	}
	
	/**
	 * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
	 * available for 1.16.4+!
	 * 
	 * @return NBTCompound containing the data of the PersistentDataAPI
	 */
	@AvailableSince(version = MinecraftVersion.MC1_16_R3)
	public NBTCompound getPersistentDataContainer() {
		FAUtil.check(this::getPersistentDataContainer, CheckUtil::isAvaliable);
		throw new NotImplementedException();
	}
	
}
