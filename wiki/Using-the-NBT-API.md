
## Using the ``NBT`` class (prefered)

```java
// Setting data on ItemStacks/Entities/BlockEntities
NBT.modify(itemStack, nbt -> {
    nbt.setString("Stringtest", "Teststring");
    nbt.setInteger("Inttest", 42);
    nbt.setDouble("Doubletest", 1.5d);
    nbt.setBoolean("Booleantest", true);
    //More are available! Ask your IDE/Javadocs for suggestions!
});
// Getting data from ItemStacks
String string = NBT.get(itemStack, nbt -> nbt.getString("Stringtest"));
// Updating ItemMeta using NBT
NBT.modify(itemStack, nbt -> {
    nbt.setInteger("kills", nbt.getOrDefault("kills", 0) + 1);
    nbt.modifyMeta((meta, readOnlyNbt) -> { // do not modify the nbt while modifying the meta!
        meta.setDisplayName("Kills: " + readOnlyNbt.getOrDefault("kills", 0));
    });
    //Do more stuff
});

// Gameprofile to NBT
ReadWriteNBT nbt = NBT.gameProfileToNBT(profile);
// NBT to Gameprofile
GameProfile profile = NBT.nbtToGameProfile(nbt);
// ItemStack to NBT
ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
// NBT to ItemStack
ItemStack itemStack = NBT.nbtToItemStack(nbt);
// ItemStack Array to NBT
ReadWriteNBT nbt = NBT.itemStackArrayToNBT(itemStacks);
// NBT to ItemStack Array
ItemStack[] itemStacks = NBT.nbtToItemStackArray(nbt);
// Creating a new NBT tag
ReadWriteNBT nbt = NBT.createNBTObject();
// Parsing a Mojang-Json String to NBT
ReadWriteNBT nbt = NBT.parseNBT(json);

```

## Old style of creating a NBT Wrapper

```java
NBTItem nbti = new NBTItem(ItemStack);

NBTEntity nbtent = new NBTEntity(Entity); // Vanilla tags only!

NBTTileEntity tent = new NBTTileEntity(block.getState()); // Vanilla tags only!

NBTFile file = new NBTFile(new File("directory"), "test.nbt")); // Will be created at ./directory/test.nbt

NBTContainer container =  new NBTContainer(json); // Parse in json

NBTContainer container =  new NBTContainer(); // Empty NBTCompound
```

## Using the wrapper(NBTCompound/)
```java
//Set
nbti.setString("Stringtest", "Teststring");
nbti.setInteger("Inttest", 42);
nbti.setDouble("Doubletest", 1.5d);
nbti.setBoolean("Booleantest", true);
//More are available! Ask your IDE for suggestions! Don't use deprecated methods!

//Access/create lists
nbtent.getCompoundList("Attributes");
nbtent.getStringList("Attributes");
//The list will be created if it doesn't exist, there is no setter for lists!

//Get
nbti.getString("Stringtest");
nbti.getInteger("Inttest");
nbti.getDouble("Doubletest");
nbti.getBoolean("Booleantest");
nbti.getKeys(); //Get all Tags
nbti.hasTag("Key");
nbti.removeKey("Key");

nbti.getOrCreateCompound("subtag"); // Get or create a subtag

NBTCompound comp = nbti.getCompound("subtag"); // Get the tag or null
B.mergeCompound(A); // Merge the data from compound A into B (Like the /data merge command)
comp.toString(); // Get the nbt as a Mojang-Json string
```
