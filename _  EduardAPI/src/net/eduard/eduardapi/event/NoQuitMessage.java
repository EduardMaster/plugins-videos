
package net.eduard.eduardapi.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.manager.EventAPI;
import net.eduard.eduardapi.EduardAPI;

public class NoQuitMessage extends EventAPI {

	@EventHandler
	public void event(PlayerQuitEvent e) {

		if (EduardAPI.option("noQuitMessage")) {
			e.setQuitMessage("");
		}

	}

}
