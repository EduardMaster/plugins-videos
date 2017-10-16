package net.eduard.warp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main plugin;
	private static Map<String, Warp> warps = new HashMap<>();
	private static FileConfiguration messages;

	public static Configs config;

	@Override
	public void onEnable() {
		plugin = this;
		config = new Configs("config.yml", this);
		getCommand("warp").setExecutor(new WarpCommand());
		Bukkit.getPluginManager().registerEvents(new WarpListener(), this);
		saveResource("messages.yml", true);
		reload();
		File pasta = new File(getDataFolder(), "Warps/");
		pasta.mkdirs();
		for (String nome : pasta.list()) {
			Configs config = new Configs("Warps/" + nome,	plugin);
			String warpnome = config.getString("name");
			World world = Bukkit.getWorld(config.getString("world"));
			double x = config.getDouble("x");
			double y = config.getDouble("y");
			double z = config.getDouble("z");
			float yaw = (float) config.getDouble("yaw");
			float pitch = (float) config.getDouble("pitch");
			Warp warp = new Warp(warpnome,
					new Location(world, x, y, z, yaw, pitch));
			warps.put(warpnome.toLowerCase(), warp);
		}

		// recarregarWarps();
	}
	@Override
	public void onDisable() {
		// salvarWarps();

		new File(getDataFolder(), "Warps").delete();

		for (Warp warp : warps.values()) {
			setWarp(warp.getName().toLowerCase(), warp.getLocation());
		}

	}
	public void recarregarWarps() {

		if (config.contains("Warps")) {

			for (String nome : config.getSection("Warps").getKeys(false)) {
				Warp warp = new Warp(nome, config.getLocation("Warps." + nome));
				warps.put(nome, warp);
			}

		}

	}
	public void salvarWarps() {

		for (Warp warp : warps.values()) {
			String secao = "Warps." + warp.getName().toLowerCase();
			config.setLocation(secao, warp.getLocation());

		}
		config.saveConfig();

	}

	public static Map<String, Warp> getWarp() {
		return warps;
	}

	public static Collection<Warp> getWarps() {
		return warps.values();
	}
	public static List<String> getWarpsNames() {
		List<String> list = new ArrayList<>();
		for (Warp warp : getWarps()) {
			list.add(warp.getName());
		}
		return list;
	}

	public static String message(String path) {
		return ChatColor.translateAlternateColorCodes('&',
				messages.getString(path));
	}

	public static void reload() {
		messages = YamlConfiguration.loadConfiguration(
				new File(plugin.getDataFolder(), "messages.yml"));

	}

	public static void setWarp(String name, Location location) {
		Configs config = new Configs("Warps/" + name.toLowerCase() + ".yml",
				plugin);
		config.set("name", name);
		config.set("world", location.getWorld().getName());
		config.set("x", location.getX());
		config.set("y", location.getY());
		config.set("z", location.getZ());
		config.set("yaw", location.getYaw());
		config.set("pitch", location.getPitch());
		config.saveConfig();
	}

}
