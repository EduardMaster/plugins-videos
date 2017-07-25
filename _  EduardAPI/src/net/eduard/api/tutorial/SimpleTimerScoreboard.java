package net.eduard.api.tutorial;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.eduard.api.util.FakeOfflinePlayer;

public class SimpleTimerScoreboard extends BukkitRunnable{

	@Override
	public void run() {
		for (Player p:Bukkit.getOnlinePlayers()){
			newScoreboard(p, "titulo", "linha1");
		}
		
	}
	public SimpleTimerScoreboard(JavaPlugin plugin	) {
		runTaskTimer(plugin, 20, 20);
	}
	public static Scoreboard newScoreboard(Player player, String title,
			String... lines) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("board", "score");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int id = 15;
		for (final String line : lines) {
			final String empty = ChatColor.values()[id - 1].toString();
			obj.getScore(new FakeOfflinePlayer(line)).setScore(id);;
			id--;
			if (id == 0) {
				break;
			}
		}

		player.setScoreboard(board);
		return board;
	}

	
	
	
}
