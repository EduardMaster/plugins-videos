package net.eduard.hg;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener {
	

	
	public Events() {
		PluginManager pm = Bukkit.getPluginManager();
		// funciona isto apenas na 1.7++
		pm.registerEvents(this, JavaPlugin.getProvidingPlugin(getClass()));

	}
}
