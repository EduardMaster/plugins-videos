package net.eduard.api.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;

public class NoFall extends EventAPI {

	private List<Player> players = new ArrayList<>();

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL && players.contains(p)) {
				e.setCancelled(true);
				players.remove(p);
			}
		}
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (e.getTo().getBlock().equals(e.getTo().getBlock().getLocation())) {
			if (API.isOnGround(p) && !API.isFalling(p)) {
				players.remove(p);
			}
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
