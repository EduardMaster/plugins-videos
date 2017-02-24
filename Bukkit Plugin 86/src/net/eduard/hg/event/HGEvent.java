
package net.eduard.hg.event;

import net.eduard.hg.HGState;
import net.eduard.hg.Main;
import net.eduard.hg.manager.TimerStart;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HGEvent implements Listener {

	@EventHandler
	public void event(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		if (Main.state == HGState.START) {
			if (p.getWorld().getSpawnLocation().distance(p.getLocation()) > 100) {
				e.setCancelled(true);
				p.sendMessage("§cVoce atingiu o limite espere a partida inciar");
			}
		} else {
			if (p.getWorld().getSpawnLocation().distance(p.getLocation()) > 1000) {
				e.setCancelled(true);
				p.sendMessage("§cVoce esta no limite do mapa!");
			}
		}

	}

	@EventHandler
	public void event(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		Main.players.remove(p);
		Main.spectators.remove(p);
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		e.setJoinMessage("");
		Main.players.add(p);
		p.teleport(p.getWorld().getSpawnLocation());
		if (Main.state == null) {
			if (Main.players.size() == 1) {
				new TimerStart();
			}
		}

	}

	@EventHandler
	public void event(BlockBreakEvent e) {

		if (Main.state == HGState.START) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(BlockPlaceEvent e) {

		if (Main.state == HGState.START) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(PlayerDropItemEvent e) {

		if (Main.state == HGState.START) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {

		if (Main.state == HGState.START) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player) {
			if (Main.state == HGState.START | Main.state == HGState.INVUNERABLE) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player) {
			Player entity = (Player) e.getEntity();
			if (e.getDamager() instanceof Player) {
				Player damager = (Player) e.getDamager();
				if (Main.spectators.contains(damager)) {
					e.setCancelled(true);
				}
				if (Main.spectators.contains(entity)) {
					e.setCancelled(true);
				}

			}
		}
	}
}
