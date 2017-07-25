package net.eduard.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
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
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Configs;
import net.eduard.api.game.Sounds;
import net.eduard.api.game.Tag;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.ItemAPI;
import net.eduard.api.manager.Manager;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.minigame.Arena;
import net.eduard.api.util.FakeOfflinePlayer;
import net.eduard.api.world.EmptyWorld;

@SuppressWarnings("unchecked")
public class API {

	public static final Sounds ROSNAR = Sounds.create(Sound.CAT_PURR);
	public static final float TNT = 4F;
	public static final float CREEPER = 3F;
	public static final float WALKING_VELOCITY = -0.08F;
	public static final int DAY_IN_HOUR = 24;
	public static final int DAY_IN_MINUTES = DAY_IN_HOUR * 60;
	public static final int DAY_IN_SECONDS = DAY_IN_MINUTES * 60;
	public static final long DAY_IN_TICKS = DAY_IN_SECONDS * 20;
	public static final long DAY_IN_LONG = DAY_IN_TICKS * 50;
	public static final String CHAT_CHANNEL = "§e(L)";
	public static boolean AUTO_SAVE_CONFIG=true;
	public static boolean CUSTOM_CHAT = false;
	public static String CHAT_FORMAT = "$channel $player: $message";
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
	public static boolean NO_DEATH_MESSAGE = true;
	public static boolean NO_JOIN_MESSAGE = true;
	public static boolean NO_QUIT_MESSAGE = true;
	public static double MIN_WALK_SPEED = 0.2;
	public static double MIN_FLY_SPEED = 0.1;
	public static boolean AUTO_RESPAWN = true;
	public static Random RANDOM = new Random();
	public static Map<Player, Arena> MAPS = new HashMap<>();
	public static Map<String, Arena> SCHEMATICS = new HashMap<>();
	public static Map<Player, Location> POSITION1 = new HashMap<>();
	public static Map<Player, Location> POSITION2 = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ARMOURS = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ITEMS = new HashMap<>();
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

	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
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

	public static PluginCommand command(String commandName,
			CommandExecutor command, String permission) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(
				API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}

	public static PluginCommand command(String commandName,
			CommandExecutor command, String permission,
			String permissionMessage) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(permissionMessage);
		return cmd;
	}

	public static void runCommand(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}

	public static boolean isIpProxy(String ip) {
		try {
			String url = "http://botscout.com/test/?ip=" + ip;
			Scanner scanner = new Scanner(new URL(url).openStream());
			if (scanner.findInLine("Y") != null) {
				scanner.close();
				return true;
			}
			scanner.close();

		} catch (Exception e) {
		}
		return false;
	}

	public static BukkitTask delay(Plugin plugin, long ticks, Runnable run) {
		return Bukkit.getScheduler().runTaskLater(plugin, run, ticks);
	}
	public static BukkitTask delays(Plugin plugin, long ticks, Runnable run) {
		return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, run,
				ticks);
	}

	public static void event(Listener event) {

		event(event, getAPI());
	}

	public static void event(Listener event, Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(event, plugin);
	}

	public static boolean existsPlayer(CommandSender sender, String player) {

		Player p = getPlayer(player);
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
			sender.sendMessage(
					API.PLUGIN_NOT_EXITS.replace("$plugin", plugin));
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

	public static boolean getChance(double chance) {

		return RANDOM.nextDouble() <= chance;
	}

	public static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

	public static long getNow() {
		return System.currentTimeMillis();
	}

	public static Player getPlayer(String name) {

		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayerExact(name);
		
		return player;
	}

	public static List<Player> getPlayers() {
		return GameAPI.getPlayers();
	}

	public static double getRandomDouble(double minValue, double maxValue) {

		double min = Math.min(minValue, maxValue),
				max = Math.max(minValue, maxValue);
		return min + (max - min) * RANDOM.nextDouble();
	}

	public static int getRandomInt(int minValue, int maxValue) {

		int min = Math.min(minValue, maxValue),
				max = Math.max(minValue, maxValue);
		return min + RANDOM.nextInt(max - min + 1);
	}

	public static Object getSerializable(File file) {
		if (!file.exists()) {
			return null;
		}
		try {

			FileInputStream getStream = new FileInputStream(file);
			ObjectInputStream get = new ObjectInputStream(getStream);
			Object object = get.readObject();
			get.close();
			return object;
		} catch (Exception ex) {
		}

		return null;
	}

	public static Location getSpawn() {
		return Bukkit.getWorlds().get(0).getSpawnLocation();
	}

	public static Tag getTag(Player player) {
		if (!Tag.getTags().containsKey(player)) {
			Tag.getTags().put(player, new Tag("", ""));
		}
		return Tag.getTags().get(player);
	}
	/**
	 * Retorna se (now < (seconds + before));
	 * 
	 * @param before
	 *            (Antes)
	 * @param seconds
	 *            (Cooldown)
	 * @return
	 */
	public static boolean inCooldown(long before, long seconds) {

		long now = System.currentTimeMillis();
		long cooldown = seconds * 1000;
		return now < (cooldown + before);

	}
	public static long getCooldown(long before, long seconds) {

		long now = System.currentTimeMillis();
		long cooldown = seconds * 1000;

		// +5 - 19 + 15

		return +cooldown - now + before;

	}

	public static File getWorldsFolder() {
		return Bukkit.getWorldContainer();
	}

	public static boolean hasAPI() {
		return hasPlugin("EduardAPI");
	}

	public static boolean hasPerm(CommandSender sender, String permission) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(
					API.NO_PERMISSION.replace("$permission", permission));
			return false;
		}
		return true;

	}

	public static boolean hasPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null;
	}

	/////////////////////////////

	public static boolean isMultBy(int number1, int numer2) {

		return number1 % numer2 == 0;
	}

	public static World newEmptyWorld(String worldName) {
		World world = Bukkit.createWorld(new WorldCreator(worldName)
				.generateStructures(false).generator(new EmptyWorld()));
		world.getBlockAt(100, 100, 100).setType(Material.GLASS);
		world.setSpawnLocation(100, 101, 100);
		return world;
	}

	public static Inventory newInventory(String name, int size) {

		return Bukkit.createInventory(null, size, name);
	}
	public static void deleteFolder(File file) {
		if (file.exists()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFolder(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
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
			ConfigSection.console("§bCommandAPI §fremovendo aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		} else {
			ConfigSection.console("§bCommandAPI §fnao foi encontrado a aliase §a" + aliase
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
			ConfigSection.console("§bCommandAPI §fremovendo o comando §a" + cmdName
					+ "§f do Plugin §b" + pluginName);
		} else {
			ConfigSection.console(
					"§bCommandAPI §fnao foi encontrado a commando §a" + name);
		}

	}

	public static void resetTag(Player player) {
		setTag(player, "");
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

	public static void setSerializable(Object object, File file) {
		try {
			FileOutputStream saveStream = new FileOutputStream(file);
			ObjectOutputStream save = new ObjectOutputStream(saveStream);
			if (object instanceof Serializable) {
				save.writeObject(object);
			}
			save.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void setTag(Player player, String prefix) {
		setTag(player, prefix, "");
	}

	public static void setTag(Player player, String prefix, String suffix) {
		setTag(player, new Tag(prefix, suffix));
	}

	public static void setTag(Player player, Tag tag) {
		Tag.getTags().put(player, tag);

	}

	public static BukkitTask timer(Plugin plugin, long ticks, Runnable run) {
		if (run instanceof BukkitRunnable) {
			BukkitRunnable bukkitRunnable = (BukkitRunnable) run;
			return bukkitRunnable.runTaskTimer(plugin, ticks, ticks);
		}
		return Bukkit.getScheduler().runTaskTimer(plugin, run, ticks, ticks);
	}
	public static BukkitTask timers(Plugin plugin, long ticks, Runnable run) {
		if (run instanceof BukkitRunnable) {
			BukkitRunnable bukkitRunnable = (BukkitRunnable) run;
			return bukkitRunnable.runTaskTimerAsynchronously(plugin, ticks,
					ticks);
		}
		return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, run,
				ticks, ticks);
	}

	public static void addPermission(String permission) {
		Bukkit.getPluginManager().addPermission(new Permission(permission));
	}

	public static void registerListener(Listener listener) {
		event(listener);
	}
	public static void addPermission(Player p, String permission) {
		p.addAttachment(API.PLUGIN, permission, true);
	}
	public static void removePermission(Player p, String permission) {
		p.addAttachment(API.PLUGIN, permission, false);
	}

	public static Scoreboard newScoreboard(Player player, String title,
			String... lines) {
		return applyScoreboard(player, title, lines);
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
	public static int getPosition(int line, int column) {
		int value = (line - 1) * 9;
		return value + column - 1;
	}

	public static boolean newExplosion(Location location, float power,
			boolean breakBlocks, boolean makeFire) {
		return location.getWorld().createExplosion(location.getX(),
				location.getY(), location.getZ(), power, breakBlocks, makeFire);
	}
	public static Firework newFirework(Location location, int high, Color color,
			Color fade, boolean trail, boolean flicker) {
		Firework firework = (Firework) location.getWorld().spawn(location,
				Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(high);
		meta.addEffect(FireworkEffect.builder().trail(trail).flicker(flicker)
				.withColor(color).withFade(fade).build());
		firework.setFireworkMeta(meta);
		return firework;
	}

}
