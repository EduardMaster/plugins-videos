
package net.eduard.spawn.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.spawn.SpawnPlugin;

public class SpawnEvents extends EventsManager {

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("spawn.join")) {
			if (SpawnPlugin.getPlugin().getBoolean("Teleport on first join")) {
				if (SpawnPlugin.getPlugin().getBoolean("Teleport only on first join")) {
					if (p.hasPlayedBefore()) {
						return;
					}
				}
				if (SpawnPlugin.getPlugin().getConfigs().contains("Spawn")) {
					p.teleport(SpawnPlugin.getPlugin().getConfigs().getLocation("Spawn"));
					SpawnPlugin.getPlugin().getConfigs().getSound("Sound on join").create(p);
				}
			}
		}

	}

	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		String name = p.getWorld().getName().toLowerCase();
		if (p.hasPermission("spawn.respawn")) {
			if (SpawnPlugin.getPlugin().getBoolean("Teleport on respawn")) {
				if (SpawnPlugin.getPlugin().getBoolean("Teleport on respawn in world." + name)) {
					if (SpawnPlugin.getPlugin().getConfigs().contains("Spawn")) {
						e.setRespawnLocation(SpawnPlugin.getPlugin().getConfigs().getLocation("Spawn"));
						Mine.TIME.delays(1, new Runnable() {

							@Override
							public void run() {
								SpawnPlugin.getPlugin().getConfigs().getSound("Sound on respawn").create(p);
							}
						});

					}
				}
			}
		}
	}
}
