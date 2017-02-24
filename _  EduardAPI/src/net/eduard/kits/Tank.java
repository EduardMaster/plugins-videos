package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.eduard.api.dev.Explosion;
import net.eduard.api.gui.Kit;

import org.bukkit.event.entity.PlayerDeathEvent;

public class Tank extends Kit {
	public Explosion effect = new Explosion(6, false, false);

	public Tank() {
		setIcon(Material.TNT, "Seja um Terrista");
	}

	@EventHandler
	public void event(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() != null) {
			Player p = e.getEntity().getPlayer();
			if (hasKit(p)) {
				effect.create(p);
			}

		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				effect.create(p);
			}
		}

	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				if (e.getCause() == DamageCause.BLOCK_EXPLOSION | e.getCause() == DamageCause.ENTITY_EXPLOSION) {
					e.setCancelled(true);
				}
			}

		}
	}

}
