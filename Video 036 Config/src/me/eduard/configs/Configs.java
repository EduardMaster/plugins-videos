
package me.eduard.configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configs {

	private File file;

	private FileConfiguration config;

	private JavaPlugin plugin;

	public Configs(JavaPlugin plugin, String name) {
		this.plugin = plugin;
		file = new File(plugin.getDataFolder(), name);
		reloadConfig();
	}

	public FileConfiguration getConfig() {

		return config;
	}

	public File getFile() {

		return file;
	}

	public void reloadConfig() {

		config = YamlConfiguration.loadConfiguration(file);
		InputStream imputStream = plugin.getResource(file.getName());
		if (imputStream != null) {
			YamlConfiguration imputConfig =
				YamlConfiguration.loadConfiguration(imputStream);
			getConfig().setDefaults(imputConfig);
		}
	}

	public void saveConfig() {

		try {
			getConfig().save(file);
		} catch (IOException ex) {
		}
	}

	public void saveDefault() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void saveDefaultConfig() {

		plugin.saveResource(file.getName(), true);
	}

}
