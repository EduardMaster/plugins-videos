package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;

public class Thor extends Kit {
	public double damage = 4;

	public Thor() {
		setIcon(Material.WOOD_AXE, "§fSolte raios nos inimigos");
		add(Material.WOOD_AXE);
		setClick(new Click(Material.WOOD_AXE, new ClickEffect() {
			
			@Override
			public void effect(PlayerInteractEntityEvent e) {
				
			}
			
			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						API.strike(p, 100);
					}
				}
			}
		}));
		setTime(7);
	}
	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.LIGHTNING) {
				if (hasKit(p)) {
					e.setCancelled(true);
				} else {
					e.setDamage(damage);
				}
				
			}

		}
	}

}
