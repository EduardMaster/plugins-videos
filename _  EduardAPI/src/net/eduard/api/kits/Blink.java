package net.eduard.api.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;

public class Blink extends Kit {
	public int distance = 7;
	public Blink() {
		setIcon(Material.LEAVES, "§fTeleporte para perto numa distancia");
		setClick(new Click(Material.NETHER_STAR, new ClickEffect() {
			
			public void effect(PlayerInteractEntityEvent e) {
				
			}
			
			@Override
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
		}));
		
		add(Material.NETHER_STAR);
		setTime(10);
	}
	

}
