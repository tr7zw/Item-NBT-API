package dev.tr7zw.nbtapi.plugin.tests.legacy.compounds;

import de.tr7zw.nbtapi.NBTContainer;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class RemovingKeys implements Test {

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

		if (comp.getKeys().size() != 10) {
			throw new NbtApiException("Key amount did not match after setting keys!");
		}

		comp.setString(STRING_TEST_KEY, null);
		comp.setInteger(INT_TEST_KEY, null);
		comp.setDouble(DOUBLE_TEST_KEY, null);
		comp.setBoolean(BOOLEAN_TEST_KEY, null);
		comp.setByte(BYTE_TEST_KEY, null);
		comp.setShort(SHORT_TEST_KEY, null);
		comp.setLong(LONG_TEST_KEY, null);
		comp.setFloat(FLOAT_TEST_KEY, null);
		comp.setIntArray(INTARRAY_TEST_KEY, null);
		comp.setByteArray(BYTEARRAY_TEST_KEY, null);

		if (comp.getKeys().size() != 0) {
			throw new NbtApiException("Keys where not removed using the setter with null!");
		}

	}

}
