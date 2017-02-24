
package net.eduard.spawn.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.util.SimpleEffect;
import net.eduard.spawn.Main;

public class SpawnEvent implements Listener {

	@EventHandler
	public void event(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("spawn.join")) {
			if (Main.config.getBoolean("teleportOnlyOnFirstJoin")) {
				if (p.hasPlayedBefore()) {
					return;
				}
			}
			if (Main.config.contains("SpawnLocation")) {
				p.teleport(Main.config.getLocation("SpawnLocation"));
				Main.config.getSound("Sound.OnJoin").create(p);
			}
		}

	}

	@EventHandler
	public void event(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		if (p.hasPermission("spawn.respawn")) {
			String name = p.getWorld().getName().toLowerCase();
			if (Main.config.getBoolean("teleportOnRespawnInWorld." + name)) {
				API.broadcast("aaewaewaw");
				if (Main.config.contains("SpawnLocation")) {
					API.broadcast("aewa");
					e.setRespawnLocation(Main.config.getLocation("SpawnLocation"));
					new Game().delay(new SimpleEffect() {
						
						public void effect() {
							// TODO Auto-generated method stub
							Main.config.getSound("Sound.OnRespawn").create(p);
						}
					});

				}
			}
		}
	}
}
