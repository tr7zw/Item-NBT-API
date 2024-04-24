package de.tr7zw.nbtapi.plugin.tests.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class LegacyItemTest implements Test {

    @Override
    public void test() throws Exception {
        ItemStack item = NBT.itemStackFromNBT(NBT.parseNBT("{id:cobblestone,Count:42,tag:{Enchantments:[{lvl:3,id:unbreaking}]}}"));
        if(item.getType() != Material.COBBLESTONE || item.getAmount() != 42 || item.getEnchantmentLevel(Enchantment.DURABILITY) != 3) {
            throw new NbtApiException("1.20 item didn't load correctly! " + item);
        }
    }

}
