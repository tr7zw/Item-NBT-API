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
	public Object getCompound() {
		return compound;
	}

	@Override
	protected void setCompound(Object compound) {
		this.compound = compound;
	}

	@Override
	protected void saveCompound() {
		owner.save();
	}

}
