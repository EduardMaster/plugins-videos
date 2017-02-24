package net.eduard.warp.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class WarpEvent extends EventAPI {

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		if (Main.config.getBoolean("WarpPerCommand")) {
			if (Warp.hasWarps()) {
				for (String warp : Warp.getWarps().split(", ")) {
					if (API.startWith(e.getMessage(), "/" + warp)) {
						e.setMessage("/warp " + warp);
						break;
					}
				}
			}
		}
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		if (Main.config.getBoolean("giveGuiItemOnJoin")) {
			e.getPlayer().getInventory().addItem(Main.config.getItem("GuiItem"));
		}
		
	}
}
