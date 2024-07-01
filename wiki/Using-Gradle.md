To start using NB-API, you either need to depend on its plugin version, or shade (include) it inside your plugin.

> [!IMPORTANT]
> Plugin and shaded versions have different ``artifactId``. Make sure to correctly choose the one you need!

# Option 1) NBT-API as a dependency

Add the following entries to your Gradle build at the correct locations:

```groovy
repositories {
    ...
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
    ...
}
```

```groovy
compileOnly("de.tr7zw:item-nbt-api-plugin:VERSION")
```

(Get the current ``VERSION`` from [here](https://modrinth.com/plugin/nbtapi/versions))

Add the API as dependency to your ``plugin.yml``:

```yml
depend: [NBTAPI]
```

# Option 2) Shading the NBT-API into your plugin

To include NBT-API directly in your plugin, you can use the gradle shadow plugin.

Add the plugin to the build configuration, as shown here:

```groovy
plugins {
    id("com.github.johnrengelman.shadow") version "VERSION"
}
```

The latest version of the shadow plugin can be found [here](https://github.com/johnrengelman/shadow/releases).

Add NBT-API to your dependencies:

```groovy
repositories {
    ...
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
    ...
}
```

```groovy
implementation("de.tr7zw:item-nbt-api:VERSION")
```

(Get the current ``VERSION`` from [here](https://modrinth.com/plugin/nbtapi/versions))

> [!WARNING]
> Make sure you're using ``item-nbt-api`` as ``artifactId``, never shade the ``-plugin`` artifact!

After this you can add the shadowJar configuration to relocate the API package:

```groovy
shadowJar {
    relocate("de.tr7zw.changeme.nbtapi", "YOUR PACKAGE WHERE THE API SHOULD END UP")
}
```

If you want to run the shadowJar task when the build task is executed, you can use this:

```groovy
build {
    dependsOn(shadowJar)
}
```

###### Initializing NBT-API early

If you are shading NBT-API, you may call ``NBT.preloadApi()`` during ``onEnable`` to initialize NBT-API early and check whether everything works. If you omit this step, NBT-API will be initialized on the first call to the API.

```java
@Override
public void onEnable() {
    if (!NBT.preloadApi()) {
        getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
        getPluginLoader().disablePlugin(this);
        return;
    }
    // Load other things
}
```

