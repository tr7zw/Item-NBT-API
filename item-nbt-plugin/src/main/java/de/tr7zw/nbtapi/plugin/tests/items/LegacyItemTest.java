package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.utils.DataFixerUtil;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class LegacyItemTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = NBT
                .itemStackFromNBT(NBT.parseNBT("{id:cobblestone,Count:42,tag:{Enchantments:[{lvl:3,id:unbreaking}]}}"));
        if (item.getType() != Material.COBBLESTONE || item.getAmount() != 42
                || item.getEnchantmentLevel(Enchantment.DURABILITY) != 3) {
            throw new NbtApiException("1.20 item didn't load correctly! " + item);
        }
        ReadWriteNBT nbt = NBT
                .parseNBT("{id:cobblestone,Count:42,tag:{display:{Name:\"test\"},ench:[{lvl:3,id:34}]}}");
        nbt = DataFixerUtil.fixUpItemData(nbt, DataFixerUtil.VERSION1_12_2, DataFixerUtil.getCurrentVersion());
        item = NBT.itemStackFromNBT(nbt);
        if (item.getType() != Material.COBBLESTONE || item.getAmount() != 42
                || item.getEnchantmentLevel(Enchantment.DURABILITY) != 3
                || !"test".equals(item.getItemMeta().getDisplayName())) {
            throw new NbtApiException("1.12.2 item didn't load correctly! " + item);
        }

        ItemStack item2 = NBT.itemStackFromNBT(NBT.parseNBT("{DataVersion:" + DataFixerUtil.VERSION1_12_2 + ",id:cobblestone,Count:42,tag:{display:{Name:\"test\"},ench:[{lvl:3,id:34}]}}"));
        if (!item.equals(item2))
            throw new NbtApiException("Data-fixed 1.12.2 item didn't load correctly! " + item2);
    }

}
