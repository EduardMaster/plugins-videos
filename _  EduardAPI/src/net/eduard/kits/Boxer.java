package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class Boxer extends Kit {
	public Boxer() {
		setIcon(Material.STONE_SWORD, "Reduza 1 de dano recebido","Cause 4 de dano usando Nada");
	}

	public double damageReduction = 1;

	public double damage = 4;

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (API.isUsing(p, Material.AIR)) {
					e.setDamage(damage);
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				e.setDamage(e.getDamage() - damageReduction);
			}
		}
	}
}
