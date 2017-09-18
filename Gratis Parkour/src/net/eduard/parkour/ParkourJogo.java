package net.eduard.parkour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.GameAPI;
import net.eduard.api.setup.WorldAPI;
import net.eduard.api.setup.ScoreAPI.DisplayBoard;

public class ParkourJogo implements Listener{

	private static Map<String, ParkourJogo> jogos = new HashMap<>();
	private static Map<Player, ParkourJogo> playing = new HashMap<>();
	private static HashMap<Player, DisplayBoard> scoreboard = new HashMap<>();
	private static HashMap<Player, Location> checkpoints = new HashMap<>();
	private static HashMap<Player, ItemStack[]> items = new HashMap<>();
	private static Location lobby;
	private Material checker = Material.DIAMOND_BLOCK;
	private double reward = 100;




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

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					p.sendMessage(map.chat("SetSpawn"));
					map.setSpawn(e.getClickedBlock().getLocation());
				}
			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(setEndItem, new ClickEffect(){

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					p.sendMessage(map.chat("SetEnd"));
					map.setEnd(e.getClickedBlock().getLocation());
				}
			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(addCheckpointItem, new ClickEffect(){
			

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					p.sendMessage(Arena.message("SetCheck"));
					e.getClickedBlock().setType(map.getChecker());
				}
			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(confirmItem, new ClickEffect(){

			@Override
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

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
		new Click(cancelItem, new ClickEffect(){

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (maps.containsKey(p)) {
					Arena map = maps.get(p);
					maps.remove(p);
					GameAPI.refreshAll(p);
					p.sendMessage(map.chat("Delete"));
				}
			}

			@Override
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

	public void save(ConfigSection section, Object value) {
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

	public Object get(ConfigSection section) {
		Arena arena = new Arena();
		arena.setChecker(
				Material.valueOf(section.getString("checker").toUpperCase()));
		arena.setEnabled(section.getBoolean("enabled"));
		arena.setSpawn(section.getLocation("Spawn"));
		arena.setEnd(section.getLocation("End"));
		arena.setReward(section.getDouble("reward"));
		arena.setName(section.getString("name"));
		if (section.contains("Effects")) {
			for (ConfigSection sec : section.getSection("Effects").getValues()) {
				arena.effects.add((Potions) sec.getValue());
			}
		}
		return arena;
	}
	private List<Player> players = new ArrayList<>();
	private Location spawn;
	private Location end;
	public boolean isPlaying(Player player	) {
		return players.contains(player);
	}
	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				if (isPlaying(p)) {
					Arena.updateFall(p);
					Arena.toCheckpoint(p);
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				if (Arena.isPlaying(p)) {
					Arena.updateFall(p);
					Arena.toCheckpoint(p);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			Arena arena = Arena.getArena(p);
			Arena.updateFall(p);
			e.setRespawnLocation(arena.getSpawn());
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player
				& e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (Arena.isPlaying(p)) {
				e.setCancelled(true);
			}
			if (Arena.isPlaying(d)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			if (ExtraAPI.startWith(e.getMessage(), "/leave")
					| ExtraAPI.startWith(e.getMessage(), "/sair")) {
				Arena.leave(p);
			} else {
				p.sendMessage(Arena.message("OnlyQuit"));
			}
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			Arena arena = Arena.getArena(p);
			if (WorldAPI.equals(e.getTo(), arena.getEnd())) {
				Arena.win(p);
			}
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN)
					.getType() == arena.getChecker()) {
				Arena.updateCheckpoint(p);
			}
		}
	}
	@EventHandler
	public void event(PlayerQuitEvent e) {
		Arena.remove(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerKickEvent e) {
		Arena.remove(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Arena.join(e.getPlayer());
	}
	@EventHandler
	public void event(FoodLevelChangeEvent e) {

		HumanEntity who = e.getEntity();
		if (who instanceof Player) {
			Player p = (Player) who;
			if (Arena.isPlaying(p)) {
				e.setFoodLevel(20);
				p.setSaturation(20);
				p.setExhaustion(0);
			}

		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			Arena arena = Arena.getArena(p);
			Arena.updateFall(p);
			e.setRespawnLocation(arena.getSpawn());
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player
				& e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (Arena.isPlaying(p)) {
				e.setCancelled(true);
			}
			if (Arena.isPlaying(d)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			if (ExtraAPI.startWith(e.getMessage(), "/leave")
					| ExtraAPI.startWith(e.getMessage(), "/sair")) {
				Arena.leave(p);
			} else {
				p.sendMessage(Arena.message("OnlyQuit"));
			}
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			Arena arena = Arena.getArena(p);
			if (WorldAPI.equals(e.getTo(), arena.getEnd())) {
				Arena.win(p);
			}
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN)
					.getType() == arena.getChecker()) {
				Arena.updateCheckpoint(p);
			}
		}
	}
	@EventHandler
	public void event(PlayerQuitEvent e) {
		Arena.remove(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerKickEvent e) {
		Arena.remove(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Arena.join(e.getPlayer());
	}
	@EventHandler
	public void event(FoodLevelChangeEvent e) {

		HumanEntity who = e.getEntity();
		if (who instanceof Player) {
			Player p = (Player) who;
			if (Arena.isPlaying(p)) {
				e.setFoodLevel(20);
				p.setSaturation(20);
				p.setExhaustion(0);
			}

		}
	}
	

}
