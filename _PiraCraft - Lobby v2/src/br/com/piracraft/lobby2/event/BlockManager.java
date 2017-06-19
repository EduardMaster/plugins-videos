package br.com.piracraft.lobby2.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockManager implements Listener {
	

	
	@EventHandler
	public void quebar(BlockBreakEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void colocar(BlockPlaceEvent e) {
		e.setCancelled(true);
	}
}
