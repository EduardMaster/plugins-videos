package net.eduard.skywars.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Skywars {
	private static Location lobby = Bukkit.getWorlds().get(0).getSpawnLocation();
	private static Map<Player, ItemStack[]> armours = new HashMap<>();
	private static Map<Player, ItemStack[]> items = new HashMap<>();
	private static Map<Integer, Room> rooms = new HashMap<>();
	private static Map<Player, Room> players = new HashMap<>();
	private static Map<String, Arena> maps = new HashMap<>();
	public static final Storage STORAGE_OP = new Storage();
	public static final Storage STORAGE_NORMAL = new Storage();
	public static final Storage STORAGE_BASIC = new Storage();
	public static final Storage STORAGE_FEAST = new Storage();
	public static final Storage STORAGE_MINI_FEAST = new Storage();

	

	public static boolean has(String name) {
		return maps.containsKey(name.toLowerCase());
	}
	public static boolean isPlaying(Player player){
		return players.containsKey(player);
	}
	public static Room getGame(Player player){
		return players.get(player);
	}
	public static Arena get(String name) {
		return maps.get(name.toLowerCase());
	}
	public static Arena create(String name) {
		Arena map = new Arena(name);
		maps.put(name.toLowerCase(), map);
		return map;
	}
	public static void delete(String name) {
		maps.remove(name.toLowerCase());
	}
	public static void reload() {
		int id = 1;
		for (Arena map:maps.values()){
			Room room = new Room(map,id);
			room.init();
			rooms.put(id, room);
			id++;
		}
	}
	public static void save() {

	}
	public static void reload(String name) {

	}
	public static void save(String name) {

	}
	public static Location getLobby() {
		return lobby;
	}
	public static void setLobby(Location lobby) {
		Skywars.lobby = lobby;
	}
	



	
}
