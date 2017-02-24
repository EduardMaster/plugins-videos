package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.SimpleEffect;

public class Magma extends Kit {

	private double damage = 1;
	public int effectTime = 5;
	public double chance = 0.35;

	public Magma() {
		setIcon(Material.MAGMA_CREAM, "Seja invuneravel a Fogo e Lava");
		new Game(1).timer(new SimpleEffect() {
			
			public void effect() {
				for (Player p : API.getOnlinePlayers()) {
					if (hasKit(p)) {
						{
							Material type = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
							if (type == Material.WATER | type == Material.STATIONARY_WATER) {
								p.damage(damage);
							}

						}
						{
							Material type = p.getLocation().getBlock().getType();
							if (type == Material.WATER | type == Material.STATIONARY_WATER) {
								p.damage(damage);
							}
						}

					}
				}
			}
		});
	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FIRE | e.getCause() == DamageCause.FIRE_TICK
					| e.getCause() == DamageCause.LAVA) {
				if (hasKit(p)) {
					e.setCancelled(true);
				}
			}

		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				if (API.getChance(chance))
					e.getDamager().setFireTicks(20 * effectTime);
			}

		}
	}

}
