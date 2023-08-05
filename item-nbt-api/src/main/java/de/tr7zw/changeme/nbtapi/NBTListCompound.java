package de.tr7zw.changeme.nbtapi;

/**
 * Cut down version of the {@link NBTCompound} for inside
 * {@link NBTCompoundList} This Compound implementation is missing the ability
 * for further subCompounds and Lists. This class probably will change in the
 * future
 * 
 * @author tr7zw
 *
 */
public class NBTListCompound extends NBTCompound {

    private NBTList<?> owner;
    private Object compound;

    protected NBTListCompound(NBTList<?> parent, Object obj) {
        super(null, null);
        owner = parent;
        compound = obj;
    }

    public NBTList<?> getListParent() {
        return owner;
    }

    @Override
    protected boolean isClosed() {
        return owner.getParent().isClosed();
    }

    @Override
    protected boolean isReadOnly() {
        return owner.getParent().isReadOnly();
    }

    @Override
    public Object getCompound() {
        if (isClosed()) {
            throw new NbtApiException("Tried using closed NBT data!");
        }
        return compound;
    }

    @Override
    protected void setCompound(Object compound) {
        if (isClosed()) {
            throw new NbtApiException("Tried using closed NBT data!");
        }
        if (isReadOnly()) {
            throw new NbtApiException("Tried setting data in read only mode!");
        }
        this.compound = compound;
    }

    @Override
    protected void saveCompound() {
        owner.save();
    }

}
