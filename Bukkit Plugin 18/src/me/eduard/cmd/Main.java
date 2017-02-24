
package me.eduard.cmd;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	@EventHandler
	public void NoCMD(PlayerCommandPreprocessEvent e) {

		String cmd = e.getMessage();
		Player p = e.getPlayer();
		e.setCancelled(true);
		if (cmd.contains("gamemode")) {
			e.setCancelled(true);
			return;
		}
		p.sendMessage("§cApenas pode usar o comando /gamemode");

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
		saveDefaultConfig();
	}

}
