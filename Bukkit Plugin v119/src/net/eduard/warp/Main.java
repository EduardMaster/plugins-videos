package net.eduard.warp;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main plugin;
	private static Map<String, Warp> warps = new HashMap<>();
	private static FileConfiguration messages;

	public void onEnable() {
		getCommand("warp").setExecutor(new WarpCommand());
		Bukkit.getPluginManager().registerEvents(new WarpListener(), this);
		reload();
	}
	public static Collection<Warp> getWarps(){
		return warps.values();
	}
	
	public static String message(String path) {
		return ChatColor.translateAlternateColorCodes('&',
				messages.getString(path));
	}

	public static void reload() {
		messages = YamlConfiguration.loadConfiguration(
				new File(plugin.getDataFolder(), "messages.yml"));

	}
}
