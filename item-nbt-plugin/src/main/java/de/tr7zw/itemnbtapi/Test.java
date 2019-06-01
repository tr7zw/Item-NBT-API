package de.tr7zw.itemnbtapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Test implements Listener{

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event) {
		NBTCompound comp = NBTInjector.getNbtData(event.getEntity());
		System.out.println(comp);
		System.out.println(comp.asNBTString());
		comp.setInteger("hits", comp.getInteger("hits") + 1);
		event.getDamager().sendMessage("Hits: " + comp.getInteger("hits"));
	}

	@EventHandler
	public void onAttack(PlayerInteractEvent event) {
		if(event.getClickedBlock() != null && event.getClickedBlock().getState() != null) {
			NBTCompound comp = NBTInjector.getNbtData(event.getClickedBlock().getState());
			if(comp != null) {
				System.out.println(comp);
				System.out.println(comp.asNBTString());
				comp.setInteger("uses", comp.getInteger("uses") + 1);
				event.getPlayer().sendMessage("Uses: " + comp.getInteger("uses"));
			}
		}
	}

}
