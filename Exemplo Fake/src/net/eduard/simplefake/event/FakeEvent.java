package net.eduard.simplefake.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.setup.Mine;
import net.eduard.simplefake.FakeAPI;
import net.eduard.simplefake.Main;


public class FakeEvent implements Listener  {

	
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		FakeAPI.fake(p, p.getName());
		FakeAPI.getOriginal().put(p, p.getName());
	}
	@EventHandler
	public void event(AsyncPlayerPreLoginEvent e) {
		String name = e.getName();
		if (FakeAPI.getOriginal().containsValue(name)){
			e.setKickMessage(Main.config.message("kick_by_player_exist").replace("$name", name));
				e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
		}else if (FakeAPI.getData().containsValue(name)){
			Player p = Mine.getPlayer(name);
			FakeAPI.reset(p);
			p.sendMessage(Main.config.message("name_reset_by_other")
					.replace("$name", name));
		}
	}
	@EventHandler
	public void event(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		FakeAPI.getData().remove(p);
		FakeAPI.getOriginal().remove(p);
	}
}
