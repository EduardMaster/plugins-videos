package net.eduard.login.events;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.login.manager.LoginAPI;

public class LoginEvents extends EventsManager {

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (!LoginAPI.isLogged(p)) {
			if (!Mine.equals2(e.getFrom(), e.getTo())) {
				e.setTo(e.getFrom().setDirection(e.getTo().getDirection()));
				Mine.OPT_SOUND_ERROR.create(p);
			}

		}
	}



	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!LoginAPI.isLogged(p)) {
			e.setCancelled(true);
			Mine.OPT_SOUND_ERROR.create(p);
		}
	}

	@EventHandler
	public void event(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (!LoginAPI.isLogged(p)) {
			e.setCancelled(true);
			Mine.OPT_SOUND_ERROR.create(p);
		}
	}

	@EventHandler
	public void event(PlayerQuitEvent e) {
		LoginAPI.logout(e.getPlayer());

	}

	@EventHandler
	public void event(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (!LoginAPI.isLogged(p)) {
			e.setCancelled(true);
			Mine.OPT_SOUND_ERROR.create(p);
		}
	}

	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();

			if (!LoginAPI.isLogged(p)) {
				e.setCancelled(true);
				Mine.OPT_SOUND_ERROR.create(p);
			}
		}
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (LoginAPI.isRegistered(p)) {
			if (!LoginAPI.isLogged(p)) {
//				title.create(p);
			}

		}

		p.setAllowFlight(true);
		p.setFlying(true);
//			if (!isRegistered(p)) {
//				Mine.chat(p, messageRegister);
//				title.create(p);
//				Mine.TIME.delay(maxTimeToRegister * 20, new Runnable() {
		//
//					@Override
//					public void run() {
//						if (!isRegistered(p)) {
//							p.kickPlayer(messageTimeOut);
//						}
//					}
//				});
//			}

	}

	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!LoginAPI.isLogged(p)) {
			e.setCancelled(true);
			Mine.OPT_SOUND_ERROR.create(p);
		}
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		String msg = e.getMessage();
		Player p = e.getPlayer();
		PluginCommand loginCmd = Bukkit.getPluginCommand("login");
		PluginCommand registerCmd = Bukkit.getPluginCommand("register");
		if (!LoginAPI.isLogged(p)) {
			e.setCancelled(true);
			if (msg.toLowerCase().startsWith("/" + registerCmd.getName())) {
				e.setCancelled(false);
			}
			if (msg.toLowerCase().startsWith("/" + loginCmd.getName())) {
				e.setCancelled(false);
			}
			for (String cmd : loginCmd.getAliases()) {
				if (msg.toLowerCase().startsWith("/" + cmd.toLowerCase())) {
					e.setCancelled(false);
				}
			}
			for (String cmd : registerCmd.getAliases()) {
				if (msg.toLowerCase().startsWith("/" + cmd.toLowerCase())) {
					e.setCancelled(false);
				}
			}

		}

	}
}
