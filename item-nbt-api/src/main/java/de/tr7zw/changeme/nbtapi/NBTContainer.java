package de.tr7zw.changeme.nbtapi;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ObjectCreator;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

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

    public NBTContainer(String nbtString) throws NbtApiException {
        super(null, null);
        try{
            nbt = ReflectionMethod.PARSE_NBT.run(null, nbtString);
        }catch(Exception ex){
            throw new NbtApiException("Unable to parse Malformed Json!", ex);
        }
    }

    @Override
    public Object getCompound() {
        return nbt;
    }

    @Override
    public void setCompound(Object tag) {
        nbt = tag;
    }

}
