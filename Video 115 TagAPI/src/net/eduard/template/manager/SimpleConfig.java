package net.eduard.template.manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleConfig {

	private JavaPlugin plugin;
	private String name;
	private File file;
	private FileConfiguration config;

	public String getName() {
		return name;
	}

	public SimpleConfig setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
		return this;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public SimpleConfig(String name, JavaPlugin plugin) {
		this.plugin = plugin;
		this.name = name;
		reloadConfig();
	}

	public SimpleConfig reloadConfig() {
		file = new File(plugin.getDataFolder(), name);
		config = YamlConfiguration.loadConfiguration(file);
		InputStream defaults = plugin.getResource(file.getName());
		if (defaults != null) {
			YamlConfiguration loadConfig = YamlConfiguration.loadConfiguration(defaults);
			config.setDefaults(loadConfig);
		}
		return this;
	}

	public SimpleConfig saveConfig() {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public SimpleConfig saveDefaultConfig() {
		plugin.saveResource(name, false);
		return this;
	}

	public SimpleConfig saveDefault() {
		config.options().copyDefaults(true);
		saveConfig();
		return this;
	}

}