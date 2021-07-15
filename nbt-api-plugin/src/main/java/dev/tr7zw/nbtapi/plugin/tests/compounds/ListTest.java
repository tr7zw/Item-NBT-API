package dev.tr7zw.nbtapi.plugin.tests.compounds;

import java.util.Arrays;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTCompoundList;
import dev.tr7zw.nbtapi.NBTContainer;
import dev.tr7zw.nbtapi.NBTList;
import dev.tr7zw.nbtapi.NBTListCompound;
import dev.tr7zw.nbtapi.NBTType;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class ListTest implements Test {

	@Override
	public void test() throws Exception {
		NBTContainer comp = new NBTContainer();

		// Strings
		NBTList<String> list = comp.getStringList("testlist");
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");
		list.set(2, "test42");
		list.remove(1);
		if (!list.get(1).equals("test42") || list.size() != 3) {
			throw new NbtApiException("The String-list did not match what it should have looked like.");
		}

		// Compound
		NBTCompoundList taglist = comp.getCompoundList("complist");
		NBTListCompound lcomp = taglist.addCompound();
		lcomp.setDouble("double1", 0.3333);
		lcomp.setInteger("int1", 42);
		lcomp.setString("test1", "test1");
		lcomp.setString("test2", "test2");
		lcomp.removeKey("test1");
		NBTCompound subsubcomp = lcomp.addCompound("listsubkey");
		subsubcomp.setString("deep", "String");
		subsubcomp.getCompoundList("deeplist").addCompound().setString("test", "test");
		subsubcomp.getCompoundList("clonelist").addCompound(comp);

		taglist = null;
		lcomp = null;
		subsubcomp = null;

		taglist = comp.getCompoundList("complist");
		if (taglist.size() == 1) {
			lcomp = taglist.get(0);
			if (lcomp.getKeys().size() != 4) {
				throw new NbtApiException("Wrong key amount in Taglist (" + lcomp.getKeys().size() + ")!");
			} else if (!(lcomp.getDouble("double1") == 0.3333 && lcomp.getInteger("int1") == 42
					&& lcomp.getString("test2").equals("test2") && !lcomp.hasKey("test1"))) {
				throw new NbtApiException("One key in the Taglist changed! The Item-NBT-API may not work!");
			} else if (lcomp.getCompound("listsubkey") == null
					|| !"String".equals(lcomp.getCompound("listsubkey").getString("deep"))) {
				throw new NbtApiException(
						"The Compound nested in the listcompound was not correct! The Item-NBT-API may not work!");
			} else if (lcomp.getCompound("listsubkey").getType("deep") != NBTType.NBTTagString) {
				throw new NbtApiException("The nested key's type wasn't correct! The Item-NBT-API may not work!");
			} else if (lcomp.getCompound("listsubkey").getType("deeplist") != NBTType.NBTTagList) {
				throw new NbtApiException("The nested list's type wasn't correct '"
						+ lcomp.getCompound("listsubkey").getType("deeplist") + "'! The Item-NBT-API may not work!");
			}
		} else {
			throw new NbtApiException("Taglist is empty! The Item-NBT-API may not work!");
		}
		
		if(comp.getListType("complist") != NBTType.NBTTagCompound) {
			throw new NbtApiException("complist had the wrong type! The Item-NBT-API may not work!");
		}

		// Integer
		NBTList<Integer> intlist = comp.getIntegerList("inttest");
		intlist.add(42);
		intlist.add(69);
		if (intlist.size() == 2 && intlist.get(0) == 42 && intlist.get(1) == 69) {
			// ok
		} else {
			throw new NbtApiException("IntList is not correct! " + Arrays.toString(intlist.toArray(new Integer[0])));
		}
		
		if(comp.getListType("inttest") != NBTType.NBTTagInt) {
			throw new NbtApiException("inttest had the wrong type! The Item-NBT-API may not work!");
		}

		// Double
		NBTList<Double> doublelist = comp.getDoubleList("doubletest");
		doublelist.add(42.23d);
		doublelist.add(69.69d);
		if (doublelist.size() == 2 && doublelist.get(0) == 42.23d && doublelist.get(1) == 69.69d) {
			// ok
		} else {
			throw new NbtApiException("DoubleList is not correct! " + Arrays.toString(doublelist.toArray(new Double[0])));
		}
		
		if(comp.getListType("doubletest") != NBTType.NBTTagDouble) {
			throw new NbtApiException("doubletest had the wrong type! The Item-NBT-API may not work!");
		}

		// Float
		NBTList<Float> floatlist = comp.getFloatList("floattest");
		floatlist.add(42.23f);
		floatlist.add(69.69f);
		if (floatlist.size() == 2 && floatlist.get(0) == 42.23f && floatlist.get(1) == 69.69f) {
			// ok
		} else {
			throw new NbtApiException("FloatList is not correct! " + Arrays.toString(floatlist.toArray(new Float[0])));
		}
		
		if(comp.getListType("floattest") != NBTType.NBTTagFloat) {
			throw new NbtApiException("floattest had the wrong type! The Item-NBT-API may not work!");
		}

		// Long
		NBTList<Long> longlist = comp.getLongList("longtest");
		longlist.add(1241234124124l);
		longlist.add(1231454321312l);
		if (longlist.size() == 2 && longlist.get(0) == 1241234124124l && longlist.get(1) == 1231454321312l) {
			// ok
		} else {
			throw new NbtApiException("LongList is not correct! " + Arrays.toString(longlist.toArray(new Long[0])));
		}

		if(comp.getListType("longtest") != NBTType.NBTTagLong) {
			throw new NbtApiException("longtest had the wrong type! The Item-NBT-API may not work!");
		}
		
	}

}
