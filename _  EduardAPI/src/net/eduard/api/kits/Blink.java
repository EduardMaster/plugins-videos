package net.eduard.api.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.setup.GameAPI;

public class Blink extends Kit {
	public int distance = 7;
	public Blink() {
		setIcon(Material.LEAVES, "§fTeleporte para perto numa distancia");
		setClick(new Click(Material.NETHER_STAR, new ClickEffect() {
			
			@Override
			public void effect(PlayerInteractEntityEvent e) {
				
			}
			
			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						Location loc = GameAPI.getTargetLoc(p, distance);
						GameAPI.teleport(p,loc.clone().add(0, 2, 0));
						loc.getBlock().setType(Material.LEAVES);
					}
				}
				
			}
		}));
		
		add(Material.NETHER_STAR);
		setTime(10);
	}
	

}
