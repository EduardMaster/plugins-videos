package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.eduard.api.gui.Kit;
import net.eduard.api.manager.ItemAPI;

public class Viking extends Kit {
	public double damage = 2;

	public Viking() {
		setIcon(Material.IRON_AXE, "§fBatalhe melhor usando Machados");
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (ItemAPI.isUsing(p, "_AXE")) {
					e.setDamage(e.getDamage() + damage);
				}
			}

		}
	}
}
