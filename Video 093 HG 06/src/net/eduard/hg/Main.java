
package net.eduard.hg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import net.eduard.hg.event.HGEvent;
import net.eduard.hg.manager.TestScoreboard;


public class Main extends JavaPlugin implements Listener {

	public static PluginManager pm = Bukkit.getPluginManager();
	public static ConsoleCommandSender send = Bukkit.getConsoleSender();
	public static ScoreboardManager sm = Bukkit.getScoreboardManager();
	public static BukkitScheduler sc = Bukkit.getScheduler();
	public static ArrayList<Player> players = new ArrayList<>();
	public static ArrayList<Player> spectators = new ArrayList<>();
	public static HGState state;
	public static Main plugin;
	@Override
	public void onLoad() {
		
	}

	@Override
	public void onEnable() {
		plugin = this;
		pm.registerEvents(new HGEvent(), this);
		pm = Bukkit.getPluginManager();
		send = Bukkit.getConsoleSender();
		sm = Bukkit.getScoreboardManager();
		sc = Bukkit.getScheduler();
//		new ScoreboardSetup();
		new TestScoreboard();
	}
	public static int getMoney(Player p) {
		return 100;
	}
	public static String getKit(Player p) {
		return "Nenhum";
	}


}
