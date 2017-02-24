
package net.eduard.template.event;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.template.Main;
import net.eduard.template.system.TeleportBow;

public class TemplateEvent implements Listener {

	@EventHandler
	public void event(PlayerMoveEvent e) {
	}

	@EventHandler
	public void event(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player p = (Player) arrow.getShooter();
				if (p.hasPermission("teleportbow.use")) {
					TeleportBow telepotbow = Main.TELEPORTBOW.get(p.getWorld());
					if (telepotbow.isEnabled()) {
						p.teleport(arrow.getLocation().setDirection(p.getLocation().getDirection()));
						p.playSound(p.getLocation(), telepotbow.getSound(), 2, 1);

					}
				}

			}

		}
	}

}
