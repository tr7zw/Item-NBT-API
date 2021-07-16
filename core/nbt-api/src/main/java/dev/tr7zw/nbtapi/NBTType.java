package dev.tr7zw.nbtapi;

/**
 * Enum of all NBT Types Minecraft contains
 * 
 * @author tr7zw
 *
 */
public enum NBTType {
	NBTTagEnd(0),
    NBTTagByte(1),
	NBTTagShort(2), 
	NBTTagInt(3), 
	NBTTagLong(4), 
	NBTTagFloat(5), 
	NBTTagDouble(6),
	NBTTagByteArray(7), 
	NBTTagIntArray(11), 
	NBTTagString(8), 
	NBTTagList(9), 
	NBTTagCompound(10);

	NBTType(int i) {
		id = i;
	}

	private final int id;

	/**
	 * @return Id used by Minecraft internally
	 */
	public int getId() {
		return id;
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
