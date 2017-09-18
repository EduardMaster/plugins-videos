
package me.eduard.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import me.eduard.kitpvp.habilidades.Camel;
import me.eduard.kitpvp.habilidades.Stomper;
import me.eduard.kitpvp.habilidades.Viper;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static ScoreboardManager sc = Bukkit.getScoreboardManager();

	public static PluginManager pm = Bukkit.getPluginManager();

	public static BukkitScheduler sh = Bukkit.getScheduler();

	public static CommandSender send = Bukkit.getConsoleSender();

	@Override
	public void onEnable() {

		pm.registerEvents(new KitSelector(), this);
		pm.registerEvents(new Stomper(), this);
		pm.registerEvents(new Camel(), this);
		pm.registerEvents(new Viper(), this);
	}

	@Override
	public void onLoad() {

		m = this;
		cf = getConfig();
	}

}
