
package me.eduard.kitpvp.habilidades;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.eduard.kitpvp.KitPvP;
import me.eduard.kitpvp.KitType;

public class Stomper extends KitPvP implements Listener {

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p, KitType.STOMPER)) {
				double d = e.getDamage();
				for (Entity alvo : p.getNearbyEntities(3, 3, 3)) {
					if (alvo instanceof Player) {
						Player pa = (Player) alvo;
						if (!pa.isSneaking()) {
							pa.damage(d);
						}
					} else if (alvo instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) alvo;
						livingEntity.damage(d);

					}
				}
				e.setDamage(d > 2 ? 2 : d);
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 1);
			}
		}
	}

}
