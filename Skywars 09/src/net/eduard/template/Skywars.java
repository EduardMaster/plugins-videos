package net.eduard.template;

import java.util.HashMap;
import java.util.Map;

public class Skywars {

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

}
