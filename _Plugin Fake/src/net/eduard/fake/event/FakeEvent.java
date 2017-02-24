package net.eduard.fake.event;

import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.api.manager.PlayerAPI;
import net.eduard.fake.Main;


public class FakeEvent extends EventAPI  {
	@EventHandler
	public void event(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (!Main.fakes.containsKey(p.getName())) {
			Main.fakes.put(p.getName(), p.getName());
		}
	}
	@EventHandler
	public void event(AsyncPlayerPreLoginEvent e) {

		String name = e.getName();
		if (Main.fakes.containsKey(name)) {
			e.setKickMessage(
				Main.config.message("kick_by_player_exist").replace("$name", name));
			e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
		} else if (Main.fakes.containsValue(name)) {
			Player p = API.getPlayer(name);
			for (Entry<String, String> fake : Main.fakes.entrySet()) {
				if (fake.getValue().equals(p.getName())) {
					p.sendMessage(Main.config.message("name_reset_by_other")
						.replace("$name", name));
					p.setDisplayName(fake.getKey());
					p.setPlayerListName(fake.getKey());
					Main.fakes.put(fake.getKey(), fake.getKey());
					PlayerAPI.changeName(p, fake.getKey());
				}
			}
		}
	}
	@EventHandler
	public void event(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		String name = p.getName();
		if (Main.fakes.containsKey(name)) {
			Main.fakes.remove(name);
		} else if (Main.fakes.containsValue(name)) {
			for (Entry<String, String> fake : Main.fakes.entrySet()) {
				if (fake.getValue().equals(p.getName())) {
					Main.fakes.remove(fake.getKey());
					break;
				}
			}
		}
	}
}
