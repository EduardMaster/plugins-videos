
package net.eduard.teleportbow;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.teleportbow.command.TeleportBowReloadCommand;
import net.eduard.teleportbow.event.TeleportBowEvent;
import net.eduard.teleportbow.system.TeleportBow;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static FileConfiguration config;

	public static final Map<World, TeleportBow> TELEPORTBOW = new HashMap<>();

	public void onEnable() {
		plugin = this;
		config = plugin.getConfig();
		for (World world : Bukkit.getWorlds()) {
			ConfigurationSection section = config.createSection("TeleportBow." + world.getName());
			section.addDefault("enable", true);
			section.addDefault("sound", Sound.ENDERMAN_TELEPORT);
		}
		config.options().copyDefaults(true);
		plugin.saveConfig();
		getCommand("teleportbowreload").setExecutor(new TeleportBowReloadCommand());
		Bukkit.getPluginManager().registerEvents(new TeleportBowEvent(), this);
		reload();
	}

	public static void reload() {
		for (World world : Bukkit.getWorlds()) {
			ConfigurationSection section = config.getConfigurationSection("TeleportBow." + world.getName());
			boolean enabled = section.getBoolean("enable");
			Sound sound = Sound.valueOf(config.getString("sound"));
			TELEPORTBOW.put(world, new TeleportBow(enabled, sound));
		}
	}

	public static void save() {
		for (Entry<World, TeleportBow> map : TELEPORTBOW.entrySet()) {
			World world = map.getKey();
			TeleportBow teleporbow = map.getValue();
			ConfigurationSection section = config.getConfigurationSection("TeleportBow." + world.getName());
			section.set("enable", teleporbow.isEnabled());
			section.set("sound", teleporbow.getSound());
			plugin.saveConfig();
		}
	}

	public void onDisable() {
		save();
	}

}
