package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.setup.GameAPI;

public class Flash extends Kit {

	public int distance = 45;

	public Flash() {
		setIcon(Material.REDSTONE_TORCH_ON, "§fTeleporte para Longe");
		add(Material.REDSTONE_TORCH_ON);
		setTime(40);
		setClick(new Click(Material.REDSTONE_TORCH_ON, new ClickEffect() {
			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						GameAPI.teleport(p, distance);
					}
				}
			}

		}));
	}
}
