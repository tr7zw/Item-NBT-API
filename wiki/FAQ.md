## Frequently Asked Questions

### I've updated NBT-API, but is keeps saying that it's outdated

// TODO

### I've downloaded NBT-API, but the plugin keeps asking for ItemNBTAPI

The plugin uses a very outdated version of NBT-API. In this case, download the version 1.8.3 from the versions tab. The outdated "ItemNBTAPI" and "NBTAPI" can be used both at the same time.

### 1.7 support

- Use 1.7.10.
- NBTLists may not work.
- NBTTypes don't work as 1.7.x is missing this feature.
- TLDR: 1.7.10 is a bit broken and not everything will work! Also, it's not supported anymore!

### Where did NBTInjector go?

The experimental NBTInjector became unsupported since Minecraft 1.14 and was removed in 2.13.0 with Minecraft 1.21 release.

NBTInjector is incompatible with reloads and may break things, and thus is not recommended.

If you're using Minecraft 1.14, you should switch to the persistent storage API instead.

In versions prior to 1.14, you may use a workaround like storing data inside an item's nbt that the entity is wearing (e.g. having a button in mob's helmet, and storing data on that button).
