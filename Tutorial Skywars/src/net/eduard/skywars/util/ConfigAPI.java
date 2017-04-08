package net.eduard.skywars.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAPI {

	private JavaPlugin plugin;
	private String name;
	private File file;
	private FileConfiguration config;

	public String getName() {
		return name;
	}

	public ConfigAPI setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
		return this;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public ConfigAPI(String name, JavaPlugin plugin) {
		this.plugin = plugin;
		this.name = name;
		reloadConfig();
	}

	public ConfigAPI reloadConfig() {
		file = new File(plugin.getDataFolder(), name);
		config = YamlConfiguration.loadConfiguration(file);
		InputStream defaults = plugin.getResource(file.getName());
		if (defaults != null) {
			YamlConfiguration loadConfig = YamlConfiguration
					.loadConfiguration(defaults);
			config.setDefaults(loadConfig);
		}
		return this;
	}

	public ConfigAPI saveConfig() {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public String message(String path) {
		return ChatColor.translateAlternateColorCodes('&',
				getConfig().getString(path));
	}

	public ConfigAPI saveDefaultConfig() {
		plugin.saveResource(name, false);
		return this;
	}

	public ConfigAPI saveDefault() {
		config.options().copyDefaults(true);
		saveConfig();
		return this;
	}

}