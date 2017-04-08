package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.eduard.api.gui.Kit;

public class NoFall extends Kit {

	public NoFall() {
		setIcon(Material.LAPIS_BLOCK, "§fNão leve dano de Queda");
	}
	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p) && e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}

		}
	}

}
