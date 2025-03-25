package de.tr7zw.changeme.nbtapi;

/**
 * Enum of all NBT Types Minecraft contains
 * 
 * @author tr7zw
 *
 */
@SuppressWarnings("javadoc")
public enum NBTType {
    NBTTagEnd(0, ""), NBTTagByte(1, "BYTE"), NBTTagShort(2, "SHORT"), NBTTagInt(3, "INT"), NBTTagLong(4, "LONG"), NBTTagFloat(5, "FLOAT"), NBTTagDouble(6, "DOUBLE"),
    NBTTagByteArray(7, "BYTE[]"), NBTTagString(8, "STRING"), NBTTagList(9, "LIST"), NBTTagCompound(10, "COMPOUND"), NBTTagIntArray(11, "INT[]"), NBTTagLongArray(12, "LONG[]");

    NBTType(int i, String name) {
        id = i;
        this.name = name;
    }

    private final int id;
    private final String name;

    /**
     * @return Id used by Minecraft internally
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return Name of the NBTType
     */
    public String getName() {
        return name;
    }

    /**
     * @param id Internal Minecraft id
     * @return Enum representing the id, NBTTagEnd for invalide ids
     */
    public static NBTType valueOf(int id) {
        for (NBTType t : values())
            if (t.getId() == id)
                return t;
        return NBTType.NBTTagEnd;
    }

    public static NBTType fromName(String name) {
        for (NBTType t : values())
            if (t.getName().equals(name))
                return t;
        return NBTType.NBTTagEnd;
    }

}
