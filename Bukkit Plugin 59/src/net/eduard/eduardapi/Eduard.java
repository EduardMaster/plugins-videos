
package net.eduard.eduardapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Eduard {

	public enum ActionGUI {
			RIGHT, LEFT, BOTH
	}

	public class Configs {

		private File file;

		private FileConfiguration config;

		public Configs(String name) {

			file = new File(getPlugin().getDataFolder(), name);
			reloadConfig();
		}

		public void add(String name, Object value) {

			config.addDefault(name, value);
		}

		public Object get(String name) {

			return config.get(name);
		}

		public boolean getBoolean(String name) {

			return config.getBoolean(name);
		}

		public FileConfiguration getConfig() {

			return config;
		}

		public double getDouble(String name) {

			return config.getDouble(name);
		}

		public File getFile() {

			return file;
		}

		public float getFloat(String name) {

			return (float) getDouble(name);
		}

		public int getInt(String name) {

			return config.getInt(name);
		}

		public Set<String> getKeys(String name) {

			return getSection(name).getKeys(false);
		}

		public Location getLocation(String name) {

			return getEduard().getLocation(config, name);
		}

		public long getLong(String name) {

			return config.getLong(name);
		}

		public String getMessage(String name) {

			return ChatColor.translateAlternateColorCodes('&',
				config.getString(name));
		}

		public ConfigurationSection getSection(String name) {

			return config.getConfigurationSection(name);
		}

		public String getString(String name) {

			return config.getString(name);
		}

		public Map<String, Object> getValues(String name) {

			return getSection(name).getValues(false);
		}

		public boolean has(String name) {

			return config.contains(name);
		}

		public boolean hasLocation(String name) {

			return getEduard().hasLocation(config, name);
		}

		public void reloadConfig() {

			config = YamlConfiguration.loadConfiguration(file);
			InputStream imputStream = getPlugin().getResource(file.getName());
			if (imputStream != null) {
				YamlConfiguration imputConfig =
					YamlConfiguration.loadConfiguration(imputStream);
				config.setDefaults(imputConfig);
			}
		}

		public void saveConfig() {

			try {
				config.save(file);
			} catch (IOException ex) {
			}
		}

		public void saveDefault() {

			config.options().copyDefaults(true);
			saveConfig();
		}

		public void saveDefaultConfig() {

			getPlugin().saveResource(file.getName(), true);
		}

		public void set(String name, Object value) {

			config.set(name, value);
		}

		public void setLocation(Location loc, String name) {

			getEduard().setLocation(config, name, loc);
			saveConfig();
		}

	}

	public class Cooldown {

		private HashMap<String, Integer> time = new HashMap<>();

		private HashMap<String, Long> id = new HashMap<>();

		public long getTime(Player p) {

			Long last = id.get(p.getUniqueId().toString());
			Integer delay = time.get(p.getUniqueId().toString());
			delay *= 1000;
			long wait = last - (System.currentTimeMillis() - delay);
			return wait / 1000;
		}

		public boolean has(Player p) {

			if (id.containsKey(p.getUniqueId().toString())) {
				return getTime(p) > 0;
			}
			return false;
		}

		public void remove(Player p) {

			id.remove(p.getUniqueId().toString());
			time.remove(p.getUniqueId().toString());
		}

		public void start(Player p, int seconds) {

			if (!has(p)) {
				id.put(p.getUniqueId().toString(), System.currentTimeMillis());
				time.put(p.getUniqueId().toString(), seconds);
			}
		}
	}

	public class Cooldowns {

		private HashMap<String, Time> time = new HashMap<>();

		public int getTime(Player p) {

			if (has(p)) {
				return time.get(p.getUniqueId().toString()).getTime();
			}
			return -1;
		}

		public boolean has(Player p) {

			return time.containsKey(p.getUniqueId().toString());
		}

		public void start(Player p, int seconds) {

			start(p, seconds, (Runnable) null);
		}

		public void start(final Player p, int seconds, final Runnable end) {

			if (!has(p)) {
				Time time = new Time(new Runnable() {

					public void run() {

						if (getTime(p) == 0) {
							if (end != null) {
								end.run();
							}
							stop(p);
						}
					}
				}, seconds);
				this.time.put(p.getUniqueId().toString(), time);
			}
		}

		public void start(final Player p, int seconds, final String message) {

			start(p, seconds, new Runnable() {

				public void run() {

					p.sendMessage(message);
				}
			});
		}

		public void stop(Player p) {

			if (has(p)) {
				String name = p.getUniqueId().toString();
				time.get(name).stop();
				time.remove(p.getUniqueId().toString());
			}
		}
	}

	@SuppressWarnings("deprecation")
	public class CraftExtra {

		private ShapedRecipe recipe;

		private ItemStack result;

		private Material[] items = new Material[9];

		private int[] datas = new int[9];

		public CraftExtra(ItemStack result) {

			setResult(result);
			setRecipe(new ShapedRecipe(result));
			for (int x = 0; x < datas.length; x++) {
				datas[x] = 0;
			}
		}

		public ShapedRecipe getRecipe() {

			try {
				recipe.shape(
					(items[0] == null ? " " : "A") + (items[1] == null ? " " : "B")
						+ (items[2] == null ? " " : "C"),
					(items[3] == null ? " " : "D") + (items[4] == null ? " " : "E")
						+ (items[5] == null ? " " : "F"),
					(items[6] == null ? " " : "G") + (items[7] == null ? " " : "H")
						+ (items[8] == null ? " " : "I"));

				char shape = 'A';
				for (int x = 0; x < items.length; x++) {
					if (items[x] == null) {
						shape++;
						continue;
					}
					recipe.setIngredient(shape, items[x], datas[x]);
					shape++;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return recipe;
		}

		public ItemStack getResult() {

			return result;
		}

		public void set(int slot, Material material) {

			set(slot, material, 0);

		}

		public void set(int slot, Material material, int data) {

			if (slot < 1 || slot > 9) {
				return;
			}
			items[slot - 1] = material;
			datas[slot - 1] = data;

		}

		public void setRecipe(ShapedRecipe recipe) {

			this.recipe = recipe;
		}

		public void setResult(ItemStack result) {

			this.result = result;
		}
	}

	@SuppressWarnings("deprecation")
	public class CraftNormal {

		private ShapelessRecipe recipe;

		private ItemStack result;

		public CraftNormal(ItemStack result) {

			setResult(result);
			setRecipe(new ShapelessRecipe(result));
		}

		public void add(Material ingredient, int data) {

			recipe.addIngredient(ingredient, data);
		}

		public ShapelessRecipe getRecipe() {

			return recipe;
		}

		public ItemStack getResult() {

			return result;
		}

		public void remove(Material ingredient, int data) {

			recipe.removeIngredient(ingredient, data);
		}

		public void setRecipe(ShapelessRecipe recipe) {

			this.recipe = recipe;
		}

		public void setResult(ItemStack result) {

			this.result = result;
		}

	}

	public class Delays {

		private HashMap<String, Time> time = new HashMap<>();

		public boolean has(Player p) {

			return time.containsKey(p.getUniqueId().toString());
		}

		public void start(Player p, int seconds) {

			start(p, seconds, (Runnable) null);
		}

		public void start(final Player p, int seconds, final Runnable end) {

			if (has(p)) {
				return;
			}
			Time time = new Time(new Runnable() {

				public void run() {

					if (end != null) {
						end.run();
					}
					stop(p);
				}
			}, seconds, 1);
			this.time.put(p.getUniqueId().toString(), time);

		}

		public void start(final Player p, int seconds, final String message) {

			start(p, seconds, new Runnable() {

				public void run() {

					p.sendMessage(message);
				}
			});
		}

		public void stop(Player p) {

			if (has(p)) {
				String name = p.getUniqueId().toString();
				time.get(name).stop();
				time.remove(p.getUniqueId().toString());
			}
		}
	}

	public class Fireworks {

		private Firework firework;

		private FireworkMeta meta;

		private List<FireworkEffect> effects;

		public Fireworks(Location loc) {

			firework = loc.getWorld().spawn(loc, Firework.class);
			meta = firework.getFireworkMeta();
			FireworkMeta meta = firework.getFireworkMeta();
			effects = meta.getEffects();
		}

		public List<FireworkEffect> getEffects() {

			return effects;
		}

		public Firework getFirework() {

			return firework;
		}

		public void item(FireworkEffect effect) {

			meta.addEffect(effect);
			reload();
		}

		public void item(FireworkEffect.Type type, boolean flicker, boolean trail,
			Color firstColor, Color lastColor) {

			item(FireworkEffect.builder().with(type).flicker(flicker).trail(trail)
				.withColor(firstColor).withFade(lastColor).build());
		}

		public void item(FireworkType type, Color firstColor, Color lastColor) {

			item(FireworkEffect.Type.valueOf(type.name()), true, true, firstColor,
				lastColor);
		}

		public void reload() {

			firework.setFireworkMeta(meta);
		}

		public void setPower(int power) {

			meta.setPower(power);
			reload();
		}
	}

	public enum FireworkType {
			BALL, BALL_LARGE, BURST, CREEPER, STAR
	}

	public static class GUI {

		private static class Event implements Listener {

			@SuppressWarnings("deprecation")
			@EventHandler
			public void InteractGui(InventoryClickEvent e) {

				if (!(e.getWhoClicked() instanceof Player)) {
					return;
				}
				Player p = (Player) e.getWhoClicked();
				if (e.getCurrentItem() == null) {
					return;
				}
				for (GUI gui : GUI.all_gui) {
					if (gui.getGUI().getTitle()
						.equals(e.getInventory().getTitle())) {
						if (gui.effect.containsKey(e.getSlot())) {
							if (gui.getItemKey() != null) {
								if (gui.getItemKey().equals(e.getCurrentItem())) {
									return;
								}
							}
							if (gui.getGUI().getSize() < e.getRawSlot()) {
								p.updateInventory();
								return;
							}
							if (gui.getGUI().getItem(e.getSlot())
								.equals(e.getCurrentItem())) {
								IconEffect slotEffect = gui.effect.get(e.getSlot());
								if (slotEffect != null) {
									slotEffect.run(p);
								}
							}

						}
						e.setCancelled(true);
						return;
					}
				}
			}

			@EventHandler
			public void OpenGui(InventoryOpenEvent e) {

				if (!(e.getPlayer() instanceof Player)) {
					return;
				}
				Inventory inv = e.getInventory();
				Player p = (Player) e.getPlayer();
				for (GUI gui : GUI.all_gui) {
					if (inv.getTitle().equals(gui.getGUI().getTitle())) {
						p.playSound(p.getLocation(), gui.getOpenSound(), 2, 1);
						return;
					}
				}
			}

			@EventHandler
			public void OpenGui(PlayerInteractEvent e) {

				Player p = e.getPlayer();
				ItemStack item = e.getItem();
				Action action = e.getAction();
				if (item == null) {
					return;
				}
				for (GUI gui : GUI.all_gui) {
					if (gui.getItemKey() == null) {
						continue;
					}
					boolean itemType = false;
					if (gui.isByMaterial()) {
						if (gui.getItemKey().getType() == item.getType()) {
							itemType = true;
						}
					} else if (gui.getItemKey().equals(item)) {
						itemType = true;
					}
					if (itemType) {
						boolean actionType = false;
						if (gui.getAction() == ActionGUI.RIGHT) {
							if (action == Action.RIGHT_CLICK_AIR
								|| action == Action.RIGHT_CLICK_BLOCK) {
								actionType = true;
							}
						} else if (gui.getAction() == ActionGUI.LEFT) {
							if (action == Action.LEFT_CLICK_AIR
								|| action == Action.LEFT_CLICK_BLOCK) {
								actionType = true;
							}
						} else if (gui.getAction() == ActionGUI.BOTH) {
							if (action != Action.PHYSICAL) {
								actionType = true;
							}
						}
						if (actionType) {
							e.setCancelled(true);
							p.openInventory(gui.getGUI());
							return;
						}
					}
				}
			}
		}

		public static List<GUI> all_gui = new ArrayList<>();

		public static void enableGUI() {

			Bukkit.getPluginManager().registerEvents(new GUI.Event(),
				getEduard().getPlugin());
		}

		public HashMap<Integer, IconEffect> effect = new HashMap<>();

		private Inventory inv;

		private ActionGUI action;

		private Sound openSound;

		private ItemStack itemKey;

		private boolean byMaterial;

		public GUI(String name, int size) {

			if (name.isEmpty()) {
				name = "No empty Name";
			}
			if (size % 9 > 0) {
				size = 9;
			} else if (size < 0 | size > 9 * 6) {
				size = 9;
			}
			setGUI(Bukkit.createInventory(null, size, name));
			GUI remove = null;
			for (GUI gui : all_gui) {
				if (gui.getGUI().getTitle().equals(name)) {
					remove = gui;
				}
			}
			if (remove != null) {
				all_gui.remove(remove);
			}
			all_gui.add(this);
			setAction(ActionGUI.BOTH);
			setOpenSound(Sound.HORSE_ARMOR);
			setByMaterial(false);
		}

		public void addIcon(ItemStack icon) {

			getGUI().addItem(icon);
		}

		public void addIcon(ItemStack icon, IconEffect run) {

			addIcon(icon);
			int id = getGUI().first(icon);
			effect.put(id, run);
		}

		public ActionGUI getAction() {

			return action;
		}

		public Inventory getGUI() {

			return inv;
		}

		public ItemStack getItemKey() {

			return itemKey;
		}

		public Sound getOpenSound() {

			return openSound;
		}

		public boolean isByMaterial() {

			return byMaterial;
		}

		public void setAction(ActionGUI action) {

			this.action = action;
		}

		public void setByMaterial(boolean byMaterial) {

			this.byMaterial = byMaterial;
		}

		private void setGUI(Inventory invGUI) {

			inv = invGUI;
		}

		public void setIcon(int slot, ItemStack icon) {

			getGUI().setItem(slot, icon);
		}

		public void setIcon(int slot, ItemStack icon, IconEffect run) {

			getGUI().setItem(slot, icon);
			effect.put(slot, run);
		}

		public void setItemKey(ItemStack itemKey) {

			this.itemKey = itemKey;
		}

		public void setOpenSound(Sound openSound) {

			this.openSound = openSound;
		}

	}

	public interface IconEffect {

		void run(Player p);
	}

	public interface IPlayer {

		void run(Player p);

	}

	public interface ITimer {

		void finalRun();

		void lastRun();

		void normalRun();

	}

	public class Minigame {

		public class Arena {

			private Configs config;

			public Arena(String name) {

				config = new Configs(arenas + "/" + name + ".yml");
			}

			public void create() {

				enable(false);
			}

			public void delete() {

				config.getFile().delete();
			}

			public boolean enable() {

				return config.getBoolean("enable");
			}

			public void enable(boolean value) {

				set("enable", value);
			}

			public boolean exist() {

				return config.getFile().exists();
			}

			public Object get(String name) {

				return config.get(name);
			}

			public Location getLocation(String name) {

				return config.getLocation(name);
			}

			public boolean has(String name) {

				return config.has(name);
			}

			public boolean hasLocation(String name) {

				return config.hasLocation(name);
			}

			public void set(String name, Object value) {

				config.set(name, value);
				config.saveConfig();
			}

			public void setLocation(Location loc, String name) {

				config.setLocation(loc, name);
			}

		}

		private String name;

		private String arenas;

		public HashMap<String, String> players = new HashMap<>();

		public Minigame() {

			name = "";
			arenas = "Arenas";

		}

		public Arena getArena(String name) {

			return new Arena(name);
		}

		public List<String> getArenas() {

			Configs arenaPasta = new Configs(arenas);
			arenaPasta.getFile().mkdirs();
			return Arrays.asList(Arrays.asList(arenaPasta.getFile().list())
				.toString().replace(".yml", "").split(", "));
		}

		public List<String> getEnabledArenas() {

			ArrayList<String> list = new ArrayList<String>();
			for (String essaArena : getArenas()) {
				if (getArena(essaArena).enable()) {
					list.add(essaArena);
				}
			}
			return list;
		}

		public Location getLobby() {

			return getEduard().getLocation(getPlugin().getConfig(),
				name + "." + "Lobby");
		}

		@SuppressWarnings("deprecation")
		public List<Player> getPlayers() {

			List<Player> player = new ArrayList<>();
			for (String p : players.keySet()) {
				player.add(Bukkit.getPlayerExact(p));
			}
			return player;
		}

		@SuppressWarnings("deprecation")
		public List<Player> getPlayers(String arena) {

			List<Player> player = new ArrayList<>();
			for (Entry<String, String> p : players.entrySet()) {
				if (p.getValue().equals(arena)) {
					player.add(Bukkit.getPlayerExact(p.getKey()));
				}
			}
			return player;
		}

		public boolean hasLobby() {

			return getEduard().hasLocation(getPlugin().getConfig(),
				name + "." + "Lobby");

		}

		public void setLobby(Player p) {

			getEduard().setLocation(getPlugin().getConfig(), name + "." + "Lobby",
				p.getLocation());
			getPlugin().saveConfig();
		}

	}

	public class Scoreboards {

		private Scoreboard score;

		private Objective scoreObj;

		private HashMap<Integer, String> scoreSlots;

		private int scoreSize;

		private int scoreNameSize;

		private String scoreName;

		private String scoreNameColor;

		private int scoreNameId;

		public Scoreboards() {

			scoreSlots = new HashMap<>();
			score = Bukkit.getScoreboardManager().getNewScoreboard();
			scoreObj = score.registerNewObjective(getName("§6§lSimples ScoreBoard"),
				"dummy");
			scoreObj.setDisplaySlot(DisplaySlot.SIDEBAR);
			setName("§6§lSimples ScoreBoard");
			update();
		}

		public String getFake(int size) {

			String text = "";
			for (int x = size; x > 1; x--) {
				text = text.concat(" ");
			}
			return text;
		}

		public String getFake(int id, int size) {

			if (size < 1 | size > 14) {
				size = 14;
			}
			return ChatColor.values()[id] + getFake(size);
		}

		public String getName(int size, String name) {

			return name.length() > size ? name.substring(0, size) : name;
		}

		public String getName(String name) {

			return name.length() > 16 ? name.substring(0, 16) : name;
		}

		public Scoreboard getScore() {

			return score;
		}

		@SuppressWarnings("deprecation")
		public void removeSlot(int id) {

			if (scoreSlots.containsKey(id)) {
				score.resetScores(Bukkit.getOfflinePlayer(scoreSlots.get(id)));
				scoreSlots.remove(id);
			}
		}

		public void setFake(int id) {

			setFake(id, 10);
		}

		public void setFake(int id, int size) {

			setSlot(id, getFake(id, size));
		}

		public void setName(String name) {

			scoreObj.setDisplayName(getName(32, name));
			scoreName = name;
			scoreNameId = 0;
		}

		public void setNameSize(int nameSize, String colorBeforeName) {

			if (nameSize < 5) {
				nameSize = 5;
			}
			if (nameSize + colorBeforeName.length() > 32) {
				nameSize -= colorBeforeName.length();
			}
			scoreNameSize = nameSize;
			scoreNameColor = colorBeforeName;
		}

		public void setSize(int size) {

			if (size > 15 | size < 1) {
				size = 15;
			}
			scoreSize = size;
			for (int x = 1; x < 15; x++) {
				removeSlot(x);
			}
			scoreSlots.clear();
			for (int x = 1; x <= size; x++) {
				setFake(x);
			}
		}

		@SuppressWarnings("deprecation")
		public void setSlot(int id, String name) {

			if (id > scoreSize || id < 1) {
				return;
			}
			name = getName(name);
			removeSlot(id);
			scoreSlots.put(id, name);
			scoreObj.getScore(Bukkit.getOfflinePlayer(name)).setScore(id);
		}

		public void show(Player p) {

			p.setScoreboard(score);
		}

		private void update() {

			setNameSize(16, "§f§l");
			new Time(new Runnable() {

				public void run() {

					String text = ChatColor.stripColor(scoreName);
					if (text.length() > scoreNameSize) {
						text = text + getFake(5);
						scoreNameId++;
						String message = "";
						if (scoreNameId > text.length()) {
							scoreNameId = 0;
							message = text.substring(0, scoreNameSize);
						} else if (scoreNameId > text.length() - scoreNameSize) {
							String frontText = text.substring(0,
								scoreNameId + scoreNameSize - text.length());
							message = text.substring(0 + scoreNameId, text.length())
								+ frontText;
						} else {
							message = text.substring(0 + scoreNameId,
								scoreNameSize + scoreNameId);
						}
						scoreObj.setDisplayName(scoreNameColor + message);
					}
				}

			}, 5L, -1);

		}

	}

	public class Time {

		private int id = -1;

		private int time = -1;

		public Time(Runnable run) {

			this(run, 1);
		}

		public Time(Runnable run, int times) {

			this(run, 1, times);
		}

		public Time(Runnable run, int seconds, int times) {

			this(run, seconds * 20L, times);
		}

		public Time(final Runnable run, long ticks, final int times) {

			if (times < 0) {
				time = 100000;
			} else {
				time = times;
			}
			id = new BukkitRunnable() {

				public void run() {

					if (times == time) {
						time--;
						run.run();
					} else if (time < 1) {
						stop();
					} else {
						time--;
						run.run();
					}
				}
			}.runTaskTimer(getPlugin(), ticks, ticks).getTaskId();
		}

		public int getTime() {

			return time;
		}

		public void setTime(int time) {

			this.time = time;
		}

		public void stop() {

			if (id != -1) {
				Bukkit.getScheduler().cancelTask(id);
			}
		}

	}

	public class Timers {

		private Time time;

		private ITimer game;

		private int[] numbers;

		public Timers() {

			setGame(new ITimer() {

				@Override
				public void finalRun() {

					Bukkit.broadcastMessage("§6Final Run: §eGame Over");
				}

				@Override
				public void lastRun() {

					Bukkit.broadcastMessage("§6Last Run: §b" + getTime());
				}

				@Override
				public void normalRun() {

					Bukkit.broadcastMessage("§6Normal Run: §e" + getTime());
				}
			});
			setNumbers(1, 2, 3, 4, 5, 10, 20, 30, 60);
		}

		public ITimer getGame() {

			return game;
		}

		public int[] getNumbers() {

			return numbers;
		}

		public int getTime() {

			return time.getTime();
		}

		public void setGame(ITimer game) {

			this.game = game;
		}

		public void setNumbers(int... numbers) {

			this.numbers = numbers;
		}

		public void setTime(int seconds) {

			time.setTime(seconds);
		}

		public void start(ITimer game, int seconds) {

			if (game != null) {
				setGame(game);
			}
			time = new Time(new Runnable() {

				public void run() {

					if (getTime() == 0) {
						getGame().finalRun();
					} else {
						for (int id : getNumbers()) {
							if (id == getTime()) {
								getGame().normalRun();
							}
						}
					}
				}
			}, 1, seconds);
		}

		public boolean started() {

			return getTime() > 0;
		}

		public void stop() {

			if (time != null) {
				time.stop();
			}
		}

	}

	public static Eduard getEduard() {

		return new Eduard();
	}

	public ItemStack add(Enchantment ench, int lvl, ItemStack item) {

		item.addEnchantment(ench, lvl);
		return item;
	}

	public ItemStack add(Enchantment ench, ItemStack item) {

		item.addEnchantment(ench, 1);
		return item;
	}

	public ItemStack add(Inventory inv, ItemStack item) {

		inv.addItem(item);
		return item;
	}

	public void add(String name, Object value) {

		getPlugin().getConfig().addDefault(name, value);
	}

	public ItemStack addLore(String message, ItemStack item) {

		ItemMeta meta = item.getItemMeta();
		if (meta.getLore() == null) {
			meta.setLore(Arrays.asList(message));
		} else {
			meta.getLore().add(message);
		}
		item.setItemMeta(meta);
		return item;
	}

	public List<Location> blocksBox(Location loc, int size, int high) {

		return blocksBox(loc, size, high, 0);
	}

	public List<Location> blocksBox(Location loc, int size, int high, int low) {

		return getLocations(getHighLocation(getLocation(loc), high, size),
			getLowLocation(getLocation(loc), low, size));
	}

	public List<Location> blocksSquared(Location loc, int size) {

		return blocksBox(loc, size, 0);
	}

	public boolean chance(double chance) {

		Random random = new Random();
		return random.nextDouble() <= chance;
	}

	public ItemStack clearLore(ItemStack item) {

		ItemMeta meta = item.getItemMeta();
		if (meta.getLore() == null) {
			meta.setLore(new ArrayList<String>());
		} else {
			meta.getLore().clear();
		}
		item.setItemMeta(meta);
		return item;
	}

	public String colorToString(String name) {

		return name.replace('§', '&');
	}

	public boolean compareLocation(Location loc1, Location loc2) {

		return locationToInt(loc1).equals(locationToInt(loc2));
	}

	public void createFloor(Location loc, int size, Material material) {

		List<Location> locations = blocksSquared(loc, size);
		setType(locations, material);
	}

	public void effect(Location loc, Effect effect) {

		effect(loc, effect, 0);
	}

	public void effect(Location loc, Effect effect, int data) {

		loc.getWorld().playEffect(loc, effect, data);
	}

	public void effect(Location loc, Effect effect, int data, int radius) {

		loc.getWorld().playEffect(loc, effect, data, radius);
	}

	public boolean exist(Player p) {

		return Arrays.asList(Bukkit.getOfflinePlayers()).contains(p);
	}

	public Object get(String name) {

		return getPlugin().getConfig().get(name);
	}

	public List<Block> getBlocks(Block block1, Block block2) {

		return getBlocks(block1.getLocation(), block2.getLocation());
	}

	public List<Block> getBlocks(Location loc1, Location loc2) {

		List<Block> blocks = new ArrayList<>();
		for (Location block : getLocations(loc1, loc2)) {
			blocks.add(block.getBlock());
		}
		return blocks;
	}

	public boolean getBoolean(String name) {

		return getPlugin().getConfig().getBoolean(name);
	}

	public double getDouble(String name) {

		return getPlugin().getConfig().getDouble(name);
	}

	public float getFloat(String name) {

		return (float) getDouble(name);
	}

	public Location getHighLocation(Location loc, int high, int size) {

		loc.add(size, high, size);
		return loc;
	}

	public Eduard getInstance() {

		return this;
	}

	public int getInt(String name) {

		return getPlugin().getConfig().getInt(name);
	}

	public Set<String> getKeys(String name) {

		return getSection(name).getKeys(false);
	}

	public Location getLocation(FileConfiguration config, String name) {

		World w = Bukkit.getWorld(config.getString(name + ".world"));
		double x = config.getDouble(name + ".x");
		double y = config.getDouble(name + ".y");
		double z = config.getDouble(name + ".z");
		double yaw = config.getDouble(name + ".yaw");
		double pitch = config.getDouble(name + ".pitch");
		return new Location(w, x, y, z, (float) yaw, (float) pitch);
	}

	public Location getLocation(Location loc) {

		return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
			loc.getYaw(), loc.getPitch());
	}

	public Location getLocation(String name) {

		return getLocation(getPlugin().getConfig(), name);
	}

	public List<Location> getLocations(Block block1, Block block2) {

		return getLocations(block1.getLocation(), block2.getLocation());
	}

	public List<Location> getLocations(Location loc1, Location loc2) {

		Location min = getMinLocation(loc1, loc2);
		Location max = getMaxLocation(loc1, loc2);
		return getLocs(min, max);
	}

	public List<Location> getLocs(Location min, Location max) {

		List<Location> locs = new ArrayList<>();
		for (double x = min.getX(); x <= max.getX(); x++) {
			for (double y = min.getY(); y <= max.getY(); y++) {
				for (double z = min.getZ(); z <= max.getZ(); z++) {
					Location loc = new Location(min.getWorld(), x, y, z);
					if (!locs.contains(loc)) {
						locs.add(loc);
					}
				}
			}
		}
		return locs;
	}

	public long getLong(String name) {

		return getPlugin().getConfig().getLong(name);
	}

	public Location getLowLocation(Location loc, int low, int size) {

		loc.subtract(size, low, size);
		return loc;
	}

	public Location getMaxLocation(Location loc1, Location loc2) {

		double x = Math.max(loc1.getX(), loc2.getX());
		double y = Math.max(loc1.getY(), loc2.getY());
		double z = Math.max(loc1.getZ(), loc2.getZ());
		return new Location(loc1.getWorld(), x, y, z);
	}

	public String getMessage(String name) {

		return ChatColor.translateAlternateColorCodes('&', getString(name));
	}

	public Location getMinLocation(Location loc1, Location loc2) {

		double x = Math.min(loc1.getX(), loc2.getX());
		double y = Math.min(loc1.getY(), loc2.getY());
		double z = Math.min(loc1.getZ(), loc2.getZ());
		return new Location(loc1.getWorld(), x, y, z);
	}

	public String getName(ItemStack item) {

		return item.getItemMeta().getDisplayName();
	}

	@SuppressWarnings("deprecation")
	public Player getPlayer(String name) {

		return Bukkit.getPlayerExact(name);
	}

	public JavaPlugin getPlugin() {

		return Main.instance;
	}

	public ConfigurationSection getSection(String name) {

		return getPlugin().getConfig().getConfigurationSection(name);
	}

	public String getString(String name) {

		return getPlugin().getConfig().getString(name);
	}

	public Map<String, Object> getValues(String name) {

		return getSection(name).getValues(false);
	}

	public World getWorld(String name) {

		return Bukkit.getWorld(name);
	}

	public boolean has(String name) {

		return getPlugin().getConfig().contains(name);
	}

	public boolean hasLocation(FileConfiguration config, String name) {

		return config.contains(name + ".world");
	}

	public boolean hasLocation(String name) {

		return hasLocation(getPlugin().getConfig(), name);
	}

	public boolean hasMeta(ItemStack item) {

		return item.hasItemMeta();
	}

	public boolean hasName(ItemStack item) {

		return item.getItemMeta().hasDisplayName();
	}

	public boolean isLocation(FileConfiguration config, String name) {

		return config.contains(name + ".world") && config.contains(name + ".x")
			&& config.contains(name + ".y") && config.contains(name + ".z")
			&& config.contains(name + ".yaw") && config.contains(name + ".pitch");
	}

	public boolean isOnline(Player p) {

		return p != null;
	}

	public ItemStack item(Material type) {

		return item(type, 1);
	}

	public ItemStack item(Material type, int amount) {

		return item(type, amount, (short) 0);
	}

	public ItemStack item(Material type, int amount, short data) {

		return item(type, amount, data, null);
	}

	public ItemStack item(Material type, int amount, short data, String name) {

		List<String> lista = null;
		return item(type, amount, data, name, lista);
	}

	public ItemStack item(Material type, int amount, short data, String name,
		List<String> lores) {

		ItemStack item = new ItemStack(type, amount, data);
		if (name != null) {
			setName(name, item);
		}
		if (lores != null) {
			setLore(lores, item);
		}
		return item;
	}

	public ItemStack item(Material type, int amount, short data, String name,
		String... lores) {

		return item(type, amount, data, name, Arrays.asList(lores));
	}

	public ItemStack item(Material type, int amount, String name) {

		return item(type, amount, (short) 0, name);
	}

	public ItemStack item(Material type, short data) {

		return item(type, 1, data);
	}

	public ItemStack item(Material type, short data, String name) {

		return item(type, 1, data, name);
	}

	public ItemStack item(Material type, String name) {

		return item(type, (short) 0, name);
	}

	public ItemStack item(Material type, String name, List<String> lores) {

		return item(type, 1, (short) 0, name, lores);
	}

	public ItemStack item(Material type, String name, String... lores) {

		return item(type, 1, (short) 0, name, lores);
	}

	public Location locationToInt(Location loc) {

		return new Location(loc.getWorld(), (int) loc.getX(), (int) loc.getY(),
			(int) loc.getZ());
	}

	public void potion(LivingEntity ent, PotionEffectType potion, int seconds) {

		potion(ent, potion, seconds, 1);
	}

	public void potion(LivingEntity ent, PotionEffectType potion, int seconds,
		int force) {

		potion(ent, potion, seconds, force, true);
	}

	public void potion(LivingEntity ent, PotionEffectType potion, int seconds,
		int force, boolean ambient) {

		ent.addPotionEffect(new PotionEffect(potion, 20 * seconds, force, ambient));
	}

	public int random(int x, int y) {

		int min, max;
		min = Math.min(x, y);
		max = Math.max(x, y);
		Random random = new Random();
		int numberRandom = min + random.nextInt(max - min + 1);
		return numberRandom;
	}

	public void reloadConfig() {

		getPlugin().reloadConfig();
	}

	public void saveConfig() {

		getPlugin().saveConfig();
	}

	public void saveDefault() {

		getPlugin().getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void saveDefaultConfig() {

		getPlugin().saveDefaultConfig();
	}

	public ItemStack set(Inventory inv, int slot, ItemStack item) {

		inv.setItem(slot, item);
		return item;
	}

	public void set(String name, Object value) {

		getPlugin().getConfig().set(name, value);
	}

	public void setCommand(String cmd, CommandExecutor ex) {

		getPlugin().getCommand(cmd).setExecutor(ex);
	}

	public void setCommands(Map<String, CommandExecutor> cmds) {

		for (Entry<String, CommandExecutor> cmd : cmds.entrySet()) {
			setCommand(cmd.getKey(), cmd.getValue());
		}
	}

	public void setError(String cmd) {

		getPlugin().getCommand(cmd)
			.setPermissionMessage(getEduard().getMessage("no-perm"));
	}

	public void setErrors(String[] cmds) {

		for (String cmd : cmds) {
			setError(cmd);
		}
	}

	public void setEvent(Listener reg) {

		Bukkit.getPluginManager().registerEvents(reg, getPlugin());
	}

	public void setEvents(Listener[] regs) {

		for (Listener reg : regs) {
			setEvent(reg);
		}
	}

	public void setLocation(FileConfiguration config, String name, Location loc) {

		config.set(name + ".world", loc.getWorld().getName());
		config.set(name + ".x", loc.getX());
		config.set(name + ".y", loc.getY());
		config.set(name + ".z", loc.getZ());
		config.set(name + ".yaw", loc.getYaw());
		config.set(name + ".pitch", loc.getPitch());
	}

	public void setLocation(String name, Location loc) {

		setLocation(getPlugin().getConfig(), name, loc);
		saveConfig();
	}

	public ItemStack setLore(List<String> lores, ItemStack item) {

		ItemMeta meta = item.getItemMeta();
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack setName(String name, ItemStack item) {

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public void setPermission(String cmd, String perm) {

		getPlugin().getCommand(cmd).setPermission(perm);
	}

	public void setPermissions(Map<String, String> perms) {

		for (Entry<String, String> perm : perms.entrySet()) {
			setPermission(perm.getKey(), perm.getValue());
		}

	}

	@SuppressWarnings("deprecation")
	public void setType(List<Location> locations, int id) {

		for (Location block : locations) {
			block.getBlock().setTypeId(id);
		}
	}

	public void setType(List<Location> locations, Material material) {

		for (Location block : locations) {
			block.getBlock().setType(material);
		}
	}

	public void sound(Location loc, Sound sound) {

		sound(loc, sound, 2);
	}

	public void sound(Location loc, Sound sound, float volume) {

		sound(loc, sound, volume, 1);
	}

	public void sound(Location loc, Sound sound, float volume, float pitch) {

		loc.getWorld().playSound(loc, sound, volume, pitch);
	}

	public List<Block> toBlocks(List<Location> locations) {

		List<Block> blocks = new ArrayList<>();
		for (Location location : locations) {
			blocks.add(location.getBlock());
		}
		return blocks;
	}

	public List<Location> toLocations(List<Block> blocks) {

		List<Location> locations = new ArrayList<>();
		for (Block block : blocks) {
			locations.add(block.getLocation());
		}
		return locations;
	}

}
