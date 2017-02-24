package net.eduard.eduardapi.manager;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;

public class WorldEdit extends EventAPI{
	
	@EventHandler
	private void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("eduardapi.worldedit")) {
			if (p.getGameMode() == GameMode.CREATIVE) {
				if (API.isUsing(p, Material.DIAMOND_AXE)) {
					e.setCancelled(true);

					if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						Block block = e.getClickedBlock();
						Location loc = block.getLocation();
						API.POSITION2.put(p, loc);
						p.sendMessage("§aPosição §22 §afoi setada: §3" + block.getX() + "," + block.getY() + ","
								+ block.getZ());
					} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
						Block block = e.getClickedBlock();
						Location loc = block.getLocation();
						API.POSITION1.put(p, loc);
						p.sendMessage("§aPosição §21 §afoi setada: §3" + block.getX() + "," + block.getY() + ","
								+ block.getZ());
					}
				}
			}

		}
	}

	@SuppressWarnings("deprecation")
	public static int set(Player p, int id, int data) {
		int amount = 0;
		for (Location location : API.getLocations(API.POSITION1.get(p), API.POSITION2.get(p))) {
			location.getBlock().setTypeIdAndData(id, (byte) data, false);
			amount++;
		}
		return amount;
	}
	
}
