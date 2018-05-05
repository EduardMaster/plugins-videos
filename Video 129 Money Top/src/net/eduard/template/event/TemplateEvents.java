
package net.eduard.template.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.setup.manager.EventsManager;
import net.eduard.template.Main;
// clase de eventos
public class TemplateEvents extends EventsManager {

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/money top")) {
			e.setCancelled(true);
			Main.mostrarTop(e.getPlayer());
		}
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {

	}
	@EventHandler
	public void event(PlayerJoinEvent e) {
	}

}
