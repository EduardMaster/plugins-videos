
package net.eduard.hg;

import net.eduard.hg.event.HGWorldRegeneration;
import net.eduard.hg.manager.Kit;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;


public class Main extends JavaPlugin implements Listener {

	public static PluginManager pm = Bukkit.getPluginManager();
	public static ConsoleCommandSender send = Bukkit.getConsoleSender();
	public static ScoreboardManager sm = Bukkit.getScoreboardManager();
	public static BukkitScheduler sc = Bukkit.getScheduler();
	public static Main plugin;
	

	public void onEnable() {
		plugin = this;
		pm.registerEvents(new HGEvents(), this);
		pm = Bukkit.getPluginManager();
		send = Bukkit.getConsoleSender();
		sm = Bukkit.getScoreboardManager();
		sc = Bukkit.getScheduler();
		pm.registerEvents(new HGEvents(), this);
		pm.registerEvents(new HGWorldRegeneration(), this);
		pm.registerEvents(new Kit(), this);
		new HGScoreboard().runTaskTimer(this, 20, 20);
		new HGTimer().runTaskTimer(this, 20, 20);;
		for (int i = 1; i < 100; i++) {
			if (i<=10) {
				HG.startTimes.add(i);
				HG.gameOverTimes.add(i);
				HG.restartTimes.add(i);
				HG.invunerabilityTimes.add(i);
			}
			if (i%30==0) {
				HG.startTimes.add(i);
				HG.gameOverTimes.add(i);
				HG.restartTimes.add(i);
				HG.invunerabilityTimes.add(i);
			}
			if (i%25==0) {
				HG.startTimes.add(i);
				HG.gameOverTimes.add(i);
				HG.restartTimes.add(i);
				HG.invunerabilityTimes.add(i);
			}
		}
	}
	public static int getMoney(Player p) {
		return 100;
	}
	public static String getKit(Player p) {
		return "Nenhum";
	}


}
