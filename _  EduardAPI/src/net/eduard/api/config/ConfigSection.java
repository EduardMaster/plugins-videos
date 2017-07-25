package net.eduard.api.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.chat.Chats;
import net.eduard.api.command.ReportCommand;
import net.eduard.api.command.staff.AdminCommand;
import net.eduard.api.config.save.SaveConfig;
import net.eduard.api.config.save.SaveItemStack;
import net.eduard.api.config.save.SaveLocation;
import net.eduard.api.config.save.SaveVector;
import net.eduard.api.game.Potions;
import net.eduard.api.game.Sounds;
import net.eduard.api.game.Tag;
import net.eduard.api.game.Title;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.DropItem;
import net.eduard.api.kits.Archer;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.manager.VaultAPI;
import net.eduard.api.time.Cooldown;
import net.eduard.api.util.Replacer;
import net.eduard.api.util.Save;

public class ConfigSection {

	static HashMap<String, Save> saves = new HashMap<>();
	private static Map<String, Replacer> replacers = new HashMap<>();
	private static HashMap<Class<?>, String> lists = new HashMap<>();
	private static HashMap<String, String> packages = new HashMap<>();
	private static List<String> lineBreakers = new ArrayList<>();

	static {
		register(CMD.class);
		register(Click.class, "");
		register(Title.class, "");
		register(ReportCommand.class, "");
		register(AdminCommand.class, "");
		register(Archer.class, "");
		register(Cooldown.class, "");
		registerList(ItemStack.class, "item");
		registerList(DropItem.class, "drop");
		registerList(Location.class, "loc");
		registerList(Potions.class, "pot");
		registerList(Tag.class, "tag");
		register("Vector", new SaveVector());
		register("Location", new SaveLocation());
		register("ItemStack", new SaveItemStack());
		register("Config", new SaveConfig());
		newWrapper("<br>");
		newWrapper("\\n");
		newWrapper("$br");

		addReplacer("$player_group", new Replacer() {

			public Object getText(Player p) {
				return VaultAPI.getPermission().getPrimaryGroup(p);
			}
		});
		addReplacer("$player_prefix", new Replacer() {

			public Object getText(Player p) {
				return VaultAPI.getChat().getPlayerPrefix(p);
			}
		});
		addReplacer("$player_suffix", new Replacer() {

			public Object getText(Player p) {
				return VaultAPI.getChat().getPlayerPrefix(p);
			}
		});
		addReplacer("$group_prefix", new Replacer() {

			public Object getText(Player p) {
				return VaultAPI.getChat().getGroupPrefix("null",
						VaultAPI.getPermission().getPrimaryGroup(p));
			}
		});
		addReplacer("$group_suffix", new Replacer() {

			public Object getText(Player p) {
				return VaultAPI.getChat().getGroupSuffix("null",
						VaultAPI.getPermission().getPrimaryGroup(p));
			}
		});
		addReplacer("$players_online", new Replacer() {

			public Object getText(Player p) {
				return API.getPlayers().size();
			}
		});
		addReplacer("$player_world", new Replacer() {

			public Object getText(Player p) {
				return p.getWorld().getName();
			}
		});
		addReplacer("$player_displayname", new Replacer() {

			public Object getText(Player p) {
				return p.getDisplayName();
			}
		});
		addReplacer("$player_name", new Replacer() {

			public Object getText(Player p) {
				return p.getName();
			}
		});
		addReplacer("$player_health", new Replacer() {

			public Object getText(Player p) {
				return p.getHealth();
			}
		});
		addReplacer("$player_maxhealth", new Replacer() {

			public Object getText(Player p) {
				return p.getMaxHealth();
			}
		});
		addReplacer("$player_kills", new Replacer() {

			public Object getText(Player p) {
				return p.getStatistic(Statistic.PLAYER_KILLS);
			}
		});
		addReplacer("$player_deaths", new Replacer() {

			public Object getText(Player p) {
				return p.getStatistic(Statistic.DEATHS);
			}
		});
		addReplacer("$player_kdr", new Replacer() {

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
		addReplacer("$player_kill/death", new Replacer() {

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
		addReplacer("$player_money", new Replacer() {

			@SuppressWarnings("deprecation")
			@Override
			public Object getText(Player p) {
				if (VaultAPI.hasVault() && VaultAPI.hasEconomy()) {
					
					DecimalFormat decimal = new DecimalFormat("#,##0.00");
						return decimal.format(VaultAPI.getEconomy().getBalance(p));

				}
				return "0.00";
			}
		});
		addReplacer("$player_x", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getX();
			}
		});
		addReplacer("$player_y", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getY();
			}
		});
		addReplacer("$player_z", new Replacer() {

			public Object getText(Player p) {
				return p.getLocation().getZ();
			}
		});
		
		new Replacers();
		

	}
	public static void newWrapper(String wrap) {
		if (!lineBreakers.contains(wrap))
			lineBreakers.add(wrap);
		
	}
	public static String getComment(String line) {

		String[] split = line.split("#");
		if (split.length > 0)
			return line.replaceFirst(split[0] + "#", "").replaceFirst(" ", "");
		return line.replaceFirst("#", "").replaceFirst(" ", "");

	}

	public static List<String> getConfigLines(Path path) throws Exception {
		return Files.readAllLines(path);
	}

	public static String getKey(String line, String space) {
		line = line.replaceFirst(space, "");
		return line.split(":")[0];

	}

	public static String getList(String line) {
		String[] split = line.split("-");
		if (split.length > 0)
			return line.replaceFirst(split[0] + "-", "").replaceFirst(" ", "");
		return line.replaceFirst("-", "");
	}

	public static String getPath(String path) {
		if (path.startsWith("#")) {
			path.replaceFirst("#", "$");
		}
		if (path.startsWith("-")) {
			path.replaceFirst("-", "$");
		}
		if (path.contains(":")) {
			path.replace(":", "$");
		}
		return path;
	}

	public static String getSpace(int id) {
		String space = "";
		for (int i = 0; i < id; i++) {
			space += "  ";
		}
		return space;
	}

	public static int getTime(String line) {
		int value = 0;
		while (line.startsWith("  ")) {
			line = line.replaceFirst("  ", "");
			value++;
		}
		return value;
	}

	public static String getValue(String line, String space) {
		line = line.replaceFirst(space, "");
		if (line.endsWith(":")) {
			return "";
		}
		String[] split = line.split(":");
		String result = line.replaceFirst(split[0] + ":", "").replaceFirst(" ",
				"");
		return result;

	}
	public static String removeQuotes(String message) {
		if (message.startsWith("'")) {
			message = message.replaceFirst("'", "");
		}
		if (message.startsWith("\"")) {
			message = message.replaceFirst("\"", "");
		}
		if (message.endsWith("'")) {
			message = message.substring(0, message.length() - 1);
		}
		if (message.endsWith("\"")) {
			message = message.substring(0, message.length() - 1);
		}
		return message;
	}

	public static boolean iCSomment(String line) {
		return line.trim().startsWith("#");
	}

	public static boolean isList(String line) {
		return line.trim().startsWith("-");
	}

	public static boolean isCS(String line) {
		return !isList(line) & !iCSomment(line) & line.contains(":");
	}

	public static boolean register(Class<?> claz) {
		return register(claz, "");
	}

	public static boolean register(Class<?> claz, String cutName) {
		String name = claz.getPackage().getName();
		if (packages.containsKey(name))
			return false;
		packages.put(name, cutName);
		return true;
	}

	public static boolean register(String name, Save save) {
		if (saves.containsKey(name))
			return false;
		saves.put(name, save);
		return true;
	}

	public static boolean registerList(Class<?> type, String prefix) {
		if (lists.containsKey(type))
			return false;
		lists.put(type, prefix);
		return true;
	}

	public static boolean registerPackage(String name, String cutName) {
		if (packages.containsKey(name))
			return false;
		packages.put(name, cutName);
		return true;
	}

	public static void setConfigLines(Path path, List<String> lines)
			throws Exception {
		Files.write(path, lines);
	}

	int lineSpaces;

	Object object;

	ConfigSection father;

	String key;

	Map<String, ConfigSection> sections = new LinkedHashMap<>();

	List<Object> list = new ArrayList<>();

	List<String> comments = new ArrayList<>();

	public ConfigSection(ConfigSection father, String key, Object value) {
		this(key, value);
		this.father = father;
		father.sections.put(key, this);

	}

	public ConfigSection(String key, Object value) {
		this.object = value;
		this.key = key;
	}

	public ConfigSection add(String path, Object value, String... comments) {
		ConfigSection sec = getSection(path);
		if (!contains(path)) {
			set(path, value);
		}
		if (sec.comments.isEmpty())
			sec.setComments(comments);
		return sec;
	}

	public boolean contains(String path) {
		ConfigSection sec = getSection(path);
		return sec.object != null & sec.object != "";
	}

	public Object get() {
		return object;
	}

	public Object get(String path) {
		return getSection(path).getValue();
	}

	public boolean getBoolean() {
		return ConfigSection.toBoolean(object);
	}

	public boolean getBoolean(String path) {
		return getSection(path).getBoolean();
	}

	public Double getDouble() {
		return ConfigSection.toDouble(object);
	}

	public Double getDouble(String path) {
		return getSection(path).getDouble();
	}

	public Float getFloat() {
		return ConfigSection.toFloat(object);
	}

	public Float getFloat(String path) {
		return getSection(path).getFloat();
	}

	public int getIndent() {
		return lineSpaces;
	}

	public Integer getInt() {
		return ConfigSection.toInt(object);
	}

	public Integer getInt(String path) {
		return getSection(path).getInt();
	}

	public List<Integer> getIntList() {
		ArrayList<Integer> list = new ArrayList<>();
		for (Object item : getList()) {
			list.add(ConfigSection.toInt(item));
		}
		return list;
	}

	public ItemStack getItem() {
		return (ItemStack) getValue();
	}

	public ItemStack getItem(String path) {
		return getSection(path).getItem();
	}

	public String getKey() {
		return key;
	}

	public Set<String> getKeys() {
		return sections.keySet();
	}

	public Set<String> getKeys(String path) {
		return getSection(path).getKeys();
	}

	public List<Object> getList() {
		return list;
	}

	public Location getLocation() {
		return (Location) getValue();
	}

	public Location getLocation(String path) {
		return getSection(path).getLocation();
	}

	public Long getLong() {
		return ConfigSection.toLong(object);
	}

	public Long getLong(String path) {
		return getSection(path).getLong();
	}

	public String getMessage() {

		return ConfigSection.toChatMessage(getString());
	}

	public ArrayList<String> getMessages() {
		ArrayList<String> list = new ArrayList<>();
		for (String text : getStringList()) {
			list.add(ConfigSection.toChatMessage(text));
		}
		return list;
	}

	public List<String> getMessages(String path) {
		return getSection(path).getMessages();
	}

	public Map<String, ConfigSection> getCS() {
		return sections;
	}

	public ConfigSection getSection(String path) {
		path = getPath(path);
		if (path.contains(".")) {
			String[] split = path.replace(".", ",").split(",");
			String restPath = path.replaceFirst(split[0] + ".", "");
			return getSection(split[0]).getSection(restPath);
		} else {
			if (path.isEmpty())
				return null;
			if (sections.containsKey(path)) {
				return sections.get(path);
			}
			return new ConfigSection(this, path, "");
		}
	}

	public Set<Entry<String, ConfigSection>> getSet() {
		return sections.entrySet();
	}

	public Sounds getSound() {
		return (Sounds) getValue();
	}

	public Sounds getSound(String path) {
		return getSection(path).getSound();
	}

	public String getString() {
		return getWraps(removeQuotes(ConfigSection.toString(object)));
	}

	public String getString(String path) {
		return getSection(path).getString();
	}

	public List<String> getStringList() {
		ArrayList<String> list = new ArrayList<>();
		for (Object item : getList()) {
			list.add(removeQuotes(ConfigSection.toString(item)));
		}
		return list;
	}

	public List<String> getStringList(String path) {
		return getSection(path).getStringList();
	}

	public Object getValue() {
		if (getString().contains("!")) {
			String[] split = getString().split("!");
			String name = split[1];
			if (saves.containsKey(name)) {
				return saves.get(name).get(this);
			} else {
				try {
					Class<?> claz = null;
					Save instance = null;
					if (name.contains(".")) {
						try {
							claz = RexAPI.get(name);
						} catch (Exception ex) {
							return null;
						}
					} else {
						for (Entry<String, String> pack : packages.entrySet()) {
							try {
								claz = RexAPI.get(pack.getKey() + "."
										+ name.replace(pack.getValue(), ""));
								break;
							} catch (Exception ex) {
							}
						}

					}
					instance = (Save) RexAPI.getNew(claz);
					while (Save.class.isAssignableFrom(claz)) {
						update((Save) instance, claz);
						claz = claz.getSuperclass();

					}
					return instance;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
		return get();

	}

	public Collection<ConfigSection> getValues() {
		return sections.values();
	}

	public Collection<ConfigSection> getValues(String path) {
		return getSection(path).getValues();
	}

	public String message(String path) {
		return getSection(path).getMessage();
	}

	void reload(Config config) {
		int spaceId = 0;
		ConfigSection path = this;
		boolean headerSeted = false;
		ArrayList<String> texts = new ArrayList<>();
		for (String line : config.lines) {
			String space = getSpace(spaceId);
			if (!headerSeted & line.isEmpty()) {
				headerSeted = true;
			}
			if (!headerSeted & iCSomment(line)) {
				comments.add(getComment(line));
			}
			if (headerSeted) {
				if (isList(line)) {
					path.list.add(getList(line));
				} else if (iCSomment(line)) {
					texts.add(getComment(line));
				} else if (isCS(line)) {
					if (!line.startsWith("  ")) {
						spaceId = 0;
						path = this;
					} else if (!line.startsWith(space)) {
						int time = getTime(line);
						while (time < spaceId) {
							path = path.father;
							spaceId--;
						}
					}
					space = getSpace(spaceId);
					path = path.getSection(getKey(line, space));
					path.set(getValue(line, space));
					path.list.clear();
					path.comments.addAll(texts);
					texts.clear();
					spaceId++;

				}
			}
		}

	}

	/**
	 * FUNCIONA TAMBEM deve se usar root.reload(config,0,0);
	 */
	@SuppressWarnings("unused")
	private void reload(Config config, int spaceId, int lineId) {
		String space = getSpace(spaceId);
		if (lineId == 0) {
			for (String line : config.lines) {
				if (line.isEmpty()) {
					lineId++;
					break;
				}
				if (iCSomment(line)) {
					comments.add(getComment(line));
				}
				lineId++;
			}
		}
		ArrayList<String> texts = new ArrayList<>();
		ConfigSection current = this;

		for (int i = lineId; i < config.lines.size(); i++) {

			String line = config.lines.get(i);

			if (line.isEmpty()) {
				texts.clear();
			} else if (iCSomment(line)) {
				texts.add(getComment(line));
			} else if (!line.startsWith(space)) {
				break;
			} else if (line.startsWith(space + getSpace(1))) {
				continue;
			} else if (isList(line)) {
				current.list.add(getList(line));
			} else if (isCS(line)) {
				String key = getKey(line, space);
				Object value = getValue(line, space);
				current = getSection(key).set(value);;
				for (String text : texts) {
					current.comments.add(text);
				}
				texts.clear();
				current.reload(config, spaceId + 1, i + 1);
			}

		}
	}

	public ConfigSection remove(String path) {
		ConfigSection sec = getSection(path);
		sec.father.sections.remove(sec.key);
		return this;
	}

	void save(Config config, int spaceId) {
		if (spaceId == -1) {
			for (String comment : comments) {
				config.lines.add("# " + comment);
			}
			config.lines.add("");
		} else {
			String space = getSpace(spaceId);
			for (String comment : comments) {
				config.lines.add(space + "# " + comment);
			}
			config.lines.add(space + key + ": " + object);
			for (Object text : list) {
				config.lines.add(space + "- " + text);
			}

		}
		for (ConfigSection CS : sections.values()) {
			CS.save(config, spaceId + 1);
			for (int i = 0; i < lineSpaces; i++) {
				config.lines.add("");
			}
		}

	}

	private void save(Save instance, Class<?> clazz) {
		try {

			for (Field field : clazz.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()))
					continue;
				if (Modifier.isTransient(field.getModifiers()))
					continue;
				field.setAccessible(true);
				Object result = field.get(instance);

				if (result != null) {
					if (result == instance)
						continue;
					set(field.getName(), result);
				}
			}
			Config.save(this, instance);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ConfigSection set(Object value) {
		this.object = value;
		return this;
	}

	public ConfigSection set(String path, Object value, String... comments) {
		ConfigSection sec = getSection(path);
		String name = value.getClass().getSimpleName();
		if (value instanceof Collection) {
			sec.list.clear();
			@SuppressWarnings("unchecked")
			Collection<Object> contents = (Collection<Object>) value;
			int id = 1;
			for (Object content : contents) {
				boolean find = false;
				for (Entry<Class<?>, String> claz : lists.entrySet()) {
					if (claz.getKey().isAssignableFrom(content.getClass())) {
						find = true;
						sec.set(claz.getValue() + id, content);
					}
				}
				if (!find) {
					sec.list.add(content);
				}
				id++;
			}
		} else if (saves.containsKey(name)) {
			sec.set("!" + name);
			Save save = (Save) saves.get(name);
			save.save(sec, value);

		} else if (value instanceof Save) {
			Save save = (Save) value;
			Class<?> clazz = value.getClass();
			if (clazz.isAnonymousClass()) {
				clazz = clazz.getSuperclass();
				if (Modifier.isAbstract(clazz.getModifiers())) {
					clazz = clazz.getSuperclass();
				}
			}
			String pack = clazz.getPackage().getName();
			name = clazz.getName();
			if (packages.containsKey(pack)) {
				name = packages.get(pack) + clazz.getSimpleName();
			}
			sec.set("!" + name);
			while (Save.class.isAssignableFrom(clazz)) {
				sec.save(save, clazz);
				clazz = clazz.getSuperclass();
			}

		}

		else {
			sec.set(value);
		}

		sec.setComments(comments);
		return sec;
	}

	public ConfigSection setComments(ArrayList<String> list) {
		this.comments = list;
		return this;
	}

	public ConfigSection setComments(String... comments) {
		if (comments != null & comments.length > 0) {
			this.comments.clear();
			for (Object value : comments) {
				this.comments.add(ConfigSection.toString(value));
			}
		}
		return this;
	}

	public ConfigSection setIndent(int amount) {
		lineSpaces = amount;
		return this;
	}

	public ConfigSection setList(ArrayList<Object> list) {
		this.list = list;
		return this;
	}

	public ConfigSection setList(Object... values) {
		if (values != null & values.length > 0) {
			list.clear();
			for (Object value : values) {
				list.add(value);
			}

		}
		return this;
	}

	private void update(Save instance, Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			try {
				if (Modifier.isStatic(field.getModifiers()))
					continue;
				if (Modifier.isTransient(field.getModifiers()))
					continue;
				field.setAccessible(true);
				if (field.getType().isEnum()) {
					try {
						field.set(instance, RexAPI.getValue(field.getType(),
								getString(field.getName())));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					if (contains(field.getName())) {
						try {
							String name = ConfigSection
									.toTitle(field.getType().getSimpleName());
							Object result = RexAPI.getResult(ConfigSection.class,
									"to" + name,
									RexAPI.getParameters(Object.class),
									getString(field.getName()));
							field.set(instance, result);
						} catch (Exception ex) {
							try {
								Object result = get(field.getName());
								if (result.getClass().equals(field.getType())) {
									field.set(instance, result);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}

			} catch (Exception ex) {
			}
		}
		try {
			Config.get(this, instance);
		} catch (Exception ex) {
		}

	}

	private static Logger logger = Logger.getLogger("EduardAPI");
	public static Logger getLog() {
		return logger;
	}

	public static String getWraps(String value) {
		for (String wrap : lineBreakers) {
			value = value.replaceAll(wrap, "\n");
		}
		return value;
	}
	public static void chat(CommandSender sender, Object... objects) {
		sender.sendMessage(API.SERVER_TAG + getMessage(objects));
	}
	public static void chatMessage(CommandSender sender, Object... objects) {
		sender.sendMessage(getMessage(objects));
	}
	public static String getMessage(Object... objects) {
		String message = getText(objects);
		message = message.replace("$>", Chats.getArrowRight());
		return ConfigSection.getWraps(message);
	}

	public static void all(Object... objects) {

		broadcast(objects);
		console(objects);
	}
	public static void broadcastMessage(Object... objects) {

		for (Player p : API.getPlayers()) {
			chatMessage(p, objects);
		}
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
	public static void consoleMessage(Object... objects) {
		chatMessage(Bukkit.getConsoleSender(), objects);
	}

	public static void sendMessage(Collection<Player> players,
			Object... objects) {
		for (Player player : players) {
			chat(player, objects);
		}

	}

	public static String getText(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(object);

		}
		return builder.toString();
	}
	public static List<String> toLines(String text, int size) {

		List<String> lista = new ArrayList<>();

		String x = text;

		int id = 1;
		while (x.length() >= size) {
			String cut = x.substring(0, size);
			x = text.substring(id * size);
			id++;
			lista.add(cut);
		}
		lista.add(x);
		return lista;

	}

	public static String getReplacers(String text, Player player) {
		for (Entry<String, Replacer> value : replacers.entrySet()) {
			if (text.contains(value.getKey())) {
				try {
					text = text.replace(value.getKey(),
							"" + value.getValue().getText(player));
					
				} catch (Exception e) {
					console(e.getMessage());
				}

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

	public static String[] toArray(Collection<String> list) {
		return list.toArray(new String[0]);
	}
	public static Player[] toPlayersArray(Collection<Player> players) {
		return players.toArray(new Player[players.size()]);
	}
	public static boolean startWith(String message, String text) {
		return message.toLowerCase().startsWith(text.toLowerCase());
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
	public static boolean contains(String message, String text) {
		return message.toLowerCase().contains(text.toLowerCase());
	}

	public static String getText(int init, String... args) {
		StringBuilder text = new StringBuilder();
		int id = 0;
		for (String arg : args) {
			if (id < init) {
				id++;
				continue;
			}
			text.append(" " + toChatMessage(arg));
			id++;
		}
		return text.toString();
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

	public static String toString(Object object) {

		return object == null ? "" : object.toString();
	}
	public static void sendActionBar(String message) {
		for (Player player : API.getPlayers()) {
			RexAPI.sendActionBar(player, message);
		}
	}
	public static void addReplacer(String key, Replacer value) {
		replacers.put(key, value);
	}
	public static Replacer getReplacer(String key) {
		return replacers.get(key);
	}
	public static String[] wordWrap(String rawString, int lineLength) {
		if (rawString == null) {
			return new String[]{""};
		}

		if ((rawString.length() <= lineLength) && (!(rawString.contains("\n")))) {
			return new String[]{rawString};
		}

		char[] rawChars = new StringBuilder().append(rawString).append(' ').toString().toCharArray();
		StringBuilder word = new StringBuilder();
		StringBuilder line = new StringBuilder();
		List lines = new LinkedList();
		int lineColorChars = 0;

		for (int i = 0; i < rawChars.length; ++i) {
			char c = rawChars[i];

			if (c == 167) {
				word.append(ChatColor.getByChar(rawChars[(i + 1)]));
				lineColorChars += 2;
				++i;
			} else if ((c == ' ') || (c == '\n')) {
				if ((line.length() == 0) && (word.length() > lineLength)) {
					for (String partialWord : word.toString()
							.split(new StringBuilder().append("(?<=\\G.{").append(lineLength).append("})").toString()))
						lines.add(partialWord);
				} else if (line.length() + word.length() - lineColorChars == lineLength) {
					line.append(word);
					lines.add(line.toString());
					line = new StringBuilder();
					lineColorChars = 0;
				} else if (line.length() + 1 + word.length() - lineColorChars > lineLength) {
					for (String partialWord : word.toString().split(
							new StringBuilder().append("(?<=\\G.{").append(lineLength).append("})").toString())) {
						lines.add(line.toString());
						line = new StringBuilder(partialWord);
					}
					lineColorChars = 0;
				} else {
					if (line.length() > 0) {
						line.append(' ');
					}
					line.append(word);
				}
				word = new StringBuilder();

				if (c == '\n') {
					lines.add(line.toString());
					line = new StringBuilder();
				}
			} else {
				word.append(c);
			}
		}

		if (line.length() > 0) {
			lines.add(line.toString());
		}

		if ((((String) lines.get(0)).length() == 0) || (((String) lines.get(0)).charAt(0) != 167)) {
			lines.set(0, new StringBuilder().append(ChatColor.WHITE).append((String) lines.get(0)).toString());
		}
		for (int i = 1; i < lines.size(); ++i) {
			String pLine = (String) lines.get(i - 1);
			String subLine = (String) lines.get(i);

			char color = pLine.charAt(pLine.lastIndexOf(167) + 1);
			if ((subLine.length() == 0) || (subLine.charAt(0) != 167)) {
				lines.set(i, new StringBuilder().append(ChatColor.getByChar(color)).append(subLine).toString());
			}
		}

		return ((String[]) lines.toArray(new String[lines.size()]));
	}

}
