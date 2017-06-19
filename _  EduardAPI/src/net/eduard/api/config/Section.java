package net.eduard.api.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
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
import net.eduard.api.game.Potions;
import net.eduard.api.game.Sounds;
import net.eduard.api.game.Tag;
import net.eduard.api.game.Title;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.DropItem;
import net.eduard.api.kits.Archer;
import net.eduard.api.manager.Cooldown;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.util.Cs;

public class Section {
	static HashMap<String, Save> saves = new HashMap<>();

	private static HashMap<Class<?>, String> lists = new HashMap<>();
	private static HashMap<String, String> packages = new HashMap<>();

	static {
		register(Click.class, "");
		register(Title.class, "");
		register(ReportCommand.class,"");
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

	public static boolean isComment(String line) {
		return line.trim().startsWith("#");
	}

	public static boolean isList(String line) {
		return line.trim().startsWith("-");
	}

	public static boolean isSection(String line) {
		return !isList(line) & !isComment(line) & line.contains(":");
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

	Section father;

	String key;

	Map<String, Section> section = new LinkedHashMap<>();

	List<Object> list = new ArrayList<>();

	List<String> comments = new ArrayList<>();

	public Section(Section father, String key, Object value) {
		this(key, value);
		this.father = father;
		father.section.put(key, this);

	}

	public Section(String key, Object value) {
		this.object = value;
		this.key = key;
	}

	public Section add(String path, Object value, String... comments) {
		Section sec = getSection(path);
		if (!contains(path)) {
			set(path, value);
		}
		if (sec.comments.isEmpty())
			sec.setComments(comments);
		return sec;
	}

	public boolean contains(String path) {
		Section sec = getSection(path);
		return sec.object != null & sec.object != "";
	}

	public Object get() {
		return object;
	}

	public Object get(String path) {
		return getSection(path).getValue();
	}

	public boolean getBoolean() {
		return Cs.toBoolean(object);
	}

	public boolean getBoolean(String path) {
		return getSection(path).getBoolean();
	}

	public Double getDouble() {
		return Cs.toDouble(object);
	}

	public Double getDouble(String path) {
		return getSection(path).getDouble();
	}

	public Float getFloat() {
		return Cs.toFloat(object);
	}

	public Float getFloat(String path) {
		return getSection(path).getFloat();
	}

	public int getIndent() {
		return lineSpaces;
	}

	public Integer getInt() {
		return Cs.toInt(object);
	}

	public Integer getInt(String path) {
		return getSection(path).getInt();
	}

	public List<Integer> getIntList() {
		ArrayList<Integer> list = new ArrayList<>();
		for (Object item : getList()) {
			list.add(Cs.toInt(item));
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
		return section.keySet();
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
		return Cs.toLong(object);
	}

	public Long getLong(String path) {
		return getSection(path).getLong();
	}

	public String getMessage() {

		return Cs.toChatMessage(getString());
	}

	public ArrayList<String> getMessages() {
		ArrayList<String> list = new ArrayList<>();
		for (String text : getStringList()) {
			list.add(Cs.toChatMessage(text));
		}
		return list;
	}

	public List<String> getMessages(String path) {
		return getSection(path).getMessages();
	}

	public Map<String, Section> getSection() {
		return section;
	}

	public Section getSection(String path) {
		path = getPath(path);
		if (path.contains(".")) {
			String[] split = path.replace(".", ",").split(",");
			String restPath = path.replaceFirst(split[0] + ".", "");
			return getSection(split[0]).getSection(restPath);
		} else {
			if (path.isEmpty())
				return null;
			if (section.containsKey(path)) {
				return section.get(path);
			}
			return new Section(this, path, "");
		}
	}

	public Set<Entry<String, Section>> getSet() {
		return section.entrySet();
	}

	public Sounds getSound() {
		return (Sounds) getValue();
	}

	public Sounds getSound(String path) {
		return getSection(path).getSound();
	}

	public String getString() {
		return Cs.toString(object);
	}

	public String getString(String path) {
		return getSection(path).getString();
	}

	public List<String> getStringList() {
		ArrayList<String> list = new ArrayList<>();
		for (Object item : getList()) {
			list.add(Cs.toString(item));
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

	public Collection<Section> getValues() {
		return section.values();
	}

	public Collection<Section> getValues(String path) {
		return getSection(path).getValues();
	}

	public String message(String path) {
		return getSection(path).getMessage();
	}

	void reload(Config config) {
		int spaceId = 0;
		Section path = this;
		boolean headerSeted = false;
		ArrayList<String> texts = new ArrayList<>();
		for (String line : config.lines) {
			String space = getSpace(spaceId);
			if (!headerSeted & line.isEmpty()) {
				headerSeted = true;
			}
			if (!headerSeted & isComment(line)) {
				comments.add(getComment(line));
			}
			if (headerSeted) {
				if (isList(line)) {
					path.list.add(getList(line));
				} else if (isComment(line)) {
					texts.add(getComment(line));
				} else if (isSection(line)) {
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
				if (isComment(line)) {
					comments.add(getComment(line));
				}
				lineId++;
			}
		}
		ArrayList<String> texts = new ArrayList<>();
		Section current = this;

		for (int i = lineId; i < config.lines.size(); i++) {

			String line = config.lines.get(i);

			if (line.isEmpty()) {
				texts.clear();
			} else if (isComment(line)) {
				texts.add(getComment(line));
			} else if (!line.startsWith(space)) {
				break;
			} else if (line.startsWith(space + getSpace(1))) {
				continue;
			} else if (isList(line)) {
				current.list.add(getList(line));
			} else if (isSection(line)) {
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

	public Section remove(String path) {
		Section sec = getSection(path);
		sec.father.section.remove(sec.key);
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
		for (Section section : section.values()) {
			section.save(config, spaceId + 1);
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

	public Section set(Object value) {
		this.object = value;
		return this;
	}

	public Section set(String path, Object value, String... comments) {
		Section sec = getSection(path);
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

	public Section setComments(ArrayList<String> list) {
		this.comments = list;
		return this;
	}

	public Section setComments(String... comments) {
		if (comments != null & comments.length > 0) {
			this.comments.clear();
			for (Object value : comments) {
				this.comments.add(Cs.toString(value));
			}
		}
		return this;
	}

	public Section setIndent(int amount) {
		lineSpaces = amount;
		return this;
	}

	public Section setList(ArrayList<Object> list) {
		this.list = list;
		return this;
	}

	public Section setList(Object... values) {
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
							String name = Cs
									.toTitle(field.getType().getSimpleName());
							Object result = RexAPI.getResult(Cs.class,
									"to" + name,
									RexAPI.getParameters(Object.class),
									getString(field.getName()));
							field.set(instance, result);
						} catch (Exception ex) {
							try {
								Object result = get(field.getName());
								if (result.getClass().equals(field.getType())){
									field.set(instance,result);
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
