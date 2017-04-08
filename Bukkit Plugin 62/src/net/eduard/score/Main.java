
package net.eduard.score;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.player.Score;

public class Main extends JavaPlugin implements Listener {

	public HashMap<Player, Score> scoreboards = new HashMap<>();

	public void onEnable() {
		API.event(this);
		
		API.timer(this,20,new Runnable() {

			@Override
			public void run() {
				updateScoreboards();
			}
		});
	}
	public void updateScoreboards() {
		for (Entry<Player, Score> map : scoreboards.entrySet()) {
			updateScoreboard(map.getKey(), map.getValue());
		}
	}
	public void updateScoreboard(Player p, Score score) {
		score.setEmpty(10);
		score.set(8, "§aNick: §6" + p.getDisplayName());
		score.set(6, "§3Kills: §e" + p.getStatistic(Statistic.PLAYER_KILLS));
		score.set(4, "§3Deaths: §e" + p.getStatistic(Statistic.DEATHS));
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		scoreboards.remove(e.getPlayer());
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Score score = new Score("§6§lSky§f§lLegend");
		score.setEmpty(10);
		updateScoreboard(p, score);
		score.setScoreboard(p);
	}

}
