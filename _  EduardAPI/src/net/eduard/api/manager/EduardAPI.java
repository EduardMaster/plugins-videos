package net.eduard.api.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.API;
import net.eduard.api.player.Tag;

public class EduardAPI extends EventAPI{
	
	@EventHandler
	public void entrar(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (!Tag.getTags().containsKey(p)) {
			API.removeTag(p);
		}
		Information.saveObject("Players/" + p.getName() + " " + p.getUniqueId(), p);
		if (API.NO_JOIN_MESSAGE) {
			e.setJoinMessage(null);
			return;
		}

		e.setJoinMessage(API.ON_JOIN.replace("$player", p.getName()));
	}
	@EventHandler
	public void morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (API.AUTO_RESPAWN) {
			if (p.hasPermission("eduardapi.autorespawn")){
				API.TIME.delay(1L,new Runnable() {

					public void run() {
						if (p.isDead()) {
							p.setFireTicks(0);
							try {
								RexAPI.makeRespawn(p);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});
			}

		}
		if (API.NO_DEATH_MESSAGE) {
			e.setDeathMessage("");
		}
	}
	@EventHandler
	public void sair(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (API.NO_QUIT_MESSAGE) {
			e.setQuitMessage("");
			return;
		}
		e.setQuitMessage(API.ON_QUIT.replace("$player", p.getName()));

	}

}
