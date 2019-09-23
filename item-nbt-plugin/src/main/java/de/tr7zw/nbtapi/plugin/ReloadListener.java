package de.tr7zw.nbtapi.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.tr7zw.nbtinjector.NBTInjector;

/**
 * This listener class tries to prevent people from reloading while the NBTInjector is enabled.
 * 
 * @author tr7zw
 *
 */
public class ReloadListener implements Listener{

	/**
	 * Console handler
	 * 
	 * @param event
	 */
	@EventHandler
	public void onCommand(org.bukkit.event.server.ServerCommandEvent event) {
		if(event.getCommand().toLowerCase().startsWith("reload") && NBTInjector.isInjected()) {
			event.setCancelled(true);
			event.getSender().sendMessage("[NBTAPI] The NBTInjector is currently enabled. Reloading will turn the server into an unstable state and data-loss may accure. Please do a clean restart. Canceled reload!");
		}
	}
	
	/**
	 * Player handler
	 * 
	 * @param event
	 */
	@EventHandler
	public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
		if(event.getMessage().toLowerCase().startsWith("/reload") && NBTInjector.isInjected()) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("[NBTAPI] The NBTInjector is currently enabled. Reloading will turn the server into an unstable state and data-loss may accure. Please do a clean restart. Canceled reload!");
		}
	}
	
}
