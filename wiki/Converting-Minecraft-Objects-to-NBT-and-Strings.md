# Converting Minecraft Objects to NBT and Strings

The basic idea is that you can convert data into different representations:
Minecraft Objects <-> NBT <-> String(Mojang-Json)

## Converting Minecraft Objects into NBT

```java
// Items
ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
ReadWriteNBT nbt = NBT.itemStackArrayToNBT(itemStacks);
// Entity
NBTEntity nbte = new NBTEntity(entity);
// Tiles
NBTTileEntity nbtt= new NBTTileEntity(block.getState());
// Gameprofiles
ReadWriteNBT nbt = NBT.gameProfileToNBT(profile);
// NBT Files
NBTFile file = new NBTFile(testFile);
```

## Converting NBT to a String and the String back to NBT

```java
// to String(works with any NBT object)
String str = nbt.toString();
// String back to NBT
ReadWriteNBT nbt = NBT.parseNBT(json);
```

## Recreating the Minecraft Objects from NBT

```java
// Items
ItemStack itemStack = NBT.nbtToItemStack(nbt);
ItemStack[] itemStacks = NBT.nbtToItemStackArray(nbt);
// Entity/Tiles
In general "new NBTEntity(entity).mergeCompound(nbt)", but you might want to remove some data from the nbt first like the Location, uuid and entityId
// Gameprofile
GameProfile profile = NBT.nbtToGameProfile(nbt);
```

