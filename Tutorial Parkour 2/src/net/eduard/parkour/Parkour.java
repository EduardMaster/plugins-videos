package net.eduard.parkour;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.config.Config;
import net.eduard.api.lib.core.Mine;
import net.eduard.api.server.minigame.Game;
import net.eduard.api.server.minigame.GameMap;
import net.eduard.api.server.minigame.Minigame;

public class Parkour extends Minigame {
	private Main plugin;
	public Parkour(Main main) {
		super("Parkour");
		this.plugin = main;
	}

	public ItemStack setSpawnItem = Mine.newItem(Material.STICK,
			"§e§lEscolha o Spawn");
	public ItemStack setEndItem = Mine.newItem(Material.BLAZE_ROD,
			"§d§lEscolha o Fim");
	public ItemStack confirmItem = Mine.newItem(Material.BEACON,
			"§a§lConfime o Parkour");
	public ItemStack cancelItem = Mine.newItem(Material.BED,
			"§c§lDelete o Parkour");
	private HashMap<Player, Location> checkpoints = new HashMap<>();
	private HashMap<Player, Integer> falls = new HashMap<>();
	private HashMap<Player, GameMap> criando = new HashMap<>();
	private Material checker = Material.DIAMOND_BLOCK;
	private double reward = 100;

	public boolean join(Player player, String name) {
		if (existsMap(name) & !isPlaying(player)) {
			joinPlayer(getGame(name), player);
			Mine.saveItems(player);
			Mine.refreshAll(player);
			chat("Join", player);
			return true;
		}
		return false;
	}
	
	
	public void chat(String key, Player player) {
		player.sendMessage(
				Mine.getReplacers(plugin.config().message(key), player));

	}

	public void leave(Player player) {
		if (hasLobby())
			player.teleport(getLobby());
		chat("Quit", player);
		remove(player);
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

	public void win(Player player) {
		Game arena = getGame(player);
		chat("Win", player);
		for (Player p : arena.getPlaying()) {
			chat("WinBroadcast", p);
		}
		if (hasLobby())
			player.teleport(getLobby());
		remove(player);
	}

	public int getFalls(Player player) {
		return falls.getOrDefault(player, 0);
	}
	public void updateFall(Player player) {
		falls.put(player, getFalls(player));
	}

	public void toCheckpoint(Player p) {
		if (checkpoints.containsKey(p)) {
			p.teleport(checkpoints.get(p));
		} else {
			Game game = getGame(p);
			p.teleport(game.getMap().getSpawn());
		}

	}

	public void updateCheckpoint(Player p) {
		if (checkpoints.containsKey(p)) {
			Location check = checkpoints.get(p);
			checkpoints.put(p, p.getLocation());
			if (Mine.equals(check, p.getLocation())) {
				return;
			}
		}
		checkpoints.put(p, p.getLocation());
		chat("CheckPoint", p);

	}

	public Config getConfig(String name) {
		return plugin.config().createConfig("Arenas/" + name + ".yml");
	}

	public static void play(Player p) {

	}

	public void createNewMap(Player p, String name) {
		PlayerInventory inv = p.getInventory();
		inv.setItem(0, confirmItem);
		inv.setItem(2, setSpawnItem);
		inv.setItem(6, setEndItem);
		inv.setItem(8, cancelItem);
		GameMap map = new GameMap(this, name);
		p.setGameMode(GameMode.CREATIVE);
		map.setSpawn(p.getLocation());
		map.getLocations().put("end", p.getLocation());
		criando.put(p, map);
		chat("Creating", p);

	}
	// criando mapa

	@EventHandler
	public void criandoMapa(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() == null)
				return;
			if (criando.containsKey(p)) {
				GameMap map = criando.get(p);
				if (e.getItem().equals(setSpawnItem)) {
					chat("SetSpawn", p);
					map.setSpawn(e.getClickedBlock().getLocation());
				} else if (e.getItem().equals(setEndItem)) {

					chat("SetEnd", p);
					map.getLocations().put("end",
							e.getClickedBlock().getLocation());

				} else if (e.getItem().equals(setEndItem)) {

					chat("SetEnd", p);
					map.getLocations().put("end",
							e.getClickedBlock().getLocation());

				} else if (e.getItem().equals(confirmItem)) {

					chat("Create", p);
					map.getLocations().put("end",
							e.getClickedBlock().getLocation());
					new Game(this, map);
					criando.remove(p);
					Mine.refreshAll(p);
				} else if (e.getItem().equals(confirmItem)) {

					chat("Delete", p);
					map.getLocations().put("end",
							e.getClickedBlock().getLocation());
					criando.remove(p);
					removeMap(map.getName());
					Mine.refreshAll(p);
				}
			}
		}
	}

	public void remove(Player player) {
		Mine.getItems(player);
		falls.remove(player);
		checkpoints.remove(player);
		super.remove(player);

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void aoRenascer(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		if (isPlaying(p)) {
			e.setRespawnLocation(getLobby());
			updateFall(p);

		}
	}
	@EventHandler
	public void aoLevarDano(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				if (isPlaying(p)) {
					updateFall(p);
					toCheckpoint(p);
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void aoTirarPvP(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player
				& e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (isPlaying(p)) {
				e.setCancelled(true);
			}
			if (isPlaying(d)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void aoDigitarComando(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		if (isPlaying(p)) {
			if (Mine.startWith(e.getMessage(), "/leave")
					|| Mine.startWith(e.getMessage(), "/sair")) {
				leave(p);
			} else {
				chat("OnlyQuit", p);
			}
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void aoMover(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (isPlaying(p)) {
			GameMap map = getGame(p).getMap();
			if (Mine.equals(e.getTo(), map.getLocations().get("end"))) {
				win(p);
			}
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN)
					.getType() == getChecker()) {
				updateCheckpoint(p);
			}
		}
	}
	@EventHandler
	public void aoSair(PlayerQuitEvent e) {
		remove(e.getPlayer());
	}
	@EventHandler
	public void aoSerKitado(PlayerKickEvent e) {
		remove(e.getPlayer());
	}
	@EventHandler
	public void semFome(FoodLevelChangeEvent e) {

		HumanEntity who = e.getEntity();
		if (who instanceof Player) {
			Player p = (Player) who;
			if (isPlaying(p)) {
				e.setFoodLevel(20);
				p.setSaturation(20);
				p.setExhaustion(0);
			}

		}
	}

	@Override
	public void event(Game room) {
		// TODO Auto-generated method stub

	}


}
