package net.eduard.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import net.eduard.api.dev.LaunchPad;
import net.eduard.api.dev.Sounds;
import net.eduard.api.dev.Tag;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Gui;
import net.eduard.api.gui.Slot;
import net.eduard.api.manager.DropManager;
import net.eduard.api.manager.GuiManager;
import net.eduard.api.manager.PadManager;
import net.eduard.api.manager.PlayerAPI;
import net.eduard.api.util.EmptyWorldGEN;
import net.eduard.api.util.GuiSize;
import net.eduard.api.util.LocationEffect;
import net.eduard.api.util.Replacer;

public abstract class API {
	public static String ONLY_PLAYER = "§cApenas jogadores pode fazer este comando!";
	public static String WORLD_NOT_EXISTS = "§cEste mundo $world não existe!";
	public static String PLAYER_NOT_EXISTS = "§cEste jogador $player não existe!";
	public static String NO_PERMISSION = "§cVoce não tem permissão para usar este comando!";
	public static double MIN_WALK_SPEED = 0.2;
	public static double MIN_FLY_SPEED = 0.1;
	public static Random RANDOM = new Random();
	public static List<Player> NO_FALL = new ArrayList<>();
	public static List<LaunchPad> PADS = new ArrayList<>();
	public static int DAY_IN_HOUR = 24;
	public static int DAY_IN_MINUTES = DAY_IN_HOUR * 60;
	public static int DAY_IN_SECONDS = DAY_IN_MINUTES * 60;
	public static long DAY_IN_TICKS = DAY_IN_SECONDS * 20;
	public static long DAY_IN_LONG = DAY_IN_TICKS * 50;
	public static List<Click> CLICKS = new ArrayList<>();
	public static List<Gui> GUIS = new ArrayList<>();
	public static Map<Player, Location> POSITION1 = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ARMOURS = new HashMap<>();
	public static Map<Player, ItemStack[]> INV_ITEMS = new HashMap<>();
	public static Map<Player, Location> POSITION2 = new HashMap<>();
	public static Map<String, Replacer> REPLACERS = new HashMap<>();
	public static Sounds SOUND_TELEPORT = Sounds.create(Sound.ENDERMAN_TELEPORT);

	public static World newWorld(String world, Environment environment, WorldType worldType) {
		return new WorldCreator(world).environment(environment).type(worldType).createWorld();
	}
	public static void changeTabName(Player player, String displayName) {
		player.setPlayerListName(toText(displayName));
	}
	public static void fill(Inventory inv, ItemStack item) {
		int id;
		while ((id = inv.firstEmpty()) != -1) {
			inv.setItem(id, item);
		}
	}

	public static World newEmptyWorld(String worldName) {
		World world = Bukkit
				.createWorld(new WorldCreator(worldName).generateStructures(false).generator(new EmptyWorldGEN()));
		world.getBlockAt(100, 100, 100).setType(Material.GLASS);
		world.setSpawnLocation(100, 101, 100);
		return world;
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
				p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation().setDirection(p.getLocation().getDirection()));
			}
			Bukkit.unloadWorld(world, false);
			deleteFolder(world.getWorldFolder());
		}
	}

	public static void copy(World world, String name) {

	}

	public static void unloadWorld(String name) {
		try {
			unloadWorld(getWorld(name));
		} catch (Exception ex) {
		}
	}

	public static World getWorld(String name) {
		return Bukkit.getWorld(name);

	}

	public static File getWorldsFolder() {
		return Bukkit.getWorldContainer();
	}

	public static void loadWorld(String name) {
		new WorldCreator(name).createWorld();
	}

	public static void unloadWorld(World world) {
		try {
			Bukkit.getServer().unloadWorld(world, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void copyWorld(File source, File target) {
		try {
			List<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
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

	public static boolean equals(Location location1, Location location2) {
		
		return getBlockLocation1(location1).equals(getBlockLocation1(location2));
	}
	public static boolean equals2(Location location1, Location location2) {
		return location1.getBlock().getLocation().equals(location2.getBlock().getLocation());
	}

	public static Location getLowLocation(Location location1, Location location2) {
		double x = Math.min(location1.getX(), location2.getX());
		double y = Math.min(location1.getY(), location2.getY());
		double z = Math.min(location1.getZ(), location2.getZ());
		return new Location(location1.getWorld(), x, y, z);
	}

	public static List<Location> getBox(Location playerLocation, double xHigh, double xLow, double zHigh, double zLow,
			double yLow, double yHigh) {
		Location low = playerLocation.clone().subtract(xLow, yLow, zLow);
		Location high = playerLocation.clone().add(xHigh, yHigh, zHigh);
		return getLocations(low, high);
	}

	public static Location getHighLocation(Location loc, double high, double size) {

		loc.add(size, high, size);
		return loc;
	}

	public static List<Location> getLocations(Location location1, Location location2) {
		return getLocations(location1, location2, new LocationEffect() {

			public boolean effect(Location location) {
				return true;
			}
		});
	}

	public static Vector getDiretion(Location location, Location target) {
		double distance = target.distance(location);
		double x = ((target.getX() - location.getX()) / distance);
		double y = ((target.getY() - location.getY()) / distance);
		double z = ((target.getZ() - location.getZ()) / distance);
		return new Vector(x, y, z);
	}

	public static Vector getVelocity(Location entity, Location target, double staticX, double staticY, double staticZ,
			double addX, double addY, double addZ) {
		double distance = target.distance(entity);
		double x = (staticX + (addX * distance)) * ((target.getX() - entity.getX()) / distance);
		double y = (staticY + (addY * distance)) * ((target.getY() - entity.getY()) / distance);
		double z = (staticZ + (addZ * distance)) * ((target.getZ() - entity.getZ()) / distance);
		return new Vector(x, y, z);

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

	public static void setDirection(Entity entity, Location target) {
		Location location = entity.getLocation().clone();
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			location = livingEntity.getEyeLocation().clone();

		}
		entity.teleport(entity.getLocation().setDirection(getDiretion(location, target)));

	}

	public static void moveTo(Entity entity, Location target, double staticX, double staticY, double staticZ,
			double addX, double addY, double addZ) {
		Location location = entity.getLocation();
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			location = livingEntity.getEyeLocation();
		}
		entity.setVelocity(getVelocity(location, target, staticX, staticY, staticZ, addX, addY, addZ));
	}

	public static double distanceX(Location loc1, Location loc2) {
		return loc1.getX() - loc2.getX();
	}

	public static double distanceZ(Location loc1, Location loc2) {
		return loc1.getZ() - loc2.getZ();
	}

	public static List<Location> getLocations(Location location1, Location location2, LocationEffect effect) {

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

	public static Location getBlockLocation1(Location location) {

		return new Location(location.getWorld(), (int) location.getX(), (int) location.getY(), (int) location.getZ());
	}

	public static Location getBlockLocation2(Location location) {

		return location.getBlock().getLocation();
	}

	public static List<Location> getBox(Location playerLocation, double higher, double lower, double size) {
		Location high = getHighLocation(playerLocation.clone(), higher, size);
		Location low = getLowLocation(playerLocation.clone(), lower, size);
		return getLocations(low, high);
	}

	public static Location getLowLocation(Location loc, double low, double size) {

		loc.subtract(size, low, size);
		return loc;
	}

	public static List<Player> getPlayerAtRange(Location location, double range) {

		List<Player> players = new ArrayList<>();
		for (Player p : location.getWorld().getPlayers()) {
			if (p.getLocation().distance(location) <= range) {
				players.add(p);
			}
		}
		return players;
	}

	public static LightningStrike strike(Location location) {
		return location.getWorld().strikeLightning(location);
	}

	public static Location getHighLocation(Location loc1, Location loc2) {

		double x = Math.max(loc1.getX(), loc2.getX());
		double y = Math.max(loc1.getY(), loc2.getY());
		double z = Math.max(loc1.getZ(), loc2.getZ());
		return new Location(loc1.getWorld(), x, y, z);
	}

	public static void setEquip(LivingEntity entity, Color color, String name) {
		EntityEquipment inv = entity.getEquipment();
		inv.setBoots(setName(setColor(new ItemStack(Material.LEATHER_BOOTS), color), name));
		inv.setHelmet(setName(setColor(new ItemStack(Material.LEATHER_HELMET), color), name));
		inv.setChestplate(setName(setColor(new ItemStack(Material.LEATHER_CHESTPLATE), color), name));
		inv.setLeggings(setName(setColor(new ItemStack(Material.LEATHER_LEGGINGS), color), name));
	}

	public static void setSlot(Inventory inventory, Slot slot) {
		inventory.setItem(slot.getSlot(), slot.getItem());
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

	public static ItemStack resetName(ItemStack item) {
		setName(item, "");
		return item;
	}

	public static String getName(ItemStack item) {

		return item.hasItemMeta() ? item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "" : "";
	}

	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
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

	public static ItemStack setColor(ItemStack item, Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}

	public static boolean isEmpty(Inventory inventory) {

		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				return false;
			}

		}
		return true;
	}

	public static Inventory newInventory(String name, int size) {

		return Bukkit.createInventory(null, size, name);
	}

	public static Inventory newInventory(String name, GuiSize size) {
		return newInventory(name, size.getValue());
	}

	public static boolean isFull(Inventory inventory) {
		return inventory.firstEmpty() == -1;
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

	public static int getItemsAmount(Inventory inventory) {
		int amount = 0;
		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				amount++;
			}
		}

		return amount;
	}

	public static List<String> getLore(ItemStack item) {
		if (item != null) {
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				return item.getItemMeta().getLore();
			}
		}
		return new ArrayList<String>();
	}

	public static List<Player> getOnlinePlayers() {
		return API.getPlayers();
	}

	public static Player getPlayer(String name) {

		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayerExact(name);
		return player;
	}

	public static void drop(Entity entity, ItemStack item) {
		drop(entity.getLocation(), item);
	}

	public static void teleport(LivingEntity entity, int range) {
		teleport(entity, getTarget(entity, range));
	}

	public static void drop(Location location, ItemStack item) {
		location.getWorld().dropItemNaturally(location, item);
	}

	public static LightningStrike strike(LivingEntity living, int maxDistance) {
		return strike(getTarget(living, maxDistance));
	}

	public static void clearHotBar(Player p) {
		for (int i = 0; i < 9; i++) {
			p.getInventory().setItem(i, null);
		}
	}

	public static Location getTarget(LivingEntity entity, int distance) {
		@SuppressWarnings("deprecation") 
		Block block = entity.getTargetBlock(null, distance);
		return block.getLocation();
	}

	public static List<LivingEntity> getNearbyEntities(LivingEntity player, double x, double y, double z,
			EntityType... types) {
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

	public static List<LivingEntity> getNearbyEntities(LivingEntity entity, double radio, EntityType... entities) {

		return getNearbyEntities(entity, radio, radio, radio, entities);

	}

	public static void teleport(Entity entity, Location target) {
		entity.teleport(target.setDirection(entity.getLocation().getDirection()));
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

	public static void setHotBar(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		for (int i = 0; i < 8; i++) {
			inv.setItem(i, item);
		}
	}

	public static void addHotBar(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		int i;
		while ((i = inv.firstEmpty()) < 9) {
			inv.setItem(i, item);
		}
	}

	public static void makeInvunerable(Player player) {
		player.setNoDamageTicks(DAY_IN_SECONDS * 20);

	}

	

	public static void setSpawn(Entity entity) {

		entity.getWorld().setSpawnLocation((int) entity.getLocation().getX(), (int) entity.getLocation().getY(),
				(int) entity.getLocation().getZ());
	}

	public static void teleportToSpawn(Entity entity) {

		entity.teleport(entity.getWorld().getSpawnLocation().setDirection(entity.getLocation().getDirection()));
	}

	public static boolean isOnGround(Entity entity) {
		return entity.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR;
	}

	public static boolean isFlying(Entity entity) {
		return entity.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getType() == Material.AIR;
	}

	public static boolean isFalling(Entity entity) {
		return entity.getVelocity().getY() < 0.0;
	}

	public static void makeVulnerable(Player player) {

		player.setNoDamageTicks(0);
	}

	public static boolean isInvulnerable(Player player) {
		return player.getNoDamageTicks() > 1;
	}

	public static void clearArmours(LivingEntity entity) {
		entity.getEquipment().setArmorContents(null);
	}

	public static void clearItens(LivingEntity entity) {
		entity.getEquipment().clear();

	}

	public static void clearInventory(Player player) {
		clearItens(player);
		clearArmours(player);
	}

	public static void resetScoreboard(Player player) {
		player.setScoreboard(getMainScoreboard());
	}
	// Parei aqui

	public static void refreshFood(Player player) {
		player.setFoodLevel(20);
		player.setSaturation(20);
		player.setExhaustion(0);
	}

	public static void refreshLife(Player p) {
		p.setHealth(p.getMaxHealth());
	}

	public static void refreshAll(Player player) {
		clearInventory(player);
		removeEffects(player);
		refreshLife(player);
		refreshFood(player);
		makeVulnerable(player);
	}

	public static void removeEffects(Player player) {
		player.setFireTicks(0);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/////////////////////////////

	public static void all(Object...objects) {

		broadcast(objects);
		console(objects);
	}
	public static void sendGlobalMessage(Object... objects) {
		all(objects);
	}
	public static void sendMessage(Object... objects) {
		broadcast(objects);
	}
	public static void broadcast(Object...objects) {

		for (Player p : getPlayers()) {
			p.sendMessage(getText(objects));
		}
	}
	public static String getText(Object...objects) {
		StringBuilder builder = new StringBuilder();
		for (Object object:objects) {
			builder.append(object);
			
		}
		return builder.toString();
	}

	public static List<Player> getPlayers() {
		return PlayerAPI.getPlayers();
	}
	public static void sendConsole(Object... objects) {
		console(objects);
	}

	public static void console(Object... objects) {

		Bukkit.getConsoleSender().sendMessage(getText(objects));
	}

	public static void broadcast(String message, String permision) {
		for (Player p : getPlayers()) {
			if (p.hasPermission(permision)) {
				p.sendMessage(message);
			}
		}
	}

	public static boolean existsWorld(CommandSender sender, String name) {
		World world = Bukkit.getWorld(name);
		if (world == null) {
			sender.sendMessage(API.WORLD_NOT_EXISTS.replace("$world", name));
			return false;
		}
		return true;
	}

	public static boolean getChance(double chance) {

		return RANDOM.nextDouble() <= chance;
	}

	public static void hide(Player player) {
		for (Player target : API.getPlayers()) {
			if (target != player) {
				target.hidePlayer(player);
			}
		}
	}

	public static String toChatMessage(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String toConfigMessage(String text) {
		return text.replace("§", "&");
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

	public static void show(Player player) {
		for (Player target : getPlayers()) {
			if (target != player) {
				target.showPlayer(player);
			}
		}
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

	public static String toDecimal(Object number) {
		return toDecimal(number, 2);
	}

	public static double getRandomDouble(double minValue, double maxValue) {

		double min = Math.min(minValue, maxValue), max = Math.max(minValue, maxValue);
		return min + (max - min) * RANDOM.nextDouble();
	}

	public static Integer toInteger(Object object) {
		return toInt(object);
	}

	public static int getRandomInt(int minValue, int maxValue) {

		int min = Math.min(minValue, maxValue), max = Math.max(minValue, maxValue);
		return min + RANDOM.nextInt(max - min + 1);
	}

	public static ItemStack getRandomItem(ItemStack... items) {

		return items[RANDOM.nextInt(items.length - 1)];
	}

	public static ItemStack getRandomItem(List<ItemStack> items) {

		return items.get(RANDOM.nextInt(items.size() - 1));
	}

	public static String toText(Collection<String> message) {
		return message.toString().replace("[", "").replace("]", "");
	}

	public static String toText(String... message) {

		return message.toString().replace("[", "").replace("]", "");
	}

	public static String toText(int size, String text) {

		return text.length() > size ? text.substring(0, size) : text;
	}

	public static String toText(String text) {

		return toText(16, text);
	}

	public static boolean isMultBy(int number1, int numer2) {

		return number1 % numer2 == 0;
	}

	public static long getTime() {
		return System.currentTimeMillis();
	}

	public static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

	public static Scoreboard newScoreboard() {
		return Bukkit.getScoreboardManager().getNewScoreboard();

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

	public static String getTime(int time) {
		
		return getTime(time, " segundos", " minutos");

	}

	public static String getTimeMid(int time) {

		return getTime(time, " seg", " min");

	}

	public static String getTimeSmall(int time) {

		return getTime(time, "s", "m");

	}

	public static String getTime(int time, String second, String minute) {
		if (time >= 60) {
			int min = time / 60;
			int sec = time % 60;
			return min + minute + sec + second;
		}
		return time + second;
	}

	public static boolean hasPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null;
	}

	public static boolean contains(String message, String text) {
		return message.toLowerCase().contains(text.toLowerCase());
	}

	public static boolean startWith(String message, String text) {
		return message.toLowerCase().startsWith(text.toLowerCase());
	}

	public static boolean existsPlayer(CommandSender sender, String player) {

		Player p = getPlayer(player);
		if (p == null) {
			sender.sendMessage(API.PLAYER_NOT_EXISTS.replace("$player", player));
			return false;
		}
		return true;
	}

	public static boolean onlyPlayer(CommandSender sender) {
		return noConsole(sender);
	}

	public static boolean noConsole(CommandSender sender) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(API.ONLY_PLAYER);
			return false;
		}
		return true;
	}

	public static boolean hasPerm(CommandSender sender, String permission) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(API.NO_PERMISSION.replace("$permission", permission));
			return false;
		}
		return true;

	}

	public static PluginCommand command(String commandName, CommandExecutor command) {
		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermissionMessage(API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}

	public static PluginCommand command(String commandName, CommandExecutor command, String permission) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(API.NO_PERMISSION.replace("$permission", cmd.getPermission()));
		return cmd;
	}

	public static PluginCommand command(String commandName, CommandExecutor command, String permission,
			String permissionMessage) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(permissionMessage);
		return cmd;
	}

	public static void event(Listener event) {

		event(event, getAPI());
	}

	public static JavaPlugin getAPI() {
		JavaPlugin plugin;
		try {
			plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("EduardAPI");
			if (plugin.isEnabled()) {
				return plugin;
			}
		} catch (Exception ex) {

		}
		return (JavaPlugin) Bukkit.getPluginManager().getPlugins()[0];
	}

	public static void event(Listener event, JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(event, plugin);
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

	public static void removeTag(Player player) {
		setTag(player, "");
	}

	public static void setTag(Player player, String prefix) {
		setTag(player, prefix, "");
	}

	public static void setTag(Player player, String prefix, String suffix) {
		setTag(player, new Tag(prefix, suffix));
	}

	public static void setTag(Player player, Tag tag) {
		Tag.getTags().put(player.getUniqueId(), tag);

	}

	public static Tag getTag(Player player) {

		return Tag.getTags().get(player.getUniqueId());
	}

	public static ItemStack newItem(Material material, String name) {
		ItemStack item = new ItemStack(material);
		setName(item, name);
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

	public static ItemStack newItem(Material material, String name, int amount) {
		return newItem(material, name, amount, 0);
	}

	public static ItemStack newItem(String name, Material material, int amount, int data, String... lore) {
		return newItem(material, name, amount, data, lore);
	}

	public static ItemStack newItem(Material material, String name, int amount, int data, String... lore) {

		ItemStack item = newItem(material, name);
		setLore(item, lore);
		item.setAmount(amount);
		item.setDurability((short) data);
		return item;
	}

	public static ItemStack add(ItemStack item, Enchantment type, int level) {
		item.addUnsafeEnchantment(type, level);
		return item;
	}

	public static ItemStack addEnchant(ItemStack item, Enchantment type, int level) {
		return add(item, type, level);
	}

	public static boolean isUsing(LivingEntity entity, Material material) {
		return (getHandType(entity) == material);
	}

	public static boolean isUsing(LivingEntity entity, String material) {
		return (contains(getHandType(entity).name(),(material)));
	}

	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
	}

	public static String getText(String text, Player player) {
		for (Entry<String, Replacer> value : REPLACERS.entrySet()) {
			text = text.replace("$" + value.getKey(), value.getValue().getText(player).toString());
		
		}
		return text;
	}

	public static void saveItems(Player player) {
		INV_ARMOURS.put(player, player.getInventory().getArmorContents());
		INV_ITEMS.put(player, player.getInventory().getContents());
	}

	@SuppressWarnings("deprecation")
	public static void getItems(Player player) {
		if (INV_ITEMS.containsKey(player)) {
			player.getInventory().setContents(INV_ITEMS.get(player));
		}
		if (INV_ARMOURS.containsKey(player)) {
			player.getInventory().setArmorContents(INV_ARMOURS.get(player));
		}
		player.updateInventory();
	}

	static {
		new GuiManager();
		new PadManager();
		new DropManager();
		REPLACERS.put("player_name", new Replacer() {

			public Object getText(Player p) {
				return p.getName();
			}
		});
		REPLACERS.put("player_size", new Replacer() {

			public Object getText(Player p) {
				return API.getPlayers().size();
			}
		});
		REPLACERS.put("player_world", new Replacer() {

			public Object getText(Player p) {
				return p.getWorld().getName();
			}
		});
		REPLACERS.put("player", new Replacer() {

			public Object getText(Player p) {
				return p.getDisplayName();
			}
		});
		REPLACERS.put("player_health", new Replacer() {

			public Object getText(Player p) {
				return p.getHealth();
			}
		});
		REPLACERS.put("player_max_health", new Replacer() {

			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		REPLACERS.put("player_x", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getX();
			}
		});
		REPLACERS.put("player_y", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getY();
			}
		});
		REPLACERS.put("player_z", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getZ();
			}
		});
	}

}
