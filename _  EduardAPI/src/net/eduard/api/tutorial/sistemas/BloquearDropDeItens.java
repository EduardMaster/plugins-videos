package net.eduard.api.tutorial.sistemas;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class BloquearDropDeItens implements Listener{
	
	@EventHandler
	public void JogadorNaoDropar(PlayerDropItemEvent e) {

		e.setCancelled(true);
	}

}
