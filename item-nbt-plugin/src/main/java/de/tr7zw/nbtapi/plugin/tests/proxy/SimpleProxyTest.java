package de.tr7zw.nbtapi.plugin.tests.proxy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.wrapper.NBTProxy;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget.Type;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class SimpleProxyTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        TestInterface ti = NBT.wrapNBT(item, TestInterface.class);
        ti.setKills(42);
        ti.addKill();
        if (!"{kills:43}".equals(ti.toString())) {
            throw new NbtApiException("ToString returned the wrong string. " + ti.toString());
        }
        if (new NBTItem(item).getInteger("kills") != 43) {
            throw new NbtApiException("The item was not modified correctly by the proxy!");
        }
        if (ti.theKillsWithADifferentMethodNameAndNoGet() != 43) {
            throw new NbtApiException("The annotation didn't work correctly!");
        }
        Statistic jumps = ti.getJumps();
        jumps.setPoints(9000);
        jumps.addPoint();
        if (ti.getJumps().getPoints() != 9001) {
            throw new NbtApiException("The stacked proxy didn't work correctly!");
        }
    }

    public interface TestInterface extends NBTProxy {

        public void setKills(int amount);

        public int getKills();

        public default void addKill() {
            setKills(getKills() + 1);
        }

        @NBTTarget(value = "kills", type = Type.GET)
        public int theKillsWithADifferentMethodNameAndNoGet();

        public Statistic getJumps();

    }

    public interface Statistic extends NBTProxy {
        public void setPoints(int amount);

        public int getPoints();

        public default void addPoint() {
            setPoints(getPoints() + 1);
        }

    }

}
