
package net.eduard.eduardapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.manager.EventAPI;
import net.eduard.eduardapi.EduardAPI;

public class CustomJoinMessage extends EventAPI {

	@EventHandler
	public void event(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (EduardAPI.option("noCustomJoinMessage")) {
			return;
		}
		e.setJoinMessage(EduardAPI.message("joinMessage").replace("$player", p.getName()));
	}

}
