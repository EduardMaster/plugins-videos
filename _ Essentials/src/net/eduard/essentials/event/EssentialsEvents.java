
package net.eduard.essentials.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.manager.EventsManager;

public class EssentialsEvents extends EventsManager {

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("essentials.sign.color")) {
			for (int i = 0; i < e.getLines().length; i++) {
				e.getLines()[i] = ChatColor.translateAlternateColorCodes('&', e.getLines()[i]);
			}
			
		}
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {

	}
	@EventHandler
	public void event(PlayerJoinEvent e) {
	}

}
