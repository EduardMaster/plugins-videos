package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class FisherMan extends Kit {

	public FisherMan() {
		setIcon(Material.FISHING_ROD, "Puxe seus inimigos ate voce");
		add(Material.FISHING_ROD);
	}

	@EventHandler
	private void event(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (e.getCaught() != null) {
				API.teleport(e.getCaught(), p.getLocation());
			}
		}
	}
}
