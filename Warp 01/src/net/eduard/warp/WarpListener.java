package net.eduard.warp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WarpListener implements Listener{

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
	}
}
