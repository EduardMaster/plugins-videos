package net.eduard.api.manager;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.eduard.api.API;

public class EventAPI extends TimeAPI implements Listener {

	private transient boolean isRegistred;

	public boolean isRegistred() {
		return isRegistred;
	}
	public EventAPI register(Plugin plugin) {
		if (!isRegistred) {
			API.event(this, plugin);
			setPlugin(plugin);
		}
		return this;
	}


	public EventAPI unregister() {
		HandlerList.unregisterAll(this);
		return this;
	}

//	public EventAPI register(Class<? extends JavaPlugin> pluginClass) {
//	return register(JavaPlugin.getPlugin(pluginClass));
//}
}
