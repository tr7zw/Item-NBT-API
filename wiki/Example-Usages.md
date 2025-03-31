## Set a skull's skin

#### We will be using [this head](https://minecraft-heads.com/custom-heads/head/28194-cup-of-soda) as example.

```java
// This is the base64 texture value from the bottom of the previously mentioned website.
final String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyY2M5MjAzYzkwYjg5YmRhYzFkZjI4NDE2NzI2NmI5NTNkZmViZjNjNDY5MGE3Y2QwYjE1NzkxYTYyZTU4MiJ9fX0=";


// Creating ItemStack

// For Minecraft 1.12.2 and below
final ItemStack item = new ItemStack(Material.SKULL_ITEM);
item.setDurability((short) 3);

// For Minecraft 1.13 and newer
final ItemStack item = new ItemStack(Material.PLAYER_HEAD);


// Applying nbt

// For Minecraft 1.20.4 and below
NBT.modify(item, nbt -> {
    ReadWriteNBT skullOwnerCompound = nbt.getOrCreateCompound("SkullOwner");

    // The owner UUID. Note that skulls with the same UUID but different textures will misbehave and only one texture will load.
    // They will share the texture. To avoid this limitation, it is recommended to use a random UUID.
    skullOwnerCompound.setUUID("Id", UUID.randomUUID());

    skullOwnerCompound.getOrCreateCompound("Properties")
        .getCompoundList("textures")
        .addCompound()
        .setString("Value", textureValue);
});

// Workaround for Minecraft 1.20.5+
NBT.modifyComponents(item, nbt -> {
    ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
    profileNbt.setUUID("id", uuid);
    ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
    propertiesNbt.setString("name", "textures");
    propertiesNbt.setString("value", textureValue);
});
```

> [!TIP]
> If you are using Paper API on 1.12.2+, you may use the following code to create textured skulls

```java
SkullMeta meta = (SkullMeta) item.getItemMeta();
PlayerProfile playerProfile = Bukkit.createProfile(uuid);
playerProfile.setProperty(new ProfileProperty("textures", textureValue));
meta.setPlayerProfile(playerProfile);
// You can also use item.editMeta(SkullMeta.class, meta -> {}); on 1.17+
item.setItemMeta(meta);
```

## Zombie that can pick up loot and does 0.5 hearts of damage

This code should serve only as a reference, the nbt structure might change between versions.

```java
Zombie zombie = location.getWorld().spawn(location, Zombie.class);

String attributeName = "minecraft:generic.attack_damage"; // Or generic.attackDamage prior to 1.16
double damageValue = 0.5;

// Modify vanilla data
NBT.modify(zombie, nbt -> {
    nbt.setBoolean("CanPickUpLoot", true);

    ReadWriteNBTCompoundList list = nbt.getCompoundList("Attributes");

    // Check if zombie already has attribute set. If so, modify it
    for (ReadWriteNBT listEntryNbt : list) {
        if (!listEntryNbt.getString("Name").equals(attributeName)) continue;

        listEntryNbt.setDouble("Base", damageValue);

        return;
    }

    // Attribute is missing, add it instead
    ReadWriteNBT listEntryNbt = list.addCompound();
    listEntryNbt.setString("Name", attributeName);
    listEntryNbt.setDouble("Base", damageValue);
});

// Modify custom data
NBT.modifyPersistentData(zombie, nbt -> {
    // Let's mark our zombie as a custom one
    nbt.setBoolean("custom_zombie", true);
});
```

## Reading world data

```java
// Get main world's folder
File worldDataFolder = Bukkit.getWorlds().getFirst().getWorldFolder();

// Read level data
NBTFileHandle levelNbtFile = NBT.getFileHandle(new File(worldDataFolder, "level.dat"));

// Obtain world name
String worldName = levelNbtFile.resolveOrNull("Data.LevelName", String.class);

// Read some player's data
UUID playerUuid;
File playerFile = new File(worldDataFolder, "playerdata/" + playerUuid + ".dat");
if (!playerFile.exists()) {
    // No offline player data for provided uuid
    return;
}

NBTFileHandle playerNbtFile = NBT.getFileHandle(playerFile);

// Change player's health
float health = playerNbtFile.getFloat("Health");
playerNbtFile.setFloat("Health", health + 5);

// Once finished, save the file
playerNbtFile.save();
```

