package net.eduard.api.config;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.player.SoundEffect;

public class Config {

	public static void save(Section section, Save save) throws Exception {
		RexAPI.getResult(save, "save",
				RexAPI.getParameters(section, Object.class), section, save);
	}

	public static void get(Section section, Save save) throws Exception {
		RexAPI.getResult(save, "get", RexAPI.getParameters(section), section);
	}

	public final static List<Config> CONFIGS = new ArrayList<>();
	private Section root;
	private File file;
	private JavaPlugin plugin;
	private String name;

	List<String> lines;

	public Config() {
		this("config.yml");
	}

	public Config(JavaPlugin plugin) {
		this(plugin, "config.yml");
	}

	public Config(JavaPlugin plugin, String name) {
		this.name = name;
		this.plugin = plugin;
		boolean contains = false;
		for (Config config : CONFIGS) {
			if (config.equals(this)) {
				file = config.file;
				lines = config.lines;
				root = config.root;
				contains = true;
				break;
			}
		}
		if (!contains) {
			file = new File(plugin.getDataFolder(), name);
			root = new Section("", "{}");
			lines = new ArrayList<>();
			CONFIGS.add(this);
			root.lineSpaces = 1;
			this.root.father = root;
			reloadConfig();
		}

	}
	public Config reloadConfig() {
		try {
			if (!file.exists()) {
				if (name.endsWith("/") | name.endsWith("\\")
						| name.endsWith(File.separator)) {
					file.mkdirs();
				} else {
					file.getParentFile().mkdirs();
					try {
						file.createNewFile();

					} catch (Exception e) {
					}
					if (plugin.getResource(name) != null)
						plugin.saveResource(name, true);
				}

			}
			if (file.isFile()){
				try {
					if (Charset.isSupported("UTF-8")) {
						lines = Files.readAllLines(file.toPath(),
								StandardCharsets.UTF_8);
					} else {
						lines = Files.readAllLines(file.toPath(),
								Charset.defaultCharset());
					}
					root.reload(this);

				} catch (Exception ex) {
					lines = Files.readAllLines(file.toPath(),
							Charset.defaultCharset());
					root.reload(this);
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this;
	}
	public Config(String name) {
		this(API.getAPI(), name);
	}

	public Section add(String path, Object value, String... comments) {
		return root.add(path, value, comments);
	}

	public Config newConfig(String name) {
		return createConfig(name);
	}

	public Config addHeader(String... header) {
		if (header != null) {
			if (root.comments.isEmpty()) {
				for (String item : header) {
					root.comments.add(item);
				}
			}
		}
		return this;
	}

	public boolean contains(String path) {
		return root.contains(path);
	}

	public Config copy(Config config) {
		config.root.save(this, -1);
		reload();
		return this;
	}

	public Config createConfig(String name) {
		return new Config(getPlugin(), name);
	}

	public Config deleteConfig() {
		file.delete();
		return this;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Config other = (Config) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (plugin == null) {
			if (other.plugin != null)
				return false;
		} else if (!plugin.equals(other.plugin))
			return false;
		return true;
	}

	public boolean existsConfig() {
		return file.exists();
	}

	public Object get(String path) {
		return root.get(path);
	}

	public boolean getBoolean(String path) {
		return root.getBoolean(path);
	}

	public Section getConfig() {
		return root;
	}

	public List<Config> getConfigs() {
		ArrayList<Config> list = new ArrayList<>();
		if (Files.isDirectory(file.toPath())) {
			for (String subFile : file.list()) {
				list.add(createConfig(name + subFile));
			}
		}
		return list;
	}

	public List<String> getConfigsNames() {
		ArrayList<String> list = new ArrayList<>();
		for (Config config : getConfigs()) {
			list.add(config.getTitle());
		}
		return list;
	}

	public File getDataFolder() {
		return plugin.getDataFolder();
	}

	public Double getDouble(String path) {
		return root.getDouble(path);
	}

	public File getFile() {
		return file;
	}

	public Float getFloat(String path) {
		return root.getFloat(path);
	}

	public Integer getInt(String path) {
		return root.getInt(path);
	}

	public ItemStack getItem(String path) {
		return root.getItem(path);
	}

	public Set<String> getKeys() {
		return root.getKeys();
	}

	public Set<String> getKeys(String path) {
		return root.getKeys(path);
	}

	public Location getLocation(String path) {
		return root.getLocation(path);
	}

	public Long getLong(String path) {
		return root.getLong(path);
	}

	public List<String> getMessages(String path) {
		return root.getMessages(path);
	}

	public String getName() {
		return file.getName();
	}

	public String getNameComplete() {
		return name;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public Section getSection(String path) {
		return root.getSection(path);
	}

	public SoundEffect getSound(String path) {
		return root.getSound(path);
	}

	public String getString(String path) {
		return root.getString(path);
	}

	public List<String> getStringList(String path) {
		return root.getStringList(path);
	}

	public String getTitle() {
		return file.getName().replace(".yml", "");
	}

	public Collection<Section> getValues(String path) {
		return root.getValues(path);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((plugin == null) ? 0 : plugin.hashCode());
		return result;
	}

	public String message(String path) {
		return root.message(path);
	}

	private void reload() {
		root.reload(this);
	}

	public Config remove(String path) {
		root.remove(path);
		return this;
	}

	private void save() {
		root.save(this, -1);
	}

	public Config saveConfig() {
		lines.clear();
		save();
		try {
			if (Charset.isSupported("UTF-8")) {
				Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
			} else {
				Files.write(file.toPath(), lines, Charset.defaultCharset());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public Section set(String path, Object value, String... comments) {
		return root.set(path, value, comments);
	}

	public Config setHeader(String... header) {
		if (header != null) {
			root.comments.clear();
			for (String item : header) {
				root.comments.add(item);
			}
		}
		return this;
	}

	public Section setIndent(int amount) {
		return root.setIndent(amount);
	}

	public String toString() {
		return "Config [plugin=" + plugin + ", name=" + name + "]";
	}


}
