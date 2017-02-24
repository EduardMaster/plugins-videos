package net.eduard.witherspawn.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.witherspawn.WitherSpawn;

public class WitherDeathEvent extends EventAPI {
	
	@EventHandler
	public void event(EntityDeathEvent e) {
		if (e.getEntity().equals(WitherSpawn.wither)) {
			WitherSpawn.config.set("WitherDeathTime", API.getTime());
			WitherSpawn.config.saveConfig();
			WitherSpawn.wither = null;
		}
	}
}
