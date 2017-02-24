package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;

public class Flash extends Kit {

	public Flash() {
		setIcon(Material.REDSTONE_TORCH_ON, "Teleporte para Longe");
		add(Material.REDSTONE_TORCH_ON);
		setTime(40);
		new Click(Material.REDSTONE_TORCH_ON, new ClickEffect() {
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						API.teleport(p, distance);
					}
				}
			}

			public void effect(PlayerInteractEntityEvent e) {

			}

		});
	}

	public int distance = 45;
}
