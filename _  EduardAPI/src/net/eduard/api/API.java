package net.eduard.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

import net.eduard.api.config.Configs;
import net.eduard.api.game.Sounds;
import net.eduard.api.game.Tag;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.ItemAPI;
import net.eduard.api.manager.Manager;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.manager.VaultAPI;
import net.eduard.api.minigame.Arena;
import net.eduard.api.util.Cs;
import net.eduard.api.util.EmptyWorld;
import net.eduard.api.util.Replacer;

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
	public static String ONLY_PLAYER = "§cApenas jogadores pode fazer este comando!";
	public static String WORLD_NOT_EXISTS = "§cEste mundo $world não existe!";
	public static String PLAYER_NOT_EXISTS = "§cEste jogador $player não existe!";
	public static String NO_PERMISSION = "§cVoce não tem permissão para usar este comando!";
	public static String ON_JOIN = "§6O jogador $player entrou no Jogo!";
	public static String ON_QUIT = "§6O jogador $player saiu no Jogo!";
	public static String USAGE = "§FDigite: §c";
	public static String SERVER_TAG = "§b§lEduardAPI ";
	public static Sounds SOUND_TELEPORT = Sounds
			.create(Sound.ENDERMAN_TELEPORT);
	public static boolean NO_DEATH_MESSAGE = true;
	public static boolean NO_JOIN_MESSAGE = true;
	public static boolean NO_QUIT_MESSAGE = true;
	public static double MIN_WALK_SPEED = 0.2;
	public static double MIN_FLY_SPEED = 0.1;
	public static boolean AUTO_RESPAWN = true;
	public static Random RANDOM = new Random();
	public static Map<Player, Location> POSITION1 = new HashMap<>();
	public static Map<Player, Arena> MAPS = new HashMap<>();
	public static Map<String, Arena> SCHEMATICS = new HashMap<>();
	public static Map<Player, Location> POSITION2 = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ARMOURS = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ITEMS = new HashMap<>();
	public static Map<String, Replacer> REPLACERS = new HashMap<>();
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
		addReplacer("$name", new Replacer() {

			public Object getText(Player p) {
				return p.getName();
			}
		});
		addReplacer("$group", new Replacer() {

			public Object getText(Player p) {
				return VaultAPI.getPermission().getPrimaryGroup(p);
			}
		});
		addReplacer("$players", new Replacer() {

			public Object getText(Player p) {
				return API.getPlayers().size();
			}
		});
		addReplacer("$world", new Replacer() {

			public Object getText(Player p) {
				return p.getWorld().getName();
			}
		});
		addReplacer("$display", new Replacer() {

			public Object getText(Player p) {
				return p.getDisplayName();
			}
		});
		addReplacer("$health", new Replacer() {

			public Object getText(Player p) {
				return p.getHealth();
			}
		});
		addReplacer("$max_health", new Replacer() {

			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		addReplacer("$max_health", new Replacer() {

			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		addReplacer("$kills", new Replacer() {

			public Object getText(Player p) {
				return p.getStatistic(Statistic.PLAYER_KILLS);
			}
		});
		addReplacer("$deaths", new Replacer() {

			public Object getText(Player p) {
				return p.getStatistic(Statistic.DEATHS);
			}
		});
		addReplacer("$kill_per_death", new Replacer() {

			public Object getText(Player p) {
				int kill = p.getStatistic(Statistic.PLAYER_KILLS);
				int death = p.getStatistic(Statistic.DEATHS);
				if (kill == 0)
					return 0;
				if (death == 0)
					return 0;
				return kill / death;
			}
		});
		addReplacer("$money", new Replacer() {

			@SuppressWarnings("deprecation")
			@Override
			public Object getText(Player p) {
				if (VaultAPI.hasVault() && VaultAPI.hasEconomy()) {
					try {
						return VaultAPI.getEconomy().getBalance(p);
					} catch (Exception e) {
						return VaultAPI.getEconomy().getBalance(p.getName());
					}

				}
				return 0;
			}
		});
		addReplacer("$x", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getX();
			}
		});
		addReplacer("$y", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getY();
			}
		});
		addReplacer("$z", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getZ();
			}
		});
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

	public static List<LivingEntity> getNearbyEntities(LivingEntity player,
			double x, double y, double z, EntityType... types) {
		List<LivingEntity> list = new ArrayList<>();
		for (Entity item : player.getNearbyEntities(x, y, z)) {
			if (item instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) item;
				if (types != null) {
					for (EntityType entitie : types) {
						if (livingEntity.getType().equals(entitie)) {
							if (!list.contains(livingEntity))
								list.add(livingEntity);
						}
					}
				} else
					list.add(livingEntity);
			}
		}
		return list;

	}

	public static List<LivingEntity> getNearbyEntities(LivingEntity entity,
			double radio, EntityType... entities) {

		return getNearbyEntities(entity, radio, radio, radio, entities);

	}

	public static long getNow() {
		return System.currentTimeMillis();
	}

	public static Player getPlayer(String name) {

		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayerExact(name);
		return player;
	}

	public static List<Player> getPlayerAtRange(Location location,
			double range) {

		List<Player> players = new ArrayList<>();
		for (Player p : location.getWorld().getPlayers()) {
			if (p.getLocation().distance(location) <= range) {
				players.add(p);
			}
		}
		return players;
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
 * @param before (Antes)
 * @param seconds (Cooldown)
 * @return
 */
	public static boolean inCooldown(long before, long seconds) {
	

		long now = System.currentTimeMillis();
		long cooldown = seconds * 1000;
		return now < (cooldown + before);

	}
	public static void getCooldown(long before, long seconds) {
		

//		long now = System.currentTimeMillis();
//		long cooldown = seconds * 1000;
//		return now < (cooldown + before);

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
	public static void addReplacer(String key, Replacer value) {
		REPLACERS.put(key, value);
	}
	public static Replacer getReplacer(String key) {
		return REPLACERS.get(key);
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
			Cs.console("§bCommandAPI §fremovendo aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		} else {
			Cs.console("§bCommandAPI §fnao foi encontrado a aliase §a" + aliase
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
			Cs.console("§bCommandAPI §fremovendo o comando §a" + cmdName
					+ "§f do Plugin §b" + pluginName);
		} else {
			Cs.console(
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
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("board", "score");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int id = 15;
		for (String line : lines) {
			String empty = ChatColor.values()[id - 1].toString();
			obj.getScore(new OfflinePlayer() {
				@Override
				public Map<String, Object> serialize() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void setOp(boolean arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean isOp() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setWhitelisted(boolean value) {
					// TODO Auto-generated method stub

				}

				@Override
				public void setBanned(boolean banned) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean isWhitelisted() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isOnline() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isBanned() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean hasPlayedBefore() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public UUID getUniqueId() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Player getPlayer() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return line.isEmpty() ? empty : line;
				}

				@Override
				public long getLastPlayed() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public long getFirstPlayed() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public Location getBedSpawnLocation() {
					// TODO Auto-generated method stub
					return null;
				}
			}).setScore(id);;
			id--;
			if (id == 0) {
				break;
			}
		}

		player.setScoreboard(board);
		return board;
	}
	public static int getPosition(int line, int z) {
		int value = (line - 1) * 9;
		return value + z - 1;
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
	  public static Point getCompassPointForDirection(double inDegrees)
	  {
	    double degrees = (inDegrees - 180.0D) % 360.0D;
	    if (degrees < 0.0D) {
	      degrees += 360.0D;
	    }

	    if ((0.0D <= degrees) && (degrees < 22.5D))
	      return Point.N;
	    if ((22.5D <= degrees) && (degrees < 67.5D))
	      return Point.NE;
	    if ((67.5D <= degrees) && (degrees < 112.5D))
	      return Point.E;
	    if ((112.5D <= degrees) && (degrees < 157.5D))
	      return Point.SE;
	    if ((157.5D <= degrees) && (degrees < 202.5D))
	      return Point.S;
	    if ((202.5D <= degrees) && (degrees < 247.5D))
	      return Point.SW;
	    if ((247.5D <= degrees) && (degrees < 292.5D))
	      return Point.W;
	    if ((292.5D <= degrees) && (degrees < 337.5D))
	      return Point.NW;
	    if ((337.5D <= degrees) && (degrees < 360.0D)) {
	      return Point.N;
	    }
	    return null;
	  }

	  public static ArrayList<String> getAsciiCompass(Point point, ChatColor colorActive, String colorDefault)
	  {
	    ArrayList<String> ret = new ArrayList<>();

	    String row = "";
	    row = row + Point.NW.toString(Point.NW == point, colorActive, colorDefault);
	    row = row + Point.N.toString(Point.N == point, colorActive, colorDefault);
	    row = row + Point.NE.toString(Point.NE == point, colorActive, colorDefault);
	    ret.add(row);

	    row = "";
	    row = row + Point.W.toString(Point.W == point, colorActive, colorDefault);
	    row = row + colorDefault + "+";
	    row = row + Point.E.toString(Point.E == point, colorActive, colorDefault);
	    ret.add(row);

	    row = "";
	    row = row + Point.SW.toString(Point.SW == point, colorActive, colorDefault);
	    row = row + Point.S.toString(Point.S == point, colorActive, colorDefault);
	    row = row + Point.SE.toString(Point.SE == point, colorActive, colorDefault);
	    ret.add(row);

	    return ret;
	  }

	  public static ArrayList<String> getAsciiCompass(double inDegrees, ChatColor colorActive, String colorDefault) {
	    return getAsciiCompass(getCompassPointForDirection(inDegrees), colorActive, colorDefault);
	  }

	  public static enum Point
	  {
	    N('N'), NE('/'), E('O'), SE('\\'), S('S'), SW('/'), W('L'), NW('\\');

	    public final char asciiChar;

	    private Point(char asciiChar) {
	      this.asciiChar = asciiChar;
	    }

	    public String toString()
	    {
	      return String.valueOf(this.asciiChar);
	    }

	    public String toString(boolean isActive, ChatColor colorActive, String colorDefault) {
	      return (isActive ? colorActive : colorDefault) + String.valueOf(this.asciiChar);
	    }
	  }

}
