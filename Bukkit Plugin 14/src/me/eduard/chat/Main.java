
package me.eduard.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	@EventHandler
	public void Chat(PlayerChatEvent e) {

		e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
	}

	@EventHandler
	public void DeathMessage(PlayerDeathEvent e) {

		e.setDeathMessage(
			ChatColor.GOLD + e.getEntity().getDisplayName() + " saiu do servidor!");
	}

	@EventHandler
	public void JoinMessage(PlayerJoinEvent e) {

		e.setJoinMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
			+ " entrou no servidor!");
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
