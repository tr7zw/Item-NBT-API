package dev.tr7zw.nbtapi.plugin.tests.compounds;

import dev.tr7zw.nbtapi.NBTContainer;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class GetterSetterTest implements Test {

	private static final String STRING_TEST_KEY = "stringTest";
	private static final String INT_TEST_KEY = "intTest";
	private static final String DOUBLE_TEST_KEY = "doubleTest";
	private static final String BOOLEAN_TEST_KEY = "booleanTest";
	private static final String SHORT_TEST_KEY = "shortTest";
	private static final String BYTE_TEST_KEY = "byteTest";
	private static final String FLOAT_TEST_KEY = "floatTest";
	private static final String LONG_TEST_KEY = "longTest";
	private static final String INTARRAY_TEST_KEY = "intarrayTest";
	private static final String BYTEARRAY_TEST_KEY = "bytearrayTest";

	private static final String STRING_TEST_VALUE = "TestString";
	private static final int INT_TEST_VALUE = 42;
	private static final double DOUBLE_TEST_VALUE = 1.5d;
	private static final boolean BOOLEAN_TEST_VALUE = true;
	private static final short SHORT_TEST_VALUE = 64;
	private static final byte BYTE_TEST_VALUE = 7;
	private static final float FLOAT_TEST_VALUE = 13.37f;
	private static final long LONG_TEST_VALUE = (long) Integer.MAX_VALUE + 42L;
	private static final int[] INTARRAY_TEST_VALUE = new int[] { 1337, 42, 69 };
	private static final byte[] BYTEARRAY_TEST_VALUE = new byte[] { 8, 7, 3, 2 };

	@Override
	public void test() throws Exception {
		NBTContainer comp = new NBTContainer();

		comp.setString(STRING_TEST_KEY, STRING_TEST_VALUE);
		comp.setInteger(INT_TEST_KEY, INT_TEST_VALUE);
		comp.setDouble(DOUBLE_TEST_KEY, DOUBLE_TEST_VALUE);
		comp.setBoolean(BOOLEAN_TEST_KEY, BOOLEAN_TEST_VALUE);
		comp.setByte(BYTE_TEST_KEY, BYTE_TEST_VALUE);
		comp.setShort(SHORT_TEST_KEY, SHORT_TEST_VALUE);
		comp.setLong(LONG_TEST_KEY, LONG_TEST_VALUE);
		comp.setFloat(FLOAT_TEST_KEY, FLOAT_TEST_VALUE);
		comp.setIntArray(INTARRAY_TEST_KEY, INTARRAY_TEST_VALUE);
		comp.setByteArray(BYTEARRAY_TEST_KEY, BYTEARRAY_TEST_VALUE);

		if (!comp.hasKey(STRING_TEST_KEY)) {
			throw new NbtApiException("Wasn't able to check a key! The Item-NBT-API may not work!");
		}
		if (!(STRING_TEST_VALUE).equals(comp.getString(STRING_TEST_KEY))
				|| comp.getInteger(INT_TEST_KEY) != INT_TEST_VALUE
				|| comp.getDouble(DOUBLE_TEST_KEY) != DOUBLE_TEST_VALUE
				|| comp.getByte(BYTE_TEST_KEY) != BYTE_TEST_VALUE || comp.getShort(SHORT_TEST_KEY) != SHORT_TEST_VALUE
				|| comp.getFloat(FLOAT_TEST_KEY) != FLOAT_TEST_VALUE || comp.getLong(LONG_TEST_KEY) != LONG_TEST_VALUE
				|| comp.getIntArray(INTARRAY_TEST_KEY).length != (INTARRAY_TEST_VALUE).length
				|| comp.getByteArray(BYTEARRAY_TEST_KEY).length != (BYTEARRAY_TEST_VALUE).length
				|| !comp.getBoolean(BOOLEAN_TEST_KEY).equals(BOOLEAN_TEST_VALUE)) {
			throw new NbtApiException("One key does not equal the original value! The Item-NBT-API may not work!");
		}
	}

}
