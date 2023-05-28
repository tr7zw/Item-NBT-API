package de.tr7zw.nbtapi.plugin.tests.proxy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class SimpleProxyTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        TestInterface ti = NBT.wrapNBT(item, TestInterface.class);
        ti.setKills(42);
        ti.addKill();
        if(!"{kills:43}".equals(ti.toString())) {
            throw new NbtApiException("ToString returned the wrong string. " + ti.toString());
        }
        if (new NBTItem(item).getInteger("kills") != 43) {
            throw new NbtApiException("The item was not modified correctly by the proxy!");
        }
    }

    public interface TestInterface {

        public void setKills(int amount);

        public int getKills();

        public default void addKill() {
            setKills(getKills() + 1);
        }

    }

}
