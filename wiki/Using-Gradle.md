# Option 1) NBT-API as a dependency
Add the following Entries to your Gradle build at the correct location:

```groovy
compileOnly("de.tr7zw:item-nbt-api-plugin:VERSION")
```
(Get the current Version from [here](https://www.spigotmc.org/resources/nbt-api.7939/))

```groovy
repositories {
    ...
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    ...
}
```

Finally, add the API as dependency to your ``plugin.yml``
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
<br/>

Add NBT-API to your dependencies:
```groovy
implementation("de.tr7zw:item-nbt-api:VERSION")
```
(Get the current Version from [here](https://www.spigotmc.org/resources/nbt-api.7939/))

```groovy
repositories {
    ...
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    ...
}
```

After this you can add the shadowjar configuration to relocate the api package:
```groovy
shadowJar {
    relocate("de.tr7zw.changeme.nbtapi", "YOUR PACKAGE WHERE THE API SHOULD END UP")
}
```

If you want to run the shadowJar task when the build task is executed, you can write like this:
```groovy
build {
    dependsOn(shadowJar)
}
```