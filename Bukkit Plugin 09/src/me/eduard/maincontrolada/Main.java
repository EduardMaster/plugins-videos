
package me.eduard.maincontrolada;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		return false;
	}

	public void onDisable() {

		HandlerList.unregisterAll();
	}

	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	public void onLoad() {

		m = this;
		cf = getConfig();
		sh = Bukkit.getScheduler();
		pm = Bukkit.getPluginManager();
		new Teste(this);
		new Teste();
	}
}
