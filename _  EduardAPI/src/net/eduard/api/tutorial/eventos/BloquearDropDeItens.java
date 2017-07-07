package net.eduard.api.tutorial.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

import net.eduard.api.manager.Manager;

public class BloquearDropDeItens extends Manager{
	
	@EventHandler
	public void JogadorNaoDropar(PlayerDropItemEvent e) {

		e.setCancelled(true);
	}

}
