
package me.eduard.parkour;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
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

	public Server server = Bukkit.getServer();

	public ScoreboardManager score = Bukkit.getScoreboardManager();

	public FileConfiguration cf = getConfig();

	public PluginManager pm = Bukkit.getPluginManager();

	public BukkitScheduler sh = Bukkit.getScheduler();

	public CommandSender send = Bukkit.getConsoleSender();

	public void addMsg(String name, String message) {

		cf.addDefault("Messages." + name, message);
	}

	public String getMsg(String name) {

		return ChatColor.translateAlternateColorCodes('&',
			cf.getString("Messages." + name));
	}

	public void Msg() {

		addMsg("console", "&cVoce nao pode usar este comando pelo console!");
		cf.options().copyDefaults(true);
		saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(getMsg("console"));
			return true;
		}
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("parkour")) {
			p.sendMessage("funciona");
		}
		return true;
	}

	public void onDisable() {

		HandlerList.unregisterAll();
	}

	public void onEnable() {

		pm.registerEvents(this, this);

	}

	public void onLoad() {

		Msg();
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}

}
