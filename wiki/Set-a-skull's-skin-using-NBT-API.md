
We will be using [this head](https://minecraft-heads.com/custom-heads/food%20&%20drinks/28194-cup-of-soda) as example.

```java
// This is base64 texture value from buttom of the previosly mentioned website.
final String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyY2M5MjAzYzkwYjg5YmRhYzFkZjI4NDE2NzI2NmI5NTNkZmViZjNjNDY5MGE3Y2QwYjE1NzkxYTYyZTU4MiJ9fX0=";

// Creating ItemStack

// 1.12 and above
final ItemStack item = new ItemStack(Material.SKULL_ITEM);
item.setDurability((short) 3);

// 1.13 and newer
final ItemStack item = new ItemStack(Material.PLAYER_HEAD);

NBT.modify(item, nbt -> {
  final ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");

  // The owner UUID, note that skulls with the same UUID but different textures will misbehave and only one texture will load,
  // they'll share it. So you should just use random UUID to avoid this limitation.
  skullOwnerCompound.setUUID("Id", UUID.randomUUID())

  skullOwnerCompound.getOrCreateCompound("Properties")
    .getCompoundList("textures")
    .addCompound()
    .setString("Value", textureValue);
});
```
