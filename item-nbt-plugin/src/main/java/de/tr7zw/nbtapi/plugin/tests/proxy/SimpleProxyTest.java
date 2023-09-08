package de.tr7zw.nbtapi.plugin.tests.proxy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.handler.NBTHandlers;
import de.tr7zw.changeme.nbtapi.wrapper.NBTProxy;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget;
import de.tr7zw.changeme.nbtapi.wrapper.NBTTarget.Type;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class SimpleProxyTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        NBT.modify(item, TestInterface.class, ti -> {
            if(ti.hasKills()) {
                throw new NbtApiException("Item reported to have kills before setting data!");
            }
            ti.setKills(42);
            ti.addKill();
            if(!ti.hasKills()) {
                throw new NbtApiException("Item reported to not have kills after setting data!");
            }
            if (!"{Kills:43}".equals(ti.toString())) {
                throw new NbtApiException("ToString returned the wrong string. " + ti.toString());
            }
        });
        if (new NBTItem(item).getInteger("Kills") != 43) {
            throw new NbtApiException("The item was not modified correctly by the proxy!");
        }
        NBT.modify(item, TestInterface.class, ti -> {
            if (ti.theKillsWithADifferentMethodNameAndNoGet() != 43) {
                throw new NbtApiException("The annotation didn't work correctly!");
            }
            Statistic jumps = ti.getJumps();
            jumps.setPoints(9000);
            jumps.addPoint();
            if (ti.getJumps().getPoints() != 9001) {
                throw new NbtApiException("The stacked proxy didn't work correctly!");
            }
            ItemStack stack = new ItemStack(Material.STONE, 42);
            ti.setItem(stack);
            if (!stack.equals(ti.getItem())) {
                throw new NbtApiException("The handler in the proxy didn't work correctly!");
            }
        });
    }

    public interface TestInterface extends NBTProxy {

        @Override
        default void init() {
            registerHandler(ItemStack.class, NBTHandlers.ITEM_STACK);
        }

        public boolean hasKills();
        
        public void setKills(int amount);

        public int getKills();

        public default void addKill() {
            setKills(getKills() + 1);
        }

        @NBTTarget(value = "Kills", type = Type.GET)
        public int theKillsWithADifferentMethodNameAndNoGet();

        public Statistic getJumps();

        public ItemStack getItem();

        public void setItem(ItemStack item);

    }

    public interface Statistic extends NBTProxy {
        public void setPoints(int amount);

        public int getPoints();

        public default void addPoint() {
            setPoints(getPoints() + 1);
        }

    }

}
