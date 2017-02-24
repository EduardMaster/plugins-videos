package net.eduard.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class LumberJack extends Kit {
	public LumberJack() {
		setIcon(Material.WOOD_AXE, "Quebre uma arvore de uma só vez");
		add(Material.WOOD_AXE);
	}

	@EventHandler
	public void event(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (API.isUsing(p, "_AXE")) {
				if (e.getBlock().getType().name().contains("LOG")) {
					Location loc;
					while (check(loc = e.getBlock().getLocation().add(0, 1, 0))) {
						loc.add(0, 1, 0);
					}
				}
			}

		}

	}

	public boolean check(Location loc) {
		Material type = loc.getBlock().getType();
		if (type.name().contains("LOG")) {
			loc.getBlock().breakNaturally();
			return true;
		}
		return false;
	}

}
