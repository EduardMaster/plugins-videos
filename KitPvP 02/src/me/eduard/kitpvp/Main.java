
package me.eduard.kitpvp;

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

import java.util.List;

public class Main extends JavaPlugin implements Listener {

	public Main main = this;

	public ScoreboardManager score = Bukkit.getScoreboardManager();

	public FileConfiguration cf = getConfig();

	public PluginManager pm = Bukkit.getPluginManager();

	public BukkitScheduler sh = Bukkit.getScheduler();

	public CommandSender send = Bukkit.getConsoleSender();

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para players!");
			return true;
		}
		// Player p = (Player) sender;

		return true;
	}

	public void onDisable() {

		HandlerList.unregisterAll();
	}

	public void onEnable() {

	}

	public void onLoad() {

	}

	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}
}
