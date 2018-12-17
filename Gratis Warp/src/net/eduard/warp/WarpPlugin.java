
package net.eduard.warp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import net.eduard.api.server.EduardPlugin;
import net.eduard.warp.command.DeleteWarpCommand;
import net.eduard.warp.command.SetWarpCommand;
import net.eduard.warp.command.WarpCommand;
import net.eduard.warp.command.WarpsCommand;
import net.eduard.warp.event.WarpEvent;
import net.eduard.warp.manager.Warp;

public class WarpPlugin extends EduardPlugin {
	private static WarpPlugin plugin;

	public static WarpPlugin getInstance() {
		return plugin;
	}
 
	@Override
	public void onEnable() {
		plugin = this;

		getCommand("setwarp").setExecutor(new SetWarpCommand());
		getCommand("warp").setExecutor(new WarpCommand());
		getCommand("deletewarp").setExecutor(new DeleteWarpCommand());
		getCommand("warps").setExecutor(new WarpsCommand());
		Bukkit.getPluginManager().registerEvents(new WarpEvent(), this);
		reloadWarps();
	}

	private static HashMap<String, Warp> warps = new HashMap<>();

	public static HashMap<String, Warp> getWarps() {
		return warps;
	}

	@Override
	public void onDisable() {
		saveWarps();
	}

	public static void saveWarps() {
		// Salvar usando multi configs
		// Simples configs :D
		for (Entry<String, Warp> map : warps.entrySet()) {
			String name = map.getKey();
			Warp warp = map.getValue();
			File file = new File(plugin.getDataFolder(), "Warps/" + name + ".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set("message", warp.getWarpMessage().replace("§", "&"));
			String tag = "Location.";
			Location loc = warp.getWarpLocation();
			config.set(tag + "world", loc.getWorld().getName());
			config.set(tag + "x", loc.getX());
			config.set(tag + "y", loc.getY());
			config.set(tag + "z", loc.getZ());
			config.set(tag + "yaw", loc.getYaw());
			config.set(tag + "pitch", loc.getPitch());
			config.set("name", warp.getWarpName());
			try {
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// Sistema pronto agora falta o de carregar

	}

	public static void reloadWarps() {
		File folder = new File(plugin.getDataFolder(), "/Warps/");
		folder.mkdirs();
		// Vou tentar assim
		if (folder.list().length == 0)
			return;
		for (File file : folder.listFiles()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			Warp warp = new Warp();
			String name = config.getString("name");
			ConfigurationSection sec = config.getConfigurationSection("Location");
			World world = Bukkit.getWorld(sec.getString("world"));
			double x = sec.getDouble("x");
			double y = sec.getDouble("y");
			double z = sec.getDouble("z");
			double yaw = sec.getDouble("yaw");
			double pitch = sec.getDouble("pitch");
			warp.setWarpLocation(new Location(world, x, y, z, (float) yaw, (float) pitch));
			warp.setWarpMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message")));
			warp.setWarpName(name);
			WarpPlugin.warps.put(name.toLowerCase(), warp);
			// Leitura completa
			// falta mensagem
			// Porque eu uso esse metodo em vez do Replace
			// galera é pq esse eh melhor evita bugs
		}
	}

}
