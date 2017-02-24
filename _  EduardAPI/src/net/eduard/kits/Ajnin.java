package net.eduard.kits;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class Ajnin extends Kit {

	public Ajnin() {
		setIcon(Material.NETHER_STAR, "Teleporte seus inimigos ate você");
		setTime(10);
	}

	public int maxDistance = 50;

	public static HashMap<Player, Player> targets = new HashMap<>();

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (e.getEntity() instanceof Player) {
					Player target = (Player) e.getEntity();
					targets.put(p, target);
				}
			}

		}
	}

	@EventHandler
	public void event(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (e.isSneaking()) {
				if (targets.containsKey(p)) {
					Player target = targets.get(p);
					if (target != null) {
						if (target.getLocation().distance(p.getLocation()) <= maxDistance) {
							if (cooldown(p)) {
								API.teleport(target, p.getLocation());
								e.setCancelled(true);

							}
						}
					}

				}

			}
		}

	}

}
