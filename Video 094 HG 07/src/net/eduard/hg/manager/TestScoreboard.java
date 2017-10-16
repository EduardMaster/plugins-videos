package net.eduard.hg.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.eduard.hg.HGState;
import net.eduard.hg.Main;

public class TestScoreboard {

	
	public TestScoreboard() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Scoreboard score = Main.sm.getNewScoreboard();
				for (Player p:Bukkit.getOnlinePlayers())
				{
					Objective sc = score.registerNewObjective(p.getName(), "dummy");
					sc.setDisplayName("§6HardcoreGames");
					sc.setDisplaySlot(DisplaySlot.SIDEBAR);
					if (Main.state==HGState.START) {
						sc.getScore(Bukkit.getOfflinePlayer("§b")).setScore(14);
						sc.getScore(Bukkit.getOfflinePlayer("§6Começando em")).setScore(13);
						sc.getScore(Bukkit.getOfflinePlayer("§e"+TimerStart.time+" segundos")).setScore(12);
					}else if(Main.state==HGState.INVUNERABLE) {
						sc.getScore(Bukkit.getOfflinePlayer("§b")).setScore(14);
						sc.getScore(Bukkit.getOfflinePlayer("§6Invuneravel")).setScore(13);
						sc.getScore(Bukkit.getOfflinePlayer("§e"+TimerInvunerable.time+" segundos")).setScore(12);
					}else {
						sc.getScore(Bukkit.getOfflinePlayer("§b")).setScore(13);
						sc.getScore(Bukkit.getOfflinePlayer("§6Seja o Melhor")).setScore(12);
					}
					
					sc.getScore(Bukkit.getOfflinePlayer("§c")).setScore(11);
					sc.getScore(Bukkit.getOfflinePlayer("§6Seu Kit é")).setScore(10);
					sc.getScore(Bukkit.getOfflinePlayer("§e"+Main.getKit(p))).setScore(9);
					sc.getScore(Bukkit.getOfflinePlayer("§a")).setScore(11);
					p.setScoreboard(score);
				}
				
			}
		}.runTaskTimer(Main.plugin, 20, 20);
	}
}
