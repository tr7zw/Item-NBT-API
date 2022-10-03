
```Zombie z = (Zombie) b.getWorld().spawnEntity(b.getLocation().add(0, 1, 0), EntityType.ZOMBIE);
NBTEntity nbtent = new NBTEntity(z);
nbtent.setByte("CanPickUpLoot", (byte) 1);
NBTList list = nbtent.getCompoundList("Attributes");
for(int i = 0; i < list.size(); i++){
    NBTListCompound lc = list.getCompound(i);
    if(lc.getString("Name").equals("generic.attackDamage")){
        lc.setDouble("Base", 0.5d);
    }
}