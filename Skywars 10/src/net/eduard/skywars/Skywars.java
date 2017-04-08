package net.eduard.skywars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Skywars {
	private static Location looby = Bukkit.getWorlds().get(0).getSpawnLocation();
	public static final Storage STORAGE_OP = new Storage();
	public static final Storage STORAGE_NORMAL = new Storage();
	public static final Storage STORAGE_BASIC = new Storage();
	public static final Storage STORAGE_FEAST = new Storage();
	public static final Storage STORAGE_MINI_FEAST = new Storage();

	private static Map<String, Arena> maps = new HashMap<>();

	public static boolean hasMap(String name) {
		return maps.containsKey(name.toLowerCase());
	}
	public static Arena getMap(String name) {
		return maps.get(name.toLowerCase());
	}
	public static Arena createMap(String name) {
		Arena map = new Arena(name);
		maps.put(name.toLowerCase(), map);
		return map;
	}
	public static void deleteMap(String name) {
		maps.remove(name.toLowerCase());
	}
	public static void reloadMaps() {

	}
	public static void saveMaps() {

	}
	public static void reloadMap(String name) {

	}
	public static void saveMap(String name) {

	}
	public static Location getLooby() {
		return looby;
	}
	public static void setLooby(Location looby) {
		Skywars.looby = looby;
	}

}
