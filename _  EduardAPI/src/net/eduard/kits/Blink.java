package net.eduard.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;

public class Blink extends Kit {
	public Blink() {
		setIcon(Material.LEAVES, "Teleporte para perto numa distancia");
		new Click(Material.NETHER_STAR, new ClickEffect() {
			
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						Location loc = API.getTarget(p, distance);
						API.teleport(p,loc.clone().add(0, 2, 0));
						loc.getBlock().setType(Material.LEAVES);
					}
				}
				
			}
		});
	}
	public int distance = 7;

}
