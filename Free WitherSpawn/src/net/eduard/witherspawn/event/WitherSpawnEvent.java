package net.eduard.witherspawn.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import net.eduard.api.API;
import net.eduard.api.manager.Manager;
import net.eduard.witherspawn.Main;

public class WitherSpawnEvent extends Manager {
	
	
	public WitherSpawnEvent() {
	}
	
	@EventHandler
	public void event(EntityDeathEvent e) {
		if (e.getEntity().equals(Main.wither)) {
			Main.config.set("WitherDeathTime", API.getNow());
			Main.config.saveConfig();
			Main.wither = null;
		}
	}
}
