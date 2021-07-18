package dev.tr7zw.nbtapi;

/**
 * Enum of all NBT Types Minecraft contains
 * 
 * @author tr7zw
 *
 */
public enum NBTType {
	NBTTagEnd(),
	NBTTagByte(),
	NBTTagShort(),
	NBTTagInt(),
	NBTTagLong(),
	NBTTagFloat(),
	NBTTagDouble(),
	NBTTagByteArray(),
	NBTTagString(),
	NBTTagList(),
	NBTTagCompound(),
	NBTTagIntArray(),
	NBTTagLongArray();

	/**
	 * @return Id used by Minecraft internally
	 */
	public int getId() {
		return ordinal();
	}

	/**
	 * @param id Internal Minecraft ID
	 * @return Enum representing the ID, NBTTagEnd for invalid IDs
	 */
	public static NBTType valueOf(int id) {
		NBTType[] values = values();
		if (id >= values.length || id < 0)
			return NBTTagEnd;
		return values[id];
	}
}
