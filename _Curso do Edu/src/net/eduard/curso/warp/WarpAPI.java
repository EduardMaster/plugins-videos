package net.eduard.curso.warp;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Location;

import net.eduard.api.lib.core.ConfigAPI;
import net.eduard.curso.CursoMain;

public class WarpAPI {

	private static ConfigAPI config = new ConfigAPI("warps.yml", CursoMain.getInstance());

	public static void setWarp(String name, Location local) {
		config.set("Warps." + name.toLowerCase(), local);
	}

	public static boolean hasWarps() {
		return config.contains("Warps") && getWarps().size() > 0;
	}

	public static Location getWarp(String name) {
		return config.getLocation("Warps." + name.toLowerCase());
	}

	public static boolean hasWarp(String name) {
		return config.contains("Warps." + name.toLowerCase());
	}

	public static void removeWarp(String name) {
		config.remove("Warps." + name.toLowerCase());
	}

	public static ConfigAPI getConfig() {
		return config;
	}

	public static List<String> getWarps() {
		return config.getSection("Warps").getKeys(false).stream().collect(Collectors.toList());
	}

	public static void setConfig(ConfigAPI config) {
		WarpAPI.config = config;
	}

}
