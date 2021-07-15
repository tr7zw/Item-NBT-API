package dev.tr7zw.nbtapi.plugin.tests.legacy.compounds;

import java.util.ListIterator;

import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTListCompound;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

/**
 * Test requested by TAU on Discord
 *
 */
public class ForEachTest implements Test {

	@SuppressWarnings("unused")
	@Override
	public void test() throws Exception {
		NBTContainer comp = new NBTContainer();
        NBTCompoundList compList = comp.getCompoundList("testkey");
        if (compList != null) {
        	compList.addCompound().setInteger("id", 1);
        	compList.addCompound().setInteger("id", 2);
        	compList.addCompound().setInteger("id", 3);
            int count = 0;
            for (NBTListCompound listComp : compList) {
            	count++;  
            }
            if(count != compList.size())
            	throw new NbtApiException("For loop did not get all Entries!");
            count = 0;
            ListIterator<NBTListCompound> lit = compList.listIterator();
            while(lit.hasNext()){
            	lit.next();
            	count++;
            }
            if(count != compList.size())
            	throw new NbtApiException("ListIterator did not get all Entries!");
            count = 0;
            while(lit.hasPrevious()){
            	lit.previous();
            	count++;
            }
            if(count != compList.size())
            	throw new NbtApiException("ListIterator previous did not get all Entries!");
        }
	}

}
