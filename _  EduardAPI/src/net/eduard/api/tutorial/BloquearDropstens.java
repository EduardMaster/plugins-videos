package net.eduard.api.tutorial;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class BloquearDropstens implements Listener{
	
	@EventHandler
	public void JogadorNaoDropar(PlayerDropItemEvent e) {

		e.setCancelled(true);
	}

}
