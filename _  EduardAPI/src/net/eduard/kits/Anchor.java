package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import net.eduard.api.dev.Game;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.SimpleEffect;

public class Anchor extends Kit {

	public Anchor() {
		setIcon(Material.ANVIL, "Não leve e nem cause NockBack");
	}
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		boolean no = false;
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if (hasKit(player)) {
				no = true;
			}
		}
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (hasKit(player)) {
				no = true;
			}
			
		}
		if (no) {
			new Game().delay(new SimpleEffect() {
				
				public void effect() {
					e.getEntity().setVelocity(new Vector());
					e.getDamager().setVelocity(new Vector());
				}
			});
		}
	}
	
	
}
