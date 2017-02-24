
package net.eduard.eduardapi.event;


import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.manager.EventAPI;
import net.eduard.eduardapi.EduardAPI;

public class NoJoinMessage extends EventAPI {

	@EventHandler
	public void event(PlayerJoinEvent e) {

		if (EduardAPI.option("noJoinMessage")) {
			e.setJoinMessage(null);
		}
	}

}
