
package me.eduard.kitpvp.habilidades;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.eduard.kitpvp.KitPvP;
import me.eduard.kitpvp.KitType;

public class FireMan extends KitPvP implements Listener {

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			if (hasKit(d, KitType.FIREMAN)) {
				e.getEntity().setFireTicks(20 * 5);
			}

		}
	}

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p, KitType.FIREMAN)) {
				if (e.getCause() == DamageCause.FIRE
					| e.getCause() == DamageCause.FIRE_TICK
					| e.getCause() == DamageCause.LAVA) {
					e.setCancelled(true);
				}
			}
		}
	}
}
