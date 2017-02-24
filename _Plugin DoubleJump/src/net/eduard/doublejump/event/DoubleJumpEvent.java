
package net.eduard.doublejump.event;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.doublejump.DoubleJump;

public class DoubleJumpEvent extends EventAPI {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void controlar(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (p.getGameMode() != GameMode.CREATIVE) {
			if (!API.equals(e.getFrom(), e.getTo())) {

				if (API.isOnGround(p)) {
					if (p.isOnGround() && p.getVelocity().getY() > -0.1) {
						DoubleJump.players.remove(p);
						p.setAllowFlight(true);
					}
					// if (!DoubleJump.players.contains(p) && !API.isFalling(p))
					// {
					// p.setAllowFlight(true);
					// }
				} else if (API.isFlying(p) & API.isFalling(p)) {
					p.setAllowFlight(false);
				}
			}
		}

	}

	@EventHandler
	public void habilitar(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player & e.getCause() == DamageCause.FALL) {
			Player p = (Player) entity;
			if (DoubleJump.players.contains(p)) {
				e.setCancelled(true);
				p.setAllowFlight(true);
				DoubleJump.players.remove(p);
			}
		}

	}

	@EventHandler
	public void ativar(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			if (!DoubleJump.players.contains(p)) {
				if (DoubleJump.config.getBoolean(p.getWorld().getName().toLowerCase() + ".enable")) {
					DoubleJump.jumps.get(p.getWorld()).create(e.getPlayer());
					DoubleJump.players.add(p);
					e.setCancelled(true);
					p.setAllowFlight(false);
				}

			}
		}

	}

}
