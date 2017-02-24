
package net.eduard.eduardapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.manager.EventAPI;
import net.eduard.eduardapi.EduardAPI;

public class CustomQuitMessage extends EventAPI {

	@EventHandler
	public void event(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (EduardAPI.option("noCustomQuitMessage")) {
			return;
		}
		e.setQuitMessage(EduardAPI.message("quitMessage").replace("$player", p.getName()));

	}

}
