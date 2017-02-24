package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.eduard.api.gui.Kit;

public class Stomper extends Kit {

	public Stomper() {
		setIcon(Material.IRON_BOOTS, "Esmague os seus inimigos");
	}

	public int range = 3;
	public double minDamage = 4;
	public double fallDamage = 4;
	public double damageNerf = 8;

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				if (e.getCause() == DamageCause.FALL) {
					if (e.getDamage() > fallDamage) {
						e.setDamage(fallDamage);
					}
					check(p);
				}
			}
		}
	}

	public void check(Player p) {
		for (Entity entity : p.getNearbyEntities(range, range, range)) {
			if (entity instanceof Player) {
				Player target = (Player) entity;
				if (target.isSneaking()) {
					target.damage(minDamage);
				} else {
					target.damage(p.getFallDistance() - damageNerf);
				}

			}
		}
	}
}
