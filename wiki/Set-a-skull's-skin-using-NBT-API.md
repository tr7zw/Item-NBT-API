
# This is an old example and needs to be updated! Proceed with caution!

We'll be using [this head](https://minecraft-heads.com/custom-heads/decoration/32112-dragonsbreath-opal "Dragonsbreath Opal") for this demonstration.
```java
String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ=="; // Pulled from the head link, scroll to the bottom and the "Other Value" field has this texture id.

ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1); // Creating the ItemStack, your input may vary.
NBTItem nbti = new NBTItem(head); // Creating the wrapper.

NBTCompound disp = nbti.addCompound("display");
disp.setString("Name", "Testitem"); // Setting the name of the Item

NBTList l = disp.getStringList("Lore");
l.add("Some lore"); // Adding a bit of lore.

NBTCompound skull = nbti.addCompound("SkullOwner"); // Getting the compound, that way we can set the skin information
skull.setString("Name", "Dragonsbreath Opal"); // Owner's name
skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
// The UUID, note that skulls with the same UUID but different textures will misbehave and only one texture will load
// (They'll share it), if skulls have different UUIDs and same textures they won't stack. See UUID.randomUUID();

NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
texture.setString("Value",  textureValue);

head = nbti.getItem(); // Refresh the ItemStack
```