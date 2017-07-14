package net.eduard.api.util;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EventRegister implements Listener {

	private boolean registration;
	
	public EventRegister() {
		this(true);
	}

	public EventRegister(boolean automatic) {
		if (automatic) {
			register(JavaPlugin.getProvidingPlugin(getClass()));

		}
	}
	public void register(Plugin plugin) {
		unregister();
		registration = true;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	public void unregister() {
		HandlerList.unregisterAll(this);
	}
	public boolean isRegistered() {
		return registration;
	}

}
