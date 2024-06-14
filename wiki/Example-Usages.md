## Set a skull's skin

// TODO broken link

#### We will be using [this head](https://minecraft-heads.com/custom-heads/food%20&%20drinks/28194-cup-of-soda) as example.

```java
// This is the base64 texture value from the bottom of the previously mentioned website.
final String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyY2M5MjAzYzkwYjg5YmRhYzFkZjI4NDE2NzI2NmI5NTNkZmViZjNjNDY5MGE3Y2QwYjE1NzkxYTYyZTU4MiJ9fX0=";

// Creating ItemStack

// For Minecraft 1.12 and below
final ItemStack item = new ItemStack(Material.SKULL_ITEM);
item.setDurability((short) 3);

// For Minecraft 1.13 and newer
final ItemStack item = new ItemStack(Material.PLAYER_HEAD);

NBT.modify(item, nbt -> {
  final ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");

  // The owner UUID. Note that skulls with the same UUID but different textures will misbehave and only one texture will load.
  // They will share the texture. To avoid this limitation, it is recommended to use a random UUID.
  skullOwnerCompound.setUUID("Id", UUID.randomUUID());

  skullOwnerCompound.getOrCreateCompound("Properties")
    .getCompoundList("textures")
    .addCompound()
    .setString("Value", textureValue);
});

// TODO 1.20.5+ example
// TODO A note/example about alternative in Paper API?
```

## Zombie that can pick up loot and does 0.5 hearts of damage

```java
Zombie zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);
NBT.modify(zombie, nbt -> {
    nbt.setByte("CanPickUpLoot", (byte) 1);
    ReadWriteNBTCompoundList list = nbt.getCompoundList("Attributes");
    for(int i = 0; i < list.size(); i++){
        ReadWriteNBT lc = list.get(i);
        if(lc.getString("Name").equals("generic.attackDamage")){
            lc.setDouble("Base", 0.5d);
        }
    }
});
```

