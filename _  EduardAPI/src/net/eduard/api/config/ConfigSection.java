package net.eduard.api.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.command.ReportCommand;
import net.eduard.api.command.admin.AdminCommand;
import net.eduard.api.command.staff.CheckIpCommand;
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
import net.eduard.api.player.Cooldown;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.RexAPI;
import net.eduard.api.util.Save;

public class ConfigSection {

	static HashMap<String, Save> saves = new HashMap<>();

	private static HashMap<Class<?>, String> lists = new HashMap<>();
	private static HashMap<String, String> packages = new HashMap<>();

	static {
		register(CMD.class);
		register(Click.class, "");
		register(Title.class, "");
		register(ReportCommand.class, "");
		register(AdminCommand.class, "");
		register(CheckIpCommand.class, "");
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

		new Replacers();

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
		return ExtraAPI.toBoolean(object);
	}

	public boolean getBoolean(String path) {
		return getSection(path).getBoolean();
	}

	public Double getDouble() {
		return ExtraAPI.toDouble(object);
	}

	public Double getDouble(String path) {
		return getSection(path).getDouble();
	}

	public Float getFloat() {
		return ExtraAPI.toFloat(object);
	}

	public Float getFloat(String path) {
		return getSection(path).getFloat();
	}

	public int getIndent() {
		return lineSpaces;
	}

	public Integer getInt() {
		return ExtraAPI.toInt(object);
	}

	public Integer getInt(String path) {
		return getSection(path).getInt();
	}

	public List<Integer> getIntList() {
		ArrayList<Integer> list = new ArrayList<>();
		for (Object item : getList()) {
			list.add(ExtraAPI.toInt(item));
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
		return ExtraAPI.toLong(object);
	}

	public Long getLong(String path) {
		return getSection(path).getLong();
	}

	public String getMessage() {

		return ExtraAPI.toChatMessage(getString());
	}

	public ArrayList<String> getMessages() {
		ArrayList<String> list = new ArrayList<>();
		for (String text : getStringList()) {
			list.add(ExtraAPI.toChatMessage(text));
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
		path = ExtraAPI.getPath(path);
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
		return ExtraAPI
				.getWraps(ExtraAPI.removeQuotes(ExtraAPI.toString(object)));
	}

	public String getString(String path) {
		return getSection(path).getString();
	}

	public List<String> getStringList() {
		ArrayList<String> list = new ArrayList<>();
		for (Object item : getList()) {
			list.add(ExtraAPI.removeQuotes(ExtraAPI.toString(item)));
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
						update(instance, claz);
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
			String space = ExtraAPI.getSpace(spaceId);
			if (!headerSeted & line.isEmpty()) {
				headerSeted = true;
			}
			if (!headerSeted & ExtraAPI.isComment(line)) {
				comments.add(ExtraAPI.getComment(line));
			}
			if (headerSeted) {
				if (ExtraAPI.isList(line)) {
					path.list.add(ExtraAPI.getList(line));
				} else if (ExtraAPI.isComment(line)) {
					texts.add(ExtraAPI.getComment(line));
				} else if (ExtraAPI.isSection(line)) {
					if (!line.startsWith("  ")) {
						spaceId = 0;
						path = this;
					} else if (!line.startsWith(space)) {
						int time = ExtraAPI.getTime(line);
						while (time < spaceId) {
							path = path.father;
							spaceId--;
						}
					}
					space = ExtraAPI.getSpace(spaceId);
					path = path.getSection(ExtraAPI.getKey(line, space));
					path.set(ExtraAPI.getValue(line, space));
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
		String space = ExtraAPI.getSpace(spaceId);
		if (lineId == 0) {
			for (String line : config.lines) {
				if (line.isEmpty()) {
					lineId++;
					break;
				}
				if (ExtraAPI.isComment(line)) {
					comments.add(ExtraAPI.getComment(line));
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
			} else if (ExtraAPI.isComment(line)) {
				texts.add(ExtraAPI.getComment(line));
			} else if (!line.startsWith(space)) {
				break;
			} else if (line.startsWith(space + ExtraAPI.getSpace(1))) {
				continue;
			} else if (ExtraAPI.isList(line)) {
				current.list.add(ExtraAPI.getList(line));
			} else if (ExtraAPI.isSection(line)) {
				String key = ExtraAPI.getKey(line, space);
				Object value = ExtraAPI.getValue(line, space);
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
			String space = ExtraAPI.getSpace(spaceId);
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
			Save save = saves.get(name);
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
				this.comments.add(ExtraAPI.toString(value));
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
							String name = ExtraAPI
									.toTitle(field.getType().getSimpleName());
							Object result = RexAPI.getResult(ExtraAPI.class,
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

}
