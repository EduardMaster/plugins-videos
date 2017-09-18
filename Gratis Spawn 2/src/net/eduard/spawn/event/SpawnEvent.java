
package net.eduard.spawn.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventsManager;
import net.eduard.spawn.Main;

public class SpawnEvent extends EventsManager {
	

	@EventHandler
	public void event(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("spawn.join")) {
			if (Main.getPlugin().getBoolean("teleportOnlyOnFirstJoin")) {
				if (p.hasPlayedBefore()) {
					return;
				}
			}
			if (Main.getPlugin().getConfigs().contains("SpawnLocation")) {
				p.teleport(Main.getPlugin().getConfigs().getLocation("SpawnLocation"));
				Main.getPlugin().getConfigs().getSound("Sound.OnJoin").create(p);
			}
		}

	}

	@EventHandler
	public void event(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("spawn.respawn")) {
			String name = p.getWorld().getName().toLowerCase();
			if (Main.getPlugin().getBoolean("teleportOnRespawnInWorld." + name)) {
				if (Main.getPlugin().getConfigs().contains("SpawnLocation")) {
					e.setRespawnLocation(Main.getPlugin().getConfigs().getLocation("SpawnLocation"));
					API.TIME.delay(1,new Runnable() {
						
						@Override
						public void run() {
							Main.getPlugin().getConfigs().getSound("Sound.OnRespawn").create(p);
						}
					});

				}
			}
		}
	}
}
