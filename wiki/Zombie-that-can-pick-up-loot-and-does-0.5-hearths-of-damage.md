```java
Zombie zombie = (Zombie) b.getWorld().spawnEntity(b.getLocation().add(0, 1, 0), EntityType.ZOMBIE);
NBT.modify(zombie, nbt -> {
    nbt.setByte("CanPickUpLoot", (byte) 1);
    ReadWriteNBTCompoundList list = nbt.getCompoundList("Attributes");
    for(int i = 0; i < list.size(); i++){
        ReadWriteNBT lc = list.get(i);
        if(lc.getString("Name").equals("generic.attackDamage")){
            lc.setDouble("Base", 0.5d);
        }
    }
});
```

