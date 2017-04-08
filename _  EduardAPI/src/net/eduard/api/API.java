package net.eduard.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import net.eduard.api.config.ConfigAPI;
import net.eduard.api.gui.Slot;
import net.eduard.api.manager.EduardAPI;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.manager.TimeAPI;
import net.eduard.api.manager.VaultAPI;
import net.eduard.api.player.LocationEffect;
import net.eduard.api.player.SoundEffect;
import net.eduard.api.player.Tag;
import net.eduard.api.util.EmptyWorldGenerator;
import net.eduard.api.util.Replacer;
import net.eduard.api.util.Space;

@SuppressWarnings("unchecked")
public class API {

	public static final SoundEffect ROSNAR = SoundEffect.create(Sound.CAT_PURR);
	public static final float TNT = 4F;
	public static final float CREEPER = 3F;
	public static final float WALKING_VELOCITY = -0.08F;

	public static String ONLY_PLAYER = "§cApenas jogadores pode fazer este comando!";
	public static String WORLD_NOT_EXISTS = "§cEste mundo $world não existe!";
	public static String PLAYER_NOT_EXISTS = "§cEste jogador $player não existe!";
	public static String NO_PERMISSION = "§cVoce não tem permissão para usar este comando!";
	public static String ON_JOIN = "§6O jogador $player entrou no Jogo!";
	public static String ON_QUIT = "§6O jogador $player saiu no Jogo!";
	public static String USAGE = "§FDigite: §c";
	public static boolean NO_DEATH_MESSAGE = true;
	public static boolean NO_JOIN_MESSAGE = false;
	public static boolean NO_QUIT_MESSAGE = false;
	public static EduardAPI EVENTS = new EduardAPI();
	public static double MIN_WALK_SPEED = 0.2;
	public static double MIN_FLY_SPEED = 0.1;
	public static boolean AUTO_RESPAWN = true;
	public static Random RANDOM = new Random();
	public static int DAY_IN_HOUR = 24;
	public static int DAY_IN_MINUTES = DAY_IN_HOUR * 60;
	public static int DAY_IN_SECONDS = DAY_IN_MINUTES * 60;
	public static long DAY_IN_TICKS = DAY_IN_SECONDS * 20;
	public static long DAY_IN_LONG = DAY_IN_TICKS * 50;
	public static Map<Player, Location> POSITION1 = new HashMap<>();
	public static Map<Player, Space> MAPS = new HashMap<>();
	public static Map<String, Space> SCHEMATICS = new HashMap<>();
	public static Map<Player, Location> POSITION2 = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ARMOURS = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ITEMS = new HashMap<>();
	public static Map<String, Replacer> REPLACERS = new HashMap<>();
	public static String SERVER_TAG = "";
	public static SoundEffect SOUND_TELEPORT = SoundEffect
			.create(Sound.ENDERMAN_TELEPORT);
	public static TimeAPI TIME;
	public static JavaPlugin PLUGIN;

	public static void chat(CommandSender sender, String message) {
		sender.sendMessage(SERVER_TAG + message);
	}

	private static Map<String, Command> commands = new HashMap<>();

	static {
		PLUGIN = JavaPlugin.getProvidingPlugin(API.class);
		TIME = new TimeAPI(PLUGIN);
		try {
			Object map = RexAPI.getValue(Bukkit.getServer().getPluginManager(),
					"commandMap");

			commands = (Map<String, Command>) RexAPI.getValue(map,
					"knownCommands");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		REPLACERS.put("$name", new Replacer() {

			public Object getText(Player p) {
				return p.getName();
			}
		});
		REPLACERS.put("$players", new Replacer() {

			public Object getText(Player p) {
				return API.getPlayers().size();
			}
		});
		REPLACERS.put("$world", new Replacer() {

			public Object getText(Player p) {
				return p.getWorld().getName();
			}
		});
		REPLACERS.put("$display", new Replacer() {

			public Object getText(Player p) {
				return p.getDisplayName();
			}
		});
		REPLACERS.put("$health", new Replacer() {

			public Object getText(Player p) {
				return p.getHealth();
			}
		});
		REPLACERS.put("$max_health", new Replacer() {

			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		REPLACERS.put("$max_health", new Replacer() {

			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		REPLACERS.put("$kills", new Replacer() {

			public Object getText(Player p) {
				return p.getStatistic(Statistic.PLAYER_KILLS);
			}
		});
		REPLACERS.put("$deaths", new Replacer() {

			public Object getText(Player p) {
				return p.getStatistic(Statistic.DEATHS);
			}
		});
		REPLACERS.put("$kill_per_death", new Replacer() {

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
		REPLACERS.put("$money", new Replacer() {

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
		REPLACERS.put("$x", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getX();
			}
		});
		REPLACERS.put("$y", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getY();
			}
		});
		REPLACERS.put("$z", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getZ();
			}
		});
		EVENTS.register(PLUGIN);
	}

	public static ItemStack add(ItemStack item, Enchantment type, int level) {
		item.addUnsafeEnchantment(type, level);
		return item;
	}
	public static ItemStack addEnchant(ItemStack item, Enchantment type,
			int level) {
		return add(item, type, level);
	}

	public static void addHotBar(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		if (item == null)
			return;
		if (item.getType() == Material.AIR)
			return;
		int i;
		while ((i = inv.firstEmpty()) < 9) {
			inv.setItem(i, item);
		}
	}
	public static void setDirection(Entity entity, Entity target) {
		entity.teleport(entity.getLocation()
				.setDirection(target.getLocation().getDirection()));
	}
	public static void all(Object... objects) {

		broadcast(objects);
		console(objects);
	}
	public static void broadcast(Object... objects) {

		for (Player p : getPlayers()) {
			chat(p, getText(objects));
		}
	}
	public static void broadcastMessage(Object... objects) {

		for (Player p : getPlayers()) {
			p.sendMessage(getText(objects));
		}
		Bukkit.getConsoleSender().sendMessage(getText(objects));
	}

	public static void broadcast(String message, String permision) {
		for (Player p : getPlayers()) {
			if (p.hasPermission(permision)) {
				chat(p, message);
			}
		}
	}
	public static void loadMaps() {
		ConfigAPI cf = new ConfigAPI("Maps/");
		try {
			cf.getFile().mkdirs();
			for (File f : cf.getFile().listFiles()) {
				SCHEMATICS.put(f.getName().replace(".map", ""), Space.load(f));
			}
		} catch (Exception e) {
		}
	}
	public static void saveMaps() {
		try {
			for (Entry<String, Space> map : SCHEMATICS.entrySet()) {
				map.getValue().save(new File(PLUGIN.getDataFolder(),
						"Maps/" + map.getKey() + ".map"));
			}
		} catch (Exception e) {
		}
	}

	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
	}
	public static void changeTabName(Player player, String displayName) {
		player.setPlayerListName(toText(displayName));
	}

	public static void clearArmours(LivingEntity entity) {
		entity.getEquipment().setArmorContents(null);
	}

	public static void clearHotBar(Player p) {
		for (int i = 0; i < 9; i++) {
			p.getInventory().setItem(i, null);
		}
	}

	public static void clearInventory(Player player) {
		clearItens(player);
		clearArmours(player);
	}

	public static void clearItens(LivingEntity entity) {
		entity.getEquipment().clear();

	}
	public static void give(Collection<ItemStack> items, Inventory inv) {
		for (ItemStack item : items) {
			inv.addItem(item);
		}
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

	public static void console(Object... objects) {

		Bukkit.getConsoleSender().sendMessage(SERVER_TAG + getText(objects));
	}
	public static void consoleMessage(Object... objects) {
		Bukkit.getConsoleSender().sendMessage(getText(objects));
	}

	public static void command(String command) {
		// Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/"+command);
		Bukkit.getConsoleSender().sendMessage("/" + command);
	}

	public static boolean contains(String message, String text) {
		return message.toLowerCase().contains(text.toLowerCase());
	}

	public static void copy(World world, String name) {

	}

	public static void copyWorld(File source, File target) {
		try {
			List<String> ignore = new ArrayList<String>(
					Arrays.asList("uid.dat", "session.dat"));
			if (!ignore.contains(source.getName())) {
				if (source.isDirectory()) {
					if (!target.exists())
						target.mkdirs();
					String files[] = source.list();
					for (String file : files) {
						File srcFile = new File(source, file);
						File destFile = new File(target, file);
						copyWorld(srcFile, destFile);
					}
				} else {
					InputStream in = new FileInputStream(source);
					OutputStream out = new FileOutputStream(target);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0)
						out.write(buffer, 0, length);
					in.close();
					out.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static BukkitTask delay(Plugin plugin, long ticks, Runnable run) {
		return Bukkit.getScheduler().runTaskLater(plugin, run, ticks);
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

	public static void deleteWorld(String name) {
		World world = Bukkit.getWorld(name);
		if (world != null) {
			for (Player p : world.getPlayers()) {
				p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()
						.setDirection(p.getLocation().getDirection()));
			}
			Bukkit.unloadWorld(world, false);
			deleteFolder(world.getWorldFolder());
		}
	}

	public static double distanceX(Location loc1, Location loc2) {
		return loc1.getX() - loc2.getX();
	}

	public static double distanceZ(Location loc1, Location loc2) {
		return loc1.getZ() - loc2.getZ();
	}

	public static void drop(Entity entity, ItemStack item) {
		drop(entity.getLocation(), item);
	}

	public static void drop(Location location, ItemStack item) {
		location.getWorld().dropItemNaturally(location, item);
	}

	public static boolean equals(Location location1, Location location2) {

		return getBlockLocation1(location1)
				.equals(getBlockLocation1(location2));
	}

	public static boolean equals2(Location location1, Location location2) {
		return location1.getBlock().getLocation()
				.equals(location2.getBlock().getLocation());
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

	public static void fill(Inventory inv, ItemStack item) {
		int id;
		while ((id = inv.firstEmpty()) != -1) {
			inv.setItem(id, item);
		}
	}

	public static JavaPlugin getAPI() {
		if (hasAPI()) {
			PLUGIN = (JavaPlugin) Bukkit.getPluginManager()
					.getPlugin("EduardAPI");
		}
		return PLUGIN;
	}

	public static Location getBlockLocation1(Location location) {

		return new Location(location.getWorld(), (int) location.getX(),
				(int) location.getY(), (int) location.getZ());
	}

	public static Location getBlockLocation2(Location location) {

		return location.getBlock().getLocation();
	}

	public static List<Location> getBox(Location playerLocation, double higher,
			double lower, double size, LocationEffect effect) {
		Location high = getHighLocation(playerLocation.clone(), higher, size);
		Location low = getLowLocation(playerLocation.clone(), lower, size);
		return getLocations(low, high, effect);
	}
	public static List<Location> getBox(Location playerLocation, double higher,
			double lower, double size) {
		return getBox(playerLocation, higher, lower, size,
				new LocationEffect() {

					@Override
					public boolean effect(Location location) {
						return true;
					}
				});
	}
	public static List<Location> getBox(Location playerLocation, double xHigh,
			double xLow, double zHigh, double zLow, double yLow, double yHigh) {
		Location low = playerLocation.clone().subtract(xLow, yLow, zLow);
		Location high = playerLocation.clone().add(xHigh, yHigh, zHigh);
		return getLocations(low, high);
	}

	public static boolean getChance(double chance) {

		return RANDOM.nextDouble() <= chance;
	}

	public static Map<String, Command> getCommands() {
		return commands;
	}

	public static double getDamage(ItemStack item) {
		if (item == null)
			return 0;
		double damage = 0;
		String name = item.getType().name();
		for (int id = 0; id <= 4; id++) {
			String value = "";
			if (id == 0) {
				value = "DIAMOND_";
				damage += 3;
			}
			if (id == 1) {
				value = "IRON_";
				damage += 2;
			}
			if (id == 2) {
				value = "GOLD_";
			}
			if (id == 3) {
				value = "STONE_";
				damage++;
			}
			if (id == 4) {
				value = "WOOD_";
			}

			for (int x = 0; x <= 3; x++) {
				double newDamage = damage;
				if (x == 0) {
					value = "SWORD";
					newDamage += 4;
				}
				if (x == 1) {
					value = "AXE";
					newDamage += 3;
				}
				if (x == 2) {
					value = "PICKAXE";
					newDamage += 2;
				}
				if (x == 3) {
					value = "SPADE";
					newDamage++;
				}

				if (name.equals(value)) {
					return newDamage;
				}
			}
			damage = 0;
		}
		return damage;
	}

	public static Vector getDiretion(Location location, Location target) {
		double distance = target.distance(location);
		double x = ((target.getX() - location.getX()) / distance);
		double y = ((target.getY() - location.getY()) / distance);
		double z = ((target.getZ() - location.getZ()) / distance);
		return new Vector(x, y, z);
	}

	public static Material getHandType(LivingEntity entity) {
		EntityEquipment inv = entity.getEquipment();
		if (inv == null) {
			return Material.AIR;
		}
		ItemStack item = inv.getItemInHand();
		if (item == null) {
			return Material.AIR;
		}

		return item.getType();
	}

	public static ItemStack getHead(String name) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(name);
		item.setItemMeta(meta);
		return item;
	}

	public static Location getHighLocation(Location loc, double high,
			double size) {

		loc.add(size, high, size);
		return loc;
	}

	public static Location getHighLocation(Location loc1, Location loc2) {

		double x = Math.max(loc1.getX(), loc2.getX());
		double y = Math.max(loc1.getY(), loc2.getY());
		double z = Math.max(loc1.getZ(), loc2.getZ());
		return new Location(loc1.getWorld(), x, y, z);
	}

	@SuppressWarnings("deprecation")
	public static void getItems(Player player) {
		if (INV_ITEMS.containsKey(player)) {
			player.getInventory().setContents(INV_ITEMS.get(player));
			player.updateInventory();
		}
		getArmours(player);

	}
	@SuppressWarnings("deprecation")
	public static void getArmours(Player player) {
		if (INV_ARMOURS.containsKey(player)) {
			player.getInventory().setArmorContents(INV_ARMOURS.get(player));
			player.updateInventory();
		}
	}

	public static int getItemsAmount(Inventory inventory) {
		int amount = 0;
		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				amount++;
			}
		}

		return amount;
	}

	public static List<Location> getLocations(Location location1,
			Location location2) {
		return getLocations(location1, location2, new LocationEffect() {

			public boolean effect(Location location) {
				return true;
			}
		});
	}

	public static List<Location> getLocations(Location location1,
			Location location2, LocationEffect effect) {

		Location min = getLowLocation(location1, location2);
		Location max = getHighLocation(location1, location2);
		List<Location> locations = new ArrayList<>();
		for (double x = min.getX(); x <= max.getX(); x++) {
			for (double y = min.getY(); y <= max.getY(); y++) {
				for (double z = min.getZ(); z <= max.getZ(); z++) {
					Location loc = new Location(min.getWorld(), x, y, z);
					try {
						boolean r = effect.effect(loc);
						if (r) {
							try {
								locations.add(loc);
							} catch (Exception ex) {
							}
						}
					} catch (Exception ex) {
					}

				}
			}
		}
		return locations;

	}

	public static List<String> getLore(ItemStack item) {
		if (item != null) {
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				return item.getItemMeta().getLore();
			}
		}
		return new ArrayList<String>();
	}

	public static Location getLowLocation(Location loc, double low,
			double size) {

		loc.subtract(size, low, size);
		return loc;
	}

	public static Location getLowLocation(Location location1,
			Location location2) {
		double x = Math.min(location1.getX(), location2.getX());
		double y = Math.min(location1.getY(), location2.getY());
		double z = Math.min(location1.getZ(), location2.getZ());
		return new Location(location1.getWorld(), x, y, z);
	}

	public static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

	public static String getName(ItemStack item) {

		return item.hasItemMeta()
				? item.getItemMeta().hasDisplayName()
						? item.getItemMeta().getDisplayName()
						: ""
				: "";
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

	public static List<Player> getOnlinePlayers() {
		return API.getPlayers();
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
		return RexAPI.getPlayers();
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

	public static ItemStack getRandomItem(ItemStack... items) {

		return items[RANDOM.nextInt(items.length - 1)];
	}

	public static ItemStack getRandomItem(List<ItemStack> items) {

		return items.get(RANDOM.nextInt(items.size() - 1));
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
	public static Location getTarget(LivingEntity entity, int distance) {
		@SuppressWarnings("deprecation")
		Block block = entity.getTargetBlock(null, distance);
		return block.getLocation();
	}

	public static String getText(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(object);

		}
		return builder.toString();
	}

	public static String getText(String text, Player player) {
		for (Entry<String, Replacer> value : REPLACERS.entrySet()) {
			try {
				text = text.replace(value.getKey(),
						value.getValue().getText(player).toString());
			} catch (Exception e) {
			}

		}
		return text;
	}

	public static String getTime(int time) {

		return getTime(time, " segundo(s)", " minuto(s) ");

	}

	public static String getTime(int time, String second, String minute) {
		if (time >= 60) {
			int min = time / 60;
			int sec = time % 60;
			if (sec == 0) {
				return min + minute;
			} else {
				return min + minute + sec + second;
			}

		}
		return time + second;
	}

	public static String getTimeMid(int time) {

		return getTime(time, " seg", " min ");

	}

	public static String getTimeSmall(int time) {

		return getTime(time, "s", "m");

	}

	public static int getTotalAmount(Inventory inventory) {
		int amount = 0;
		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				amount += item.getAmount();
			}
		}
		return amount;
	}

	public static Vector getVelocity(Location entity, Location target,
			double staticX, double staticY, double staticZ, double addX,
			double addY, double addZ) {
		double distance = target.distance(entity);
		double x = (staticX + (addX * distance))
				* ((target.getX() - entity.getX()) / distance);
		double y = (staticY + (addY * distance))
				* ((target.getY() - entity.getY()) / distance);
		double z = (staticZ + (addZ * distance))
				* ((target.getZ() - entity.getZ()) / distance);
		return new Vector(x, y, z);

	}

	public static World getWorld(String name) {
		return Bukkit.getWorld(name);

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

	public static void hide(Player player) {
		for (Player target : API.getPlayers()) {
			if (target != player) {
				target.hidePlayer(player);
			}
		}
	}
	public static boolean isEmpty(Inventory inventory) {

		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				return false;
			}

		}
		return true;
	}

	public static boolean isFalling(Entity entity) {
		return entity.getVelocity().getY() < WALKING_VELOCITY;
	}

	/////////////////////////////

	public static boolean isFlying(Entity entity) {
		return entity.getLocation().getBlock().getRelative(BlockFace.DOWN, 2)
				.getType() == Material.AIR;
	}
	public static boolean isFull(Inventory inventory) {
		return inventory.firstEmpty() == -1;
	}
	public static boolean isInvulnerable(Player player) {
		return player.getNoDamageTicks() > 1;
	}
	public static boolean isMultBy(int number1, int numer2) {

		return number1 % numer2 == 0;
	}
	public static boolean isOnGround(Entity entity) {
		return entity.getLocation().getBlock().getRelative(BlockFace.DOWN)
				.getType() != Material.AIR;
	}

	public static boolean isUsing(LivingEntity entity, Material material) {
		return (getHandType(entity) == material);
	}
	public static boolean isUsing(LivingEntity entity, String material) {
		return (contains(getHandType(entity).name(), (material)));
	}

	public static void loadWorld(String name) {
		new WorldCreator(name).createWorld();
	}

	public static void makeInvunerable(Player player) {
		player.setNoDamageTicks(DAY_IN_SECONDS * 20);

	}

	public static void makeInvunerable(Player player, int seconds) {
		player.setNoDamageTicks(seconds * 20);

	}

	public static void makeVulnerable(Player player) {

		player.setNoDamageTicks(0);
	}

	public static void moveTo(Entity entity, Location target, double gravity) {
		Location location = entity.getLocation().clone();
		double distance = target.distance(location);
		double x = -(gravity - ((target.getX() - location.getX()) / distance));
		double y = -(gravity - ((target.getY() - location.getY()) / distance));
		double z = -(gravity - ((target.getZ() - location.getZ()) / distance));
		Vector vector = new Vector(x, y, z);
		entity.setVelocity(vector);
	}

	public static void moveTo(Entity entity, Location target, double staticX,
			double staticY, double staticZ, double addX, double addY,
			double addZ) {
		Location location = entity.getLocation();
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			location = livingEntity.getEyeLocation();
		}
		entity.setVelocity(getVelocity(location, target, staticX, staticY,
				staticZ, addX, addY, addZ));
	}

	public static World newEmptyWorld(String worldName) {
		World world = Bukkit.createWorld(
				new WorldCreator(worldName).generateStructures(false)
						.generator(new EmptyWorldGenerator()));
		world.getBlockAt(100, 100, 100).setType(Material.GLASS);
		world.setSpawnLocation(100, 101, 100);
		return world;
	}
	public static Inventory newInventory(String name, int size) {

		return Bukkit.createInventory(null, size, name);
	}

	public static ItemStack newItem(Material material, String name) {
		ItemStack item = new ItemStack(material);
		setName(item, name);
		return item;
	}

	public static ItemStack newItem(Material material, String name,
			int amount) {
		return newItem(material, name, amount, 0);
	}

	public static ItemStack newItem(Material material, String name, int amount,
			int data, String... lore) {

		ItemStack item = newItem(material, name);
		setLore(item, lore);
		item.setAmount(amount);
		item.setDurability((short) data);
		return item;
	}

	public static ItemStack newItem(String name, Material material) {
		ItemStack item = new ItemStack(material);
		setName(item, name);
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

	public static void refreshAll(Player player) {
		clearInventory(player);
		removeEffects(player);
		refreshLife(player);
		refreshFood(player);
		makeVulnerable(player);
		resetLevel(player);
	}

	public static void refreshFood(Player player) {
		player.setFoodLevel(20);
		player.setSaturation(20);
		player.setExhaustion(0);
	}

	public static void refreshLife(Player p) {
		p.setHealth(p.getMaxHealth());
	}

	public static void removeAliaseFromCommand(PluginCommand cmd,
			String aliase) {
		String cmdName = cmd.getName().toLowerCase();
		if (getCommands().containsKey(aliase)) {
			getCommands().remove(aliase);
			API.console("§bCommandAPI §fremovendo aliase §a" + aliase
					+ "§f do comando §b" + cmdName);
		} else {
			API.console("§bCommandAPI §fnao foi encontrado a aliase §a" + aliase
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
			API.console("§bCommandAPI §fremovendo o comando §a" + cmdName
					+ "§f do Plugin §b" + pluginName);
		} else {
			API.console(
					"§bCommandAPI §fnao foi encontrado a commando §a" + name);
		}

	}

	public static void removeEffects(Player player) {
		player.setFireTicks(0);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	public static void removeTag(Player player) {
		setTag(player, "");
	}

	public static void resetLevel(Player player) {
		player.setLevel(0);
		player.setExp(0);
		player.setTotalExperience(0);
	}

	public static ItemStack resetName(ItemStack item) {
		setName(item, "");
		return item;
	}

	public static void resetScoreboard(Player player) {
		player.setScoreboard(getMainScoreboard());
	}
	// Parei aqui

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

	public static void saveItems(Player player) {
		saveArmours(player);
		INV_ITEMS.put(player, player.getInventory().getContents());
	}
	public static void saveArmours(Player player) {
		INV_ARMOURS.put(player, player.getInventory().getArmorContents());
	}

	public static ItemStack setColor(ItemStack item, Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}

	public static void setDirection(Entity entity, Location target) {
		Location location = entity.getLocation().clone();
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			location = livingEntity.getEyeLocation().clone();

		}
		entity.teleport(entity.getLocation()
				.setDirection(getDiretion(location, target)));

	}

	public static void setEquip(LivingEntity entity, Color color, String name) {
		EntityEquipment inv = entity.getEquipment();
		inv.setBoots(setName(
				setColor(new ItemStack(Material.LEATHER_BOOTS), color), name));
		inv.setHelmet(setName(
				setColor(new ItemStack(Material.LEATHER_HELMET), color), name));
		inv.setChestplate(setName(
				setColor(new ItemStack(Material.LEATHER_CHESTPLATE), color),
				name));
		inv.setLeggings(setName(
				setColor(new ItemStack(Material.LEATHER_LEGGINGS), color),
				name));
	}

	public static void setHotBar(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		for (int i = 0; i < 8; i++) {
			inv.setItem(i, item);
		}
	}

	public static ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack setLore(ItemStack item, String... lore) {

		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static void setSeralizable(Object object, File file) {
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

	public static void setSlot(Inventory inventory, Slot slot) {
		inventory.setItem(slot.getSlot(), slot.getItem());
	}

	public static void setSpawn(Entity entity) {

		entity.getWorld().setSpawnLocation((int) entity.getLocation().getX(),
				(int) entity.getLocation().getY(),
				(int) entity.getLocation().getZ());
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

	public static void show(Player player) {
		for (Player target : getPlayers()) {
			if (target != player) {
				target.showPlayer(player);
			}
		}
	}

	public static boolean startWith(String message, String text) {
		return message.toLowerCase().startsWith(text.toLowerCase());
	}

	public static LightningStrike strike(LivingEntity living, int maxDistance) {
		return strike(getTarget(living, maxDistance));
	}

	public static LightningStrike strike(Location location) {
		return location.getWorld().strikeLightning(location);
	}

	public static void teleport(Entity entity, Location target) {
		entity.teleport(
				target.setDirection(entity.getLocation().getDirection()));
	}

	public static void teleport(LivingEntity entity, int range) {
		teleport(entity, getTarget(entity, range));
	}

	public static void teleportToSpawn(Entity entity) {

		entity.teleport(entity.getWorld().getSpawnLocation()
				.setDirection(entity.getLocation().getDirection()));
	}

	public static BukkitTask timer(Plugin plugin, long ticks, Runnable run) {
		if (run instanceof BukkitRunnable) {
			BukkitRunnable bukkitRunnable = (BukkitRunnable) run;
			return bukkitRunnable.runTaskTimer(plugin, ticks, ticks);
		}
		return Bukkit.getScheduler().runTaskTimer(plugin, run, ticks, ticks);
	}

	public static Boolean toBoolean(Object obj) {

		if (obj == null) {
			return false;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		try {
			return Boolean.valueOf(obj.toString());
		} catch (Exception e) {
			return false;
		}
	}

	public static Byte toByte(Object object) {

		if (object == null) {
			return 0;
		}
		if (object instanceof Byte) {
			return (Byte) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.byteValue();
		}
		try {
			return Byte.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}

	}

	public static String toChatMessage(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String toConfigMessage(String text) {
		return text.replace("§", "&");
	}

	public static String toDecimal(Object number) {
		return toDecimal(number, 2);
	}

	public static String toDecimal(Object number, int max) {
		String text = "" + number;
		if (text.contains(".")) {
			String[] split = text.replace(".", ",").split(",");
			if (split[1].length() >= max) {
				return split[0] + "." + split[1].substring(0, max);
			}
			return text;
		}
		return text;
	}

	public static Double toDouble(Object object) {

		if (object == null) {
			return 0D;
		}
		if (object instanceof Double) {
			return (Double) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.doubleValue();
		}
		try {
			return Double.valueOf(object.toString());
		} catch (Exception e) {
			return 0D;
		}

	}

	public static Float toFloat(Object object) {

		if (object == null) {
			return 0F;
		}
		if (object instanceof Float) {
			return (Float) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.floatValue();
		}
		try {
			return Float.valueOf(object.toString());
		} catch (Exception e) {
			return 0F;
		}

	}

	public static Integer toInt(Object object) {

		if (object == null) {
			return 0;
		}
		if (object instanceof Integer) {
			return (Integer) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.intValue();
		}
		try {
			return Integer.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}

	}

	public static Integer toInteger(Object object) {
		return toInt(object);
	}

	public static Long toLong(Object object) {

		if (object == null) {
			return 0L;
		}
		if (object instanceof Long) {
			return (Long) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.longValue();
		}
		try {
			return Long.valueOf(object.toString());
		} catch (Exception e) {
			return 0L;
		}
	}

	public static Short toShort(Object object) {

		if (object == null) {
			return 0;
		}
		if (object instanceof Short) {
			return (Short) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.shortValue();
		}
		try {
			return Short.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}

	}

	public static String toString(Object object) {

		return object == null ? "" : object.toString();
	}

	public static String toText(Collection<String> message) {
		return message.toString().replace("[", "").replace("]", "");
	}

	public static String toText(int size, String text) {

		return text.length() > size ? text.substring(0, size) : text;
	}

	public static String toText(String... message) {

		return message.toString().replace("[", "").replace("]", "");
	}

	public static String toText(String text) {

		return toText(16, text);
	}

	public static String toTitle(String name) {
		char first = name.toUpperCase().charAt(0);
		name = name.toLowerCase();
		return first + name.substring(1, name.length());

	}

	public static String toTitle(String name, String replacer) {
		if (name.contains("_")) {
			String customName = "";
			for (String newName : name.split("_")) {
				if (newName != null) {
					customName += toTitle(newName) + replacer;
				}
			}
			return customName;
		}
		return toTitle(name);
	}
	public static void unloadWorld(String name) {
		try {
			unloadWorld(getWorld(name));
		} catch (Exception ex) {
		}
	}

	public static void unloadWorld(World world) {
		try {
			Bukkit.getServer().unloadWorld(world, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
