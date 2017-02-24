package net.eduard.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;

public class BuildTower extends Kit {
	public int size = 65;
	public Material type = Material.DIRT;
	public int data;

	public BuildTower() {
		setIcon(Material.DIRT, "Contrua uma Torre de Terra");
		add(Material.CARPET);
		setTime(30);
		new Click(Material.CARPET, new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						Location loc = p.getLocation();
						for (int id = 1; id <= size; id++) {
							loc.add(0, 1, 0);
							loc.getBlock().setType(type);
						}
						loc.add(0, 1, 0);
						p.teleport(loc.setDirection(p.getLocation().getDirection()));
					}
				}
			}

			public void effect(PlayerInteractEntityEvent e) {

			}

		});
	}
}
