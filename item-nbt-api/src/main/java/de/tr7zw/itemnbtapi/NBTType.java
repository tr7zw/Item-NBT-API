package de.tr7zw.itemnbtapi;

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

    public int getId() {
        return id;
    }

    public static NBTType valueOf(int id) {
        for (NBTType t : values())
            if (t.getId() == id)
                return t;
        return NBTType.NBTTagEnd;
    }

}
