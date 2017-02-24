package net.eduard.tag.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.eduard.api.API;
import net.eduard.api.dev.Tag;
import net.eduard.api.manager.EventAPI;
import net.eduard.tag.Main;


public class TagEvent extends EventAPI {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		if (Main.config.getBoolean("ChangeChat")) {
			Tag tag = API.getTag(p);
			e.setFormat(Main.config.message("Chat")
					.replace("$prefix", tag.getPrefix())
				.replace("$suffix", tag.getSuffix())
				.replace("$player", p.getDisplayName())
				.replace("$message", e.getMessage()));
		}

	}
}
