
package net.eduard.template;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.template.command.TemplateCommand;

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
	}

	public void onDisable() {

	}

}
