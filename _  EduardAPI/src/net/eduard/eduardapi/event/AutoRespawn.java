package net.eduard.eduardapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.eduard.api.dev.Game;
import net.eduard.api.manager.EventAPI;
import net.eduard.api.manager.PlayerAPI;
import net.eduard.api.util.SimpleEffect;
import net.eduard.eduardapi.EduardAPI;

public class AutoRespawn extends EventAPI {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (EduardAPI.option("autoRespawn")) {
			new Game().delay(new SimpleEffect() {
				
				public void effect() {
					if (p.isDead()) {
						p.setFireTicks(0);
						try {
							PlayerAPI.makeRespawn(p);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			});

		}

	}
}
