package net.eduard.parkour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.Save;
import net.eduard.api.config.Section;
import net.eduard.api.game.Potions;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Gui;
import net.eduard.api.gui.Slot;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.Score;
import net.eduard.api.manager.WorldAPI;
import net.eduard.api.util.PlayerEffect;

public class Arena implements Save {
	private static final Config STATS = Main.config.createConfig("Stats.yml");
	private static Gui gui;
	private static HashMap<String, Arena> arenas = new HashMap<>();
	private static HashMap<Player, Arena> players = new HashMap<>();
	private static HashMap<Player, Score> scoreboard = new HashMap<>();
	private static HashMap<Player, Location> checkpoints = new HashMap<>();
	private static HashMap<Player, Stats> stats = new HashMap<>();
	private static HashMap<Player, ItemStack[]> items = new HashMap<>();
	private static Location lobby;
	private String name;
	private boolean enabled;
	private Location spawn, end;
	private Material checker = Material.DIAMOND_BLOCK;
	private double reward = 100;
	private ArrayList<Potions> effects = new ArrayList<>();

	public Arena(String name) {
		this.name = name;
	}

	public Arena() {
	}

	public static boolean isPlaying(Player player) {
		return players.containsKey(player);
	}

	public static Arena getArena(Player player) {
		return players.get(player);
	}

	public static void save() {
		for (Arena arena : arenas.values()) {
			Config config = getConfig(arena.name.toLowerCase());
			config.set("Parkour", arena);
			config.saveConfig();
		}
	}

	public static void reload() {
		arenas.clear();
		for (Config config : Main.config.createConfig("Arenas/").getConfigs()) {
			System.out.println(config);
			Arena arena = (Arena) config.get("Parkour");
			arenas.put(config.getTitle(), arena);
		}
		
		gui = new Gui(6, message("guiName"));
		for (Arena arena:arenas.values()) {
			gui.add((Slot) new Slot(API.newItem(Material.EMERALD_BLOCK, "§bMapa: "+arena.getName())).setEffect(new PlayerEffect() {
				
				@Override
				public void effect(Player p) {
					p.closeInventory();
					p.teleport(arena.getSpawn());
					players.put(p, arena);
					Score score = new Score("§6§lParkour");
					score.empty(15);
					score.set(14, "§a§l"+arena);
					score.empty(13);
					score.set(12, "§aTentativas:");
					score.set(11, "§e0 vezes");
					score.empty(10);
					score.set(9, "");
				}
			}));
		}
	}

	public static boolean join(Player player, String name) {
		if (exists(name) & !isPlaying(player)) {
			Arena arena = getArena(name);
			items.put(player, player.getInventory().getContents());
			GameAPI.refreshAll(player);
			player.sendMessage(arena.chat("Join"));
			return true;
		}
		return false;
	}

	public static String message(String key) {
		return Main.config.message(key);
	}

	public String chat(String key) {
		return Main.config.message(key).replace("$arena", name)
				.replace("$money", "" + reward);
	}

	public static boolean exists(String name) {
		return arenas.containsKey(name.toLowerCase());
	}

	public static Arena getArena(String name) {
		return arenas.get(name.toLowerCase());
	}

	public static void leave(Player player) {
		if (hasLobby())
			player.teleport(lobby);
		player.sendMessage(message("Quit"));
		remove(player);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
	}

	public Material getChecker() {
		return checker;
	}

	public void setChecker(Material checker) {
		this.checker = checker;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public ArrayList<Potions> getEffects() {
		return effects;
	}

	public void setEffects(ArrayList<Potions> effects) {
		this.effects = effects;
	}

	public static HashMap<Player, Arena> getPlayers() {
		return players;
	}

	public static HashMap<Player, Score> getScoreboard() {
		return scoreboard;
	}

	public static HashMap<Player, Stats> getStats() {
		return stats;
	}

	public static void win(Player player) {
		Arena arena = getArena(player);
		player.sendMessage(arena.chat("Win"));
		for (Player p : players.keySet()) {
			p.sendMessage(arena.chat("WinBroadcast").replace("$player",
					player.getName()));
		}
		if (hasLobby())
			player.teleport(lobby);
		remove(player);
	}

	public static void updateFall(Player player) {
		Stats stat = stats.get(player);
		stat.setFalls(stat.getFalls() + 1);
	}

	public static void toCheckpoint(Player p) {
		if (checkpoints.containsKey(p)) {
			p.teleport(checkpoints.get(p));
		}
		p.teleport(getArena(p).getSpawn());
	}

	public static void updateCheckpoint(Player p) {
		if (checkpoints.containsKey(p)) {
			Location check = checkpoints.get(p);
			checkpoints.put(p, p.getLocation());
			if (WorldAPI.equals(check, p.getLocation())) {
				return;
			}
		}
		checkpoints.put(p, p.getLocation());
		p.sendMessage(Arena.message("CheckPoint"));

	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public static Location getLobby() {
		return lobby;
	}

	public static void setLobby(Location lobby) {
		Arena.lobby = lobby;
	}

	public static void delete(String name) {
		getConfig(name).deleteConfig();
		arenas.remove(name);
	}

	public static Config getConfig(String name) {
		return Main.config.createConfig("Arenas/" + name + ".yml");
	}

	public static boolean hasLobby() {
		return lobby != null;
	}

	public static void play(Player p) {

	}

	public static void newItem(Player p, String name) {
		PlayerInventory inv = p.getInventory();
		inv.setItem(0, confirmItem);
		inv.setItem(2, setSpawnItem);
		inv.setItem(4, addCheckpointItem);
		inv.setItem(6, setEndItem);
		inv.setItem(8, cancelItem);
		p.setGameMode(GameMode.CREATIVE);
		Arena map = new Arena(name);
		map.setSpawn(p.getLocation());
		map.setEnd(p.getLocation());
		p.sendMessage(map.chat("Creating"));
		maps.put(p, map);
	}

	private static HashMap<Player, Arena> maps = new HashMap<>();
	public static ItemStack setSpawnItem = API.newItem(Material.STICK,
			"§e§lEscolha o Spawn");
	public static ItemStack setEndItem = API.newItem(Material.BLAZE_ROD,
			"§d§lEscolha o Fim");
	public static ItemStack addCheckpointItem = API.newItem(Material.RAILS,
			"§b§lAdicione um Checkpoint");
	public static ItemStack confirmItem = API.newItem(Material.BEACON,
			"§a§lConfime o Parkour");
	public static ItemStack cancelItem = API.newItem(Material.BED,
			"§c§lDelete o Parkour");
	static {
		new Click(setSpawnItem, new ClickEffect(){

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					p.sendMessage(map.chat("SetSpawn"));
					map.setSpawn(e.getClickedBlock().getLocation());
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(setEndItem, new ClickEffect(){

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					p.sendMessage(map.chat("SetEnd"));
					map.setEnd(e.getClickedBlock().getLocation());
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(addCheckpointItem, new ClickEffect(){
			

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					p.sendMessage(Arena.message("SetCheck"));
					e.getClickedBlock().setType(map.getChecker());
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(confirmItem, new ClickEffect(){

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					arenas.put(map.getName().toLowerCase(), map);
					p.sendMessage(map.chat("Create"));
					maps.remove(p);
					GameAPI.refreshAll(p);
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(cancelItem, new ClickEffect(){

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					maps.remove(p);
					GameAPI.refreshAll(p);
					p.sendMessage(map.chat("Delete"));
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void remove(Player player) {
		STATS.set(player.getUniqueId().toString(), getStat(player));
		scoreboard.remove(player);
		checkpoints.remove(player);
		player.getInventory().setContents(items.get(player));
		items.remove(player);
		players.remove(player);

	}

	public static Stats getStat(Player player) {
		return stats.get(player);
	}

	public static void join(Player player) {
		if (!stats.containsKey(player)) {
			if (STATS.contains(player.getUniqueId().toString())) {
				stats.put(player,
						(Stats) STATS.get(player.getUniqueId().toString()));
			} else {
				stats.put(player, new Stats(player));
			}
		}

	}

	public static void saveStats() {
		for (Entry<Player, Stats> e : stats.entrySet()) {
			STATS.set(e.getKey().getUniqueId().toString(), e.getValue());
		}
		STATS.saveConfig();
	}

	public void save(Section section, Object value) {
		Arena arena = (Arena) value;
		section.set("checker", arena.checker);
		section.set("enabled", arena.enabled);
		section.set("Spawn", arena.spawn);
		section.set("End", arena.end);
		section.set("reward", arena.reward);
		section.set("name", arena.name);
		if (!arena.effects.isEmpty()) {
			int id = 1;
			for (Potions effect : arena.effects) {
				section.set("Effects.effect-" + id, effect);
				id++;
			}
		}
	}

	public Object get(Section section) {
		Arena arena = new Arena();
		arena.setChecker(
				Material.valueOf(section.getString("checker").toUpperCase()));
		arena.setEnabled(section.getBoolean("enabled"));
		arena.setSpawn(section.getLocation("Spawn"));
		arena.setEnd(section.getLocation("End"));
		arena.setReward(section.getDouble("reward"));
		arena.setName(section.getString("name"));
		if (section.contains("Effects")) {
			for (Section sec : section.getSection("Effects").getValues()) {
				arena.effects.add((Potions) sec.getValue());
			}
		}
		return arena;
	}
}
