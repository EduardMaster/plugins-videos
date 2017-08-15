package net.eduard.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Configs;
import net.eduard.api.game.ChatChannel;
import net.eduard.api.game.Sounds;
import net.eduard.api.gui.Slot;
import net.eduard.api.manager.Arena;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.Manager;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.GameAPI;
import net.eduard.api.setup.ItemAPI;
import net.eduard.api.setup.RexAPI;
import net.eduard.api.setup.WorldAPI;
import net.eduard.api.util.EmptyWorld;
import net.eduard.api.util.FakeOfflinePlayer;

@SuppressWarnings("unchecked")
public class API {

	public static final Sounds ROSNAR = Sounds.create(Sound.CAT_PURR);

	public static Map<String, ChatChannel> CHATS = new HashMap<>();
	public static ChatChannel CHAT;
	public static boolean CUSTOM_CHAT = false;
	public static String ONLY_PLAYER = "§cApenas jogadores pode fazer este comando!";
	public static String WORLD_NOT_EXISTS = "§cEste mundo $world não existe!";
	public static String PLAYER_NOT_EXISTS = "§cEste jogador $player não existe!";
	public static String PLUGIN_NOT_EXITS = "§cEste plugin $plugin não exite!";
	public static String NO_PERMISSION = "§cVoce não tem permissão para usar este comando!";
	public static String ON_JOIN = "§6O jogador $player entrou no Jogo!";
	public static String ON_QUIT = "§6O jogador $player saiu no Jogo!";
	public static String USAGE = "§FDigite: §c";

	public static String SERVER_TAG = "§b§lEduardAPI ";
	public static List<String> COMMANDS_ON = new ArrayList<>(
			Arrays.asList("on", "ativar"));
	public static List<String> COMMANDS_OFF = new ArrayList<>(
			Arrays.asList("off", "desativar"));
	public static Sounds SOUND_TELEPORT = Sounds
			.create(Sound.ENDERMAN_TELEPORT);
	public static Sounds SOUND_SUCCESS = Sounds.create(Sound.LEVEL_UP);
	public static Sounds SOUND_ERROR = Sounds.create(Sound.NOTE_BASS_DRUM);
	public static boolean NO_DEATH_MESSAGE = true;
	public static boolean NO_JOIN_MESSAGE = true;
	public static boolean NO_QUIT_MESSAGE = true;
	public static double MIN_WALK_SPEED = 0.2;
	public static double MIN_FLY_SPEED = 0.1;
	public static boolean AUTO_RESPAWN = true;
	public static boolean CHAT_SPIGOT = false;
	public static Map<Player, Arena> MAPS = new HashMap<>();
	public static Map<String, Arena> SCHEMATICS = new HashMap<>();
	public static Map<Player, Location> POSITION1 = new HashMap<>();
	public static Map<Player, Location> POSITION2 = new HashMap<>();
	public static Manager TIME;
	public static JavaPlugin PLUGIN;
	private static Map<String, Command> commands = new HashMap<>();

	static {
		try {
			PLUGIN = JavaPlugin.getProvidingPlugin(API.class);
			TIME = new Manager(PLUGIN);
		} catch (Exception e) {
		}
		try {
			Object map = RexAPI.getValue(Bukkit.getServer().getPluginManager(),
					"commandMap");

			commands = (Map<String, Command>) RexAPI.getValue(map,
					"knownCommands");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	@SafeVarargs
	public static void commands(ConfigSection section, CMD... cmds) {
		for (CMD cmd : cmds) {
			try {

				String name = cmd.getName();
				if (section != null) {
					if (section.contains(name)) {
						cmd = (CMD) section.get(name);

					}

				}
				cmd.register();
				if (section != null) {
					section.add(name, cmd);

				}
			} catch (Exception e) {
				ExtraAPI.consoleMessage(cmd.getName());
			}
		}
	}

	public static void loadMaps() {
		Configs cf = new Configs("Maps/");
		try {
			cf.getFile().mkdirs();
			for (File f : cf.getFile().listFiles()) {
				SCHEMATICS.put(f.getName().replace(".map", ""), Arena.load(f));
			}
		} catch (Exception e) {
		}
	}

	public static void saveMaps() {
		try {
			for (Entry<String, Arena> map : SCHEMATICS.entrySet()) {
				map.getValue().save(new File(PLUGIN.getDataFolder(),
						"Maps/" + map.getKey() + ".map"));
			}
		} catch (Exception e) {
		}
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}

	public static PluginCommand command(String commandName,
			CommandExecutor command) {
		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermissionMessage(
				API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}
	public static void setSlot(Inventory inventory, Slot slot) {
		inventory.setItem(slot.getSlot(), slot.getItem());
	}

	public static PluginCommand command(String commandName,
			CommandExecutor command, String permission) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(
				API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}

	public static boolean isIpProxy(String ip) {
		return ExtraAPI.isIpProxy(ip);
	}

	public static BukkitTask delay(Plugin plugin, long ticks, Runnable run) {
		return ExtraAPI.delay(plugin, ticks, run);
	}
	public static BukkitTask delays(Plugin plugin, long ticks, Runnable run) {
		return ExtraAPI.delays(plugin, ticks, run);
	}

	public static void event(Listener event) {

		event(event, getAPI());
	}

	public static void event(Listener event, Plugin plugin) {
		ExtraAPI.event(event, plugin);
	}

	public static boolean existsPlayer(CommandSender sender, String player) {

		Player p = Bukkit.getPlayer(player);
		if (p == null) {
			sender.sendMessage(
					API.PLAYER_NOT_EXISTS.replace("$player", player));
			return false;
		}
		return true;
	}
	public static boolean existsPlugin(CommandSender sender, String plugin) {

		Plugin p = getPlugin(plugin);
		if (p == null) {
			sender.sendMessage(API.PLUGIN_NOT_EXITS.replace("$plugin", plugin));
			return false;
		}
		return true;
	}
	public static Plugin getPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin);
	}
	public static boolean existsWorld(CommandSender sender, String name) {
		World world = Bukkit.getWorld(name);
		if (world == null) {
			sender.sendMessage(API.WORLD_NOT_EXISTS.replace("$world", name));
			return false;
		}
		return true;
	}

	public static JavaPlugin getAPI() {
		if (hasAPI()) {
			PLUGIN = (JavaPlugin) Bukkit.getPluginManager()
					.getPlugin("EduardAPI");
		}
		return PLUGIN;
	}

	public static boolean hasAPI() {

		return hasPlugin("EduardAPI");
	}

	public static boolean hasPlugin(String plugin) {
		return ExtraAPI.hasPlugin(plugin);
	}

	public static boolean getChance(double chance) {

		return ExtraAPI.getChance(chance);
	}
	public static Player getPlayer(String name) {
		return Bukkit.getPlayerExact(name);
	}
	public static World getWorld(String name) {
		return Bukkit.getWorld(name);
	}

	public static List<Player> getPlayers() {
		return GameAPI.getPlayers();
	}

	public static boolean hasPerm(CommandSender sender, String permission) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(
					API.NO_PERMISSION.replace("$permission", permission));
			return false;
		}
		return true;

	}

	public static World newEmptyWorld(String worldName) {
		World world = Bukkit.createWorld(new WorldCreator(worldName)
				.generateStructures(false).generator(new EmptyWorld()));
		world.getBlockAt(100, 100, 100).setType(Material.GLASS);
		world.setSpawnLocation(100, 101, 100);
		return world;
	}

	public static ItemStack newItem(Material material, String name) {
		ItemStack item = new ItemStack(material);
		ItemAPI.setName(item, name);
		return item;
	}

	public static ItemStack newItem(Material material, String name,
			int amount) {
		return newItem(material, name, amount, 0);
	}

	public static ItemStack newItem(Material material, String name, int amount,
			int data, String... lore) {

		ItemStack item = newItem(material, name);
		ItemAPI.setLore(item, lore);
		item.setAmount(amount);
		item.setDurability((short) data);
		return item;
	}

	public static ItemStack newItem(String name, Material material) {
		ItemStack item = new ItemStack(material);
		ItemAPI.setName(item, name);
		return item;
	}

	public static ItemStack newItem(String name, Material material, int data) {
		return newItem(material, name, 1, data);
	}

	public static ItemStack newItem(String name, Material material, int amount,
			int data, String... lore) {
		return newItem(material, name, amount, data, lore);
	}

	public static Scoreboard newScoreboard() {
		return Bukkit.getScoreboardManager().getNewScoreboard();

	}

	public static World newWorld(String world, Environment environment,
			WorldType worldType) {
		return new WorldCreator(world).environment(environment).type(worldType)
				.createWorld();
	}

	public static boolean noConsole(CommandSender sender) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(API.ONLY_PLAYER);
			return false;
		}
		return true;
	}

	public static boolean onlyPlayer(CommandSender sender) {
		return noConsole(sender);
	}

	public static void removeAliaseFromCommand(PluginCommand cmd,
			String aliase) {
		String cmdName = cmd.getName().toLowerCase();
		if (getCommands().containsKey(aliase)) {
			getCommands().remove(aliase);
			console("§bCommandAPI §fremovendo aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		} else {
			console("§bCommandAPI §fnao foi encontrado a aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		}
	}

	public static void removeCommand(String name) {
		if (getCommands().containsKey(name)) {
			PluginCommand cmd = Bukkit.getPluginCommand(name);
			String pluginName = cmd.getPlugin().getName();
			String cmdName = cmd.getName();
			for (String aliase : cmd.getAliases()) {
				removeAliaseFromCommand(cmd, aliase);
				removeAliaseFromCommand(cmd,
						pluginName.toLowerCase() + ":" + aliase);
			}
			try {
				getCommands().remove(cmd.getName());
			} catch (Exception e) {
			}
			console("§bCommandAPI §fremovendo o comando §a" + cmdName
					+ "§f do Plugin §b" + pluginName);
		} else {
			console("§bCommandAPI §fnao foi encontrado a commando §a" + name);
		}

	}

	public static void resetScoreboards() {

		for (Team teams : getMainScoreboard().getTeams()) {
			teams.unregister();
		}
		for (Objective objective : getMainScoreboard().getObjectives()) {
			objective.unregister();
		}
		for (Player player : getPlayers()) {
			player.setScoreboard(getMainScoreboard());
			player.setMaxHealth(20);
			player.setHealth(20);
			player.setHealthScaled(false);
		}
	}

	public static void addPermission(Player p, String permission) {
		p.addAttachment(API.PLUGIN, permission, true);
	}
	public static void removePermission(Player p, String permission) {
		p.addAttachment(API.PLUGIN, permission, false);
	}

	@SuppressWarnings("deprecation")
	public static Scoreboard applyScoreboard(Player player, String title,
			String... lines) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("score", "dummy");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int id = 15;
		for (String line : lines) {
			String empty = ChatColor.values()[id - 1].toString();
			obj.getScore(new FakeOfflinePlayer(line.isEmpty() ? empty : line))
					.setScore(id);;
			id--;
			if (id == 0) {
				break;
			}
		}

		player.setScoreboard(board);
		return board;
	}
	public static Scoreboard newScoreboard(Player player, String title,
			String... lines) {
		return applyScoreboard(player, title, lines);
	}

	public static int getRandomInt(int minValue, int maxValue) {
		return ExtraAPI.getRandomInt(minValue, maxValue);
	}
	public static void chat(CommandSender sender, Object... objects) {
		sender.sendMessage(API.SERVER_TAG + ExtraAPI.getMessage(objects));
	}
	public static void all(Object... objects) {

		broadcast(objects);
		console(objects);
	}

	public static void broadcast(Object... objects) {

		for (Player p : API.getPlayers()) {
			chat(p, objects);
		}
	}

	public static void broadcast(String message, String permision) {
		for (Player p : API.getPlayers()) {
			if (p.hasPermission(permision)) {
				chat(p, message);
			}
		}
	}

	public static void console(Object... objects) {

		chat(Bukkit.getConsoleSender(), objects);
	}
	public static void send(Collection<Player> players, Object... objects) {
		for (Player player : players) {
			chat(player, objects);
		}

	}

	public static long getNow() {
		return ExtraAPI.getNow();
	}

	public static void callEvent(Event event) {
		ExtraAPI.callEvent(event);
	}

	public static Scoreboard getMainScoreboard() {
		return ExtraAPI.getMainScoreboard();
	}

	public static BukkitTask timer(Plugin plugin, long ticks, Runnable run) {
		return ExtraAPI.timer(plugin, ticks, run);
	}

	public static Inventory newInventory(String title, int size) {
		return ItemAPI.newInventory(title, size);
	}
	public static Inventory createInventory(String title, int size) {
		return ItemAPI.newInventory(title, size);
	}
	public static Location getSpawn() {
		return WorldAPI.getSpawn();
	}
	public static void runCommand(String command) {
		ExtraAPI.runCommand(command);
	}

}
