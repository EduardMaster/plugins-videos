package net.eduard.witherspawn.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import net.eduard.api.lib.core.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.witherspawn.Main;

public class WitherSpawnEvent extends EventsManager {
	
	
	public WitherSpawnEvent() {
	}
	
	@EventHandler
	public void event(EntityDeathEvent e) {
		if (e.getEntity().equals(Main.wither)) {
			Main.config.set("WitherDeathTime", Mine.getNow());
			Main.config.saveConfig();
			Main.wither = null;
		}
	}
}
