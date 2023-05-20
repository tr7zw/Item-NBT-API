package de.tr7zw.nbtapi.plugin.tests.proxy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class SimpleProxyTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        TestInterface ti = NBT.wrapNBT(item, TestInterface.class);
        System.out.println(ti.toString());
        ti.setKills(42);
        System.out.println(ti.getKills());
        System.out.println(ti.toString());
    }

    private interface TestInterface {

        public void setKills(int amount);

        public int getKills();

    }

}
