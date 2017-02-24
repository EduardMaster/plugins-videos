
package net.eduard.teleportbow;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.teleportbow.event.TeleportBowEvent;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public static FileConfiguration config;

	public void onEnable() {
		plugin = this;
		config = plugin.getConfig();
		
		for (World world:Bukkit.getWorlds()) {
			ConfigurationSection section = config.createSection("TeleportBow."+world.getName());
			section.addDefault("enable", true);
			section.addDefault("sound", Sound.ENDERMAN_TELEPORT);
		}
		config.addDefault("valor3", "valor4");
		config.options().copyDefaults(true);
		saveConfig();
		
		Bukkit.getPluginManager().registerEvents(new TeleportBowEvent(), this);
	}

}
