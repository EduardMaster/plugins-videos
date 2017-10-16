
package me.eduard.tapete;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static ScoreboardManager sc = Bukkit.getScoreboardManager();

	public static PluginManager pm = Bukkit.getPluginManager();

	public static BukkitScheduler sh = Bukkit.getScheduler();

	public static CommandSender send = Bukkit.getConsoleSender();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para players!");
			return true;
		}
		// Player p = (Player) sender;

		return true;
	}

	@Override
	public void onDisable() {

		HandlerList.unregisterAll();
	}

	@Override
	public void onEnable() {

		pm.registerEvents(new TapeteVoador(), this);
		getCommand("tapete").setExecutor(new TapeteVoador());
		TapeteVoador.TapeteVoadorTimer();
	}

	@Override
	public void onLoad() {

		m = this;
		cf = getConfig();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}
}
