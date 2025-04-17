package de.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.tests.Test;

import java.util.Arrays;

public class CompoundDifferenceTest implements Test {

	@Override
	public void test() throws Exception {
		ReadWriteNBT nbt1 = NBT.createNBTObject();
		nbt1.setInteger("intTag", 1);
		ReadWriteNBT tmp = NBT.createNBTObject();
		tmp.setInteger("foo", 1);
		nbt1.getCompoundList("compoundList1").addCompound(tmp);
		nbt1.setIntArray("intArray", new int[]{1, 2, 3});
		tmp = NBT.createNBTObject();
		tmp.setIntArray("foo", new int[]{1, 2, 3});
		nbt1.getCompoundList("compoundList2").addCompound(tmp);
		nbt1.getOrCreateCompound("compoundTag").setFloat("floatTag", 20F);
		nbt1.getOrCreateCompound("compoundTag").setBoolean("booleanTag", true);
		nbt1.getOrCreateCompound("compoundTag").getDoubleList("doubleList").addAll(Arrays.asList(1D, 2D, 3D));
		nbt1.getOrCreateCompound("compoundTag").getFloatList("floatList").addAll(Arrays.asList(1F, 2F));
		nbt1.getOrCreateCompound("compoundTagTwo").setString("stringTag", "string");
		ReadWriteNBT nbt2 = NBT.createNBTObject();
		nbt2.setInteger("intTag", 1);
		tmp = NBT.createNBTObject();
		tmp.setInteger("foo", 1);
		nbt2.getCompoundList("compoundList1").addCompound(tmp);
		nbt2.setInteger("alsoIntTag", 2);
		tmp = NBT.createNBTObject();
		tmp.setInteger("foo", 2);
		nbt2.getCompoundList("compoundList2").addCompound(tmp);
		nbt2.getOrCreateCompound("compoundTag").setFloat("floatTag", 20F);
		nbt2.getOrCreateCompound("compoundTag").setBoolean("booleanTag", false);
		nbt2.getOrCreateCompound("compoundTag").getDoubleList("doubleList").addAll(Arrays.asList(1D, 2D, 3D, 4D));
		nbt2.getOrCreateCompound("compoundTag").getFloatList("floatList").addAll(Arrays.asList(1F, 2F));
		nbt2.getOrCreateCompound("compoundTagTwo").setString("stringTag", "string");

		ReadWriteNBT expectedDiff1 = NBT.createNBTObject();
		tmp = NBT.createNBTObject();
		tmp.setIntArray("foo", new int[]{1, 2, 3});
		expectedDiff1.getCompoundList("compoundList2").addCompound(tmp);
		expectedDiff1.setIntArray("intArray", new int[]{1, 2, 3});
		expectedDiff1.getOrCreateCompound("compoundTag").setBoolean("booleanTag", true);
		expectedDiff1.getOrCreateCompound("compoundTag").getDoubleList("doubleList").addAll(Arrays.asList(1D, 2D, 3D));
		ReadWriteNBT expectedDiff2 = NBT.createNBTObject();
		tmp = NBT.createNBTObject();
		tmp.setInteger("foo", 2);
		expectedDiff2.getCompoundList("compoundList2").addCompound(tmp);
		expectedDiff2.setInteger("alsoIntTag", 2);
		expectedDiff2.getOrCreateCompound("compoundTag").setBoolean("booleanTag", false);
		expectedDiff2.getOrCreateCompound("compoundTag").getDoubleList("doubleList").addAll(Arrays.asList(1D, 2D, 3D, 4D));

		ReadWriteNBT diff1 = nbt1.extractDifference(nbt2);
		ReadWriteNBT diff2 = nbt2.extractDifference(nbt1);

		if (!expectedDiff1.equals(diff1)) {
			throw new NbtApiException("Diff1: Compounds did not match! " + expectedDiff1 + " " + diff1);
		}
		if (!expectedDiff2.equals(diff2)) {
			throw new NbtApiException("Diff2: Compounds did not match! " + expectedDiff2 + " " + diff2);
		}
	}

}
