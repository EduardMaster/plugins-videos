package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class Viking extends Kit {
	public double damage = 2;

	public Viking() {
		setIcon(Material.IRON_AXE, "Batalhe melhor usando Machados");
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (API.isUsing(p, "_AXE")) {
					e.setDamage(e.getDamage() + damage);
				}
			}

		}
	}
}
