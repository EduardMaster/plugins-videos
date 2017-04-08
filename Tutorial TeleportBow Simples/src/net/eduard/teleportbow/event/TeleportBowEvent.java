
package net.eduard.teleportbow.event;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TeleportBowEvent implements Listener {


	@EventHandler
	public void event(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player p = (Player) arrow.getShooter();
				if (p.hasPermission("teleportbow.use")) {
					if (arrow.getWorld().getName().equals("world")) {
						p.teleport(arrow.getLocation().setDirection(p.getLocation().getDirection()));
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
					}
				}
			}
			
		}
	}

}
