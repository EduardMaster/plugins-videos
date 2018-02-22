package net.eduard.live.almas;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Eventos implements Listener {

	@EventHandler
	public void mover(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block bloquinho = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Material tipoDoBloquinho = bloquinho.getType();
	}
	
}
