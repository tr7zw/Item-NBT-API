package de.tr7zw.changeme.nbtapi;

public class NBTContainer extends NBTCompound{

    private Object nbt;

    public NBTContainer() {
        super(null, null);
        nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
    }
    
    public NBTContainer(Object nbt){
        super(null, null);
        this.nbt = nbt;
    }

    public NBTContainer(String nbtString) throws IllegalArgumentException {
        super(null, null);
        try{
            nbt = ReflectionMethod.PARSE_NBT.run(null, nbtString);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new IllegalArgumentException("Malformed Json: " + ex.getMessage());
        }
    }

    public Object getCompound() {
        return nbt;
    }

    public void setCompound(Object tag) {
        nbt = tag;
    }

}
