package de.tr7zw.nbtapi.plugin.tests.proxy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.data.proxy.NBTItemMeta;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class DataItemProxyTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Test");
        item.setItemMeta(meta);
        NBT.modify(item, NBTItemMeta.class, nmeta -> {
            nmeta.setCustomModelData(123);
            nmeta.setUnbreakable(true);
            if (!nmeta.getDisplayData().getRawName().contains("Test")) {
                throw new NbtApiException("Raw name didn't containg the expected String: " + nmeta);
            }
            ReadWriteNBT container = NBT.createNBTObject();
            container.setString("foo", "bar");
            nmeta.setBlockStateTag(container);
            if (!container.equals(nmeta.getBlockStateTag())) {
                throw new NbtApiException("BlockStateTag did not match! " + nmeta);
            }
        });

        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_16_R1)) {
            meta = item.getItemMeta();
            if (!meta.hasCustomModelData() || meta.getCustomModelData() != 123) {
                throw new NbtApiException("Custom Model Data did not match! " + new NBTItem(item));
            }
            if (!meta.isUnbreakable()) {
                throw new NbtApiException("Unbreakable did not set!");
            }
        }
    }

}
