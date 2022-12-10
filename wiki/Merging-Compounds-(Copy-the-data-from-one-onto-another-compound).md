# Merging Compounds
You can "merge" the data from any given Compound onto some other Compound. This will overwrite tags with the same name.

Example:

`Tag1: {Num1:1, Num2:2} Tag2: {Num1:7, Num3:3}`

After merging Tag2 on Tag1:

```java
tag1.mergeCompound(tag2);
```

`Tag1: {Num1:7, Num2:2, Num3:3} Tag2: {Num1:7, Num3:3}`

## Simulate the "/data merge" command

```java
NBT.modify(zombie, nbt -> {
    nbt.mergeCompound(NBT.parseNBT("{Silent:1b,Invulnerable:1b,Glowing:1b,IsBaby:1b}"));
});
```
