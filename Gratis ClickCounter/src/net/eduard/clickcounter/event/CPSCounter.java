package net.eduard.clickcounter.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.setup.Mine.EventsManager;

public class CPSCounter extends EventsManager {
	private static Map<Player, Integer> clicks = new HashMap<>();

	public static Map<Player, Integer> getClicks() {
		return clicks;
	}
	public static void setClicks(Map<Player, Integer> clicks) {
		CPSCounter.clicks = clicks;
	}
	private static List<Player> clicking = new ArrayList<>();
	public CPSCounter() {
		API.TIME.timer(20, new Runnable() {

			@Override
			public void run() {
				show();
			}
		});
	}
	public void show() {
		for (Player p : API.getPlayers()) {
			if (clicking.contains(p)) {
				p.sendMessage(
						"§aSeus Clicks: §e(" + clicks.get(p) + "/Segundo)");
				clicks.put(p, 0);
			}
		}
	}
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (clicking.contains(p)) {
				Integer click = clicks.get(p);
				if (click == null) {
					click = 0;
				}
				clicks.put(p, click + 1);
			}

		}
	}
	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction().name().contains("LEFT")) {
			if (clicking.contains(p)) {
				Integer click = clicks.get(p);
				if (click == null) {
					click = 0;
				}
				clicks.put(p, click + 1);
			}
		}

	}
	public static List<Player> getClicking() {
		return clicking;
	}
	public static void setClicking(List<Player> clicking) {
		CPSCounter.clicking = clicking;
	}
}
