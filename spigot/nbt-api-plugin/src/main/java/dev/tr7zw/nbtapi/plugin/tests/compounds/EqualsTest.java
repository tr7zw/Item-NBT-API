package dev.tr7zw.nbtapi.plugin.tests.compounds;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class EqualsTest implements Test{

	@Override
	public void test() throws Exception {
		NBTContainer cont = new NBTContainer();
		cont.setString("hello", "world");
		cont.setInteger("theAnswer", 42);
		cont.addCompound("sub").setString("me", "too");
		cont.getStringList("somelist").addAll(Arrays.asList("a", "b", "c"));
		ItemStack item = new ItemStack(Material.STONE);
		NBTItem nbti = new NBTItem(item);
		NBTCompound customData = nbti.addCompound("customData");
		// reverse order
		customData.addCompound("sub").setString("me", "too");
		customData.setInteger("theAnswer", 42);
		customData.setString("hello", "world");
		customData.getStringList("somelist").addAll(Arrays.asList("a", "b", "c"));
		if(!customData.equals(cont)) {
			throw new NbtApiException("Compounds did not match! " + customData + " " + cont);
		}
		
		// empty test
		
		if(!new NBTContainer().equals(new NBTContainer())) {
		    throw new NbtApiException("Two empty tags did not match!");
		}
		
		// not equal test
		NBTContainer part1 = new NBTContainer();
		part1.setString("a", "a");
		part1.setString("b", "b");
		NBTContainer part2 = new NBTContainer();
        part2.setString("a", "a");
        part2.setString("b", "a");
        if(part1.equals(part2)) {
            throw new NbtApiException("Missmatched nbt did match!");
        }
	}

}
