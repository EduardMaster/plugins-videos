
package net.eduard.template;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.eduard.template.command.TemplateCommand;
import net.minecraft.server.v1_7_R4.ChatClickable;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public static FileConfiguration config;

	public void onEnable() {
		plugin = this;
		config = plugin.getConfig();
		saveDefaultConfig();
		{
			config.addDefault("valor3", "valor4");
			config.options().copyDefaults(true);
			saveConfig();
		}
		getCommand("comando").setExecutor(new TemplateCommand());
		Bukkit.getPluginManager().registerEvents(this, this);

		ArrayList<String> lines = new ArrayList<>();
		lines.add("");
		lines.add("$player");
		lines.add("");
		lines.add("&2Kills: &a$kills");
		lines.add("");
		config.addDefault("Scoreboard.name", "&6Testando");
		config.addDefault("Scoreboard.lines", lines);
		config.options().copyDefaults(true);
		saveConfig();

	}

	public void onDisable() {

	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		aply(e.getPlayer());
	}

	@SuppressWarnings("deprecation")
	public static void aply(Player p) {
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("Scoreboard", "Scoreboard");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Scoreboard.name")));
		int id = 15;
		for (String line : config.getStringList("Scoreboard.lines")) {
			String message = ChatColor.translateAlternateColorCodes('&', line);
			if (message.isEmpty())
				message = "" + ChatColor.values()[id];
			message = message.replace("$player", p.getDisplayName());
			message = message.replace("$kills", "" + p.getStatistic(Statistic.MOB_KILLS));
			message = message.length() > 16 ? message.substring(0, 16) : message;
			obj.getScore(Bukkit.getOfflinePlayer(message)).setScore(id);
			id--;
		}
		p.setScoreboard(score);
	}

}
