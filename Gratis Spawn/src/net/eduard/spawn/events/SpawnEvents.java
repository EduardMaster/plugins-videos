
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
			if (SpawnPlugin.getInstance().getBoolean("Teleport on first join")) {
				if (SpawnPlugin.getInstance().getBoolean("Teleport only on first join")) {
					if (p.hasPlayedBefore()) {
						return;
					}
				}
				if (SpawnPlugin.getInstance().getConfigs().contains("Spawn")) {
					p.teleport(SpawnPlugin.getInstance().getConfigs().getLocation("Spawn"));
					SpawnPlugin.getInstance().getConfigs().getSound("Sound on join").create(p);
				}
			}
		}

	}

	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		String name = p.getWorld().getName().toLowerCase();
		if (p.hasPermission("spawn.respawn")) {
			if (SpawnPlugin.getInstance().getBoolean("Teleport on respawn")) {
				if (SpawnPlugin.getInstance().getBoolean("Teleport on respawn in world." + name)) {
					if (SpawnPlugin.getInstance().getConfigs().contains("Spawn")) {
						e.setRespawnLocation(SpawnPlugin.getInstance().getConfigs().getLocation("Spawn"));
						Mine.TIME.asyncDelay(new Runnable() {

							@Override
							public void run() {
								SpawnPlugin.getInstance().getConfigs().getSound("Sound on respawn").create(p);
							}
						}, 1);

					}
				}
			}
		}
	}
}
