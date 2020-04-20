package de.tr7zw.nbtapi.plugin.tests.compounds;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTList;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorTest implements Test {

    @Override
    public void test() throws Exception {
        NBTList<Integer> testList;

        testList = initIntegerList();
        testIterator(testList.iterator());

        testList = initIntegerList();
        testIterator(testList.listIterator());
    }

    private NBTList<Integer> initIntegerList() {
        NBTContainer comp = new NBTContainer();
        NBTList<Integer> list = comp.getIntegerList("test");
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        return list;
    }

    private static void testIterator(Iterator<Integer> iterator) {
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 1);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 2);
        iterator.remove();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 3);
        assertTrue(iterator.hasNext());
        assertTrue(iterator.next() == 4);
        testNoMoreElements(iterator);
    }

    private static void testNoMoreElements(Iterator<Integer> iterator) {
        assertTrue(!iterator.hasNext());
        try {
            iterator.next();
        } catch(NoSuchElementException expected) {
            return;
        } catch(Exception e) {
            throw new NbtApiException("iterator threw wrong exception: " + e.toString());
        }
        throw new NbtApiException("iterator did not throw exception");
    }

    private static void assertTrue(boolean condition) {
        if(!condition) {
            throw new NbtApiException("iterator test failed");
        }
    }

}
