
package net.eduard.skywars.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TemplateEvent implements Listener {

	@EventHandler
	public void event(PlayerMoveEvent e) {
	}
	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		e.setMessage(
				ChatColor.translateAlternateColorCodes('&', e.getMessage()));

	}
}
