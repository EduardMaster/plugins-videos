
package me.eduard.score;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@SuppressWarnings("deprecation")
public class ScoreBoardMain implements Listener

{

	public OfflinePlayer getText(String string) {

		string = string.length() > 16 ? string.substring(0, 16) : string;
		return Bukkit.getOfflinePlayer(string);
	}

	@EventHandler
	public void ScoreBoardMainEvent(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("teste", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§l" + p.getName());
		obj.getScore(getText("§a§lVida")).setScore(15);
		obj.getScore(getText("§e§l" + (int) p.getHealth())).setScore(14);
		obj.getScore(getText("§a§lFome")).setScore(13);
		obj.getScore(getText("§c§l" + p.getFoodLevel())).setScore(12);
		obj.getScore(getText("§a§lKills")).setScore(11);
		obj.getScore(getText("§d§l" + p.getStatistic(Statistic.PLAYER_KILLS)))
			.setScore(10);
		obj.getScore(getText("§a§lDeaths")).setScore(9);
		obj.getScore(getText("§l" + p.getStatistic(Statistic.DEATHS))).setScore(8);
		p.setScoreboard(score);

	}
}
