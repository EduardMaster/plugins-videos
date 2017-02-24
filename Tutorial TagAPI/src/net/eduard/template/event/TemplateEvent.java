
package net.eduard.template.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.template.manager.TagAPI;

public class TemplateEvent implements Listener {

	@EventHandler
	public void event(PlayerMoveEvent e) {
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("*")) {
			TagAPI.setTag(e.getPlayer(), "§4[DONO]§6§l","§b[MEGADONO]");
		}

	}

}
