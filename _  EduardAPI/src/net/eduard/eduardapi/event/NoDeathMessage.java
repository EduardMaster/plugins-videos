
package net.eduard.eduardapi.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.eduard.api.manager.EventAPI;
import net.eduard.eduardapi.EduardAPI;

public class NoDeathMessage extends EventAPI

{

	@EventHandler
	public void event(PlayerDeathEvent e) {

		if (EduardAPI.option("noDeathMessage")) {
			e.setDeathMessage("");
		}
	}
}
