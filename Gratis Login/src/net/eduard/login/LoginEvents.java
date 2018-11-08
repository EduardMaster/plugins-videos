package net.eduard.login;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.lib.manager.EventsManager;

public class LoginEvents  extends EventsManager
{
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setAllowFlight(true);
		p.setFlying(true);
		if (!isRegistered(p)) {
			Mine.chat(p, messageRegister);
			title.create(p);
			Mine.TIME.delay(maxTimeToRegister * 20, new Runnable() {

				@Override
				public void run() {
					if (!isRegistered(p)) {
						p.kickPlayer(messageTimeOut);
					}
				}
			});
		}

	}

}
