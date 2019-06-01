package de.tr7zw.itemnbtapi;

import org.apache.commons.lang.NotImplementedException;

import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public class NBTCompoundList extends NBTList<NBTListCompound>{

	protected NBTCompoundList(NBTCompound owner, String name, NBTType type, Object list) {
		super(owner, name, type, list);
		// TODO Auto-generated constructor stub
	}

	public NBTListCompound addCompound() {
		try {
			Object compound = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, 0, compound);
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject, compound);
			}
			return new NBTListCompound(this, compound);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}
	
	@Override
	public boolean add(NBTListCompound e) {
		if(e != null) {
			throw new NotImplementedException("You need to pass null! ListCompounds from other lists won't work.");
		}
		try {
			Object compound = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, 0, compound);
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject, compound);
			}
			return true;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public void add(int index, NBTListCompound element) {
		if(element != null) {
			throw new NotImplementedException("You need to pass null! ListCompounds from other lists won't work.");
		}
		try {
			Object compound = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, index, compound);
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject, compound);
			}
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public NBTListCompound get(int index) {
		try {
			Object compound = ReflectionMethod.LIST_GET_COMPOUND.run(listObject, index);
			return new NBTListCompound(this, compound);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		}
	}

	@Override
	public NBTListCompound set(int index, NBTListCompound element) {
		throw new NotImplementedException("This method doesn't work in the ListCompound context.");
	}

}
