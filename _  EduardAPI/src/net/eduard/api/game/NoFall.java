package net.eduard.api.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.Manager;

public class NoFall extends Manager {

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
			if (GameAPI.isOnGround(p) && !GameAPI.isFalling(p)) {
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
