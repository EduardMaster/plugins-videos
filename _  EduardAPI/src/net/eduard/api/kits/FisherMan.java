package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import net.eduard.api.gui.Kit;
import net.eduard.api.manager.GameAPI;

public class FisherMan extends Kit {

	public FisherMan() {
		setIcon(Material.FISHING_ROD, "§fPuxe seus inimigos ate voce");
		add(Material.FISHING_ROD);
	}

	@EventHandler
	private void event(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (e.getCaught() != null) {
				GameAPI.teleport(e.getCaught(), p.getLocation());
			}
		}
	}
}
