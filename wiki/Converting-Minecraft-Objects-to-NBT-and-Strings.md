# Converting Minecraft Objects to NBT and Strings
The basic idea is that you can convert data into different representations:
Minecraft Objects <-> NBT <-> String(Mojang-Json)

### Converting Minecraft Objects into NBT
```
// Items
NBTCompound itemData = NBTItem.convertItemtoNBT(item);
// Entity
NBTEntity nbte = new NBTEntity(entity);
// Tiles
NBTTileEntity nbtt= new NBTTileEntity(block.getState());
// Gameprofiles
NBTCompound nbt = NBTGameProfile.toNBT(profile)
// NBT Files
NBTFile file = new NBTFile(testFile);
```

### Converting NBT to a String and the String back to NBT
```
// to String
String str = nbtCompound.toString();
// String back to NBT
NBTContainer cont = new NBTContainer(str);
```

### Recreating the Minecraft Objects from NBT
```
// Items
ItemStack rebuild = NBTItem.convertNBTtoItem(nbt);
// Entity/Tiles
In general "new NBTEntity(entity).mergeCompound(nbt)", but you might want to remove some data from the nbt first like the Location, uuid and entityId
// Gameprofile
GameProfile profile = NBTGameProfile.fromNBT(nbt);
```