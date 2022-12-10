# Welcome to the NBT API wiki

NBT API has been tested on Spigot
1.7.10-1.19+

Full JavaDoc can be found [here](https://tr7zw.github.io/Item-NBT-API/v2-api/)!

### What do I have to do as a server owner?
Just download the jar and drop it in the plugins folder. Done.
Note that outdated plugins may ask for "ItemNBTAPI". In this case, download version 1.8.3 from the versions tab. The outdated "ItemNBTAPI" and "NBTAPI" can be used at the same time.

### 1.7 Notes
* Use 1.7.10
* NBTLists may not work
* NBTTypes don't work as 1.7.x is missing this feature.
* TLDR: 1.7.10 is a bit broken and not everything will work! Also it's not supported anymore!

### Don't reload the NBT-Injector
Reloading in general is a horrible thing and it will break the NBTInjector in horrible ways! When updating plugins/changing configs always restart the server normally!

### How can I use the API as a developer?
See [Using the NBT API](https://github.com/tr7zw/Item-NBT-API/wiki/Using-the-NBT-API)