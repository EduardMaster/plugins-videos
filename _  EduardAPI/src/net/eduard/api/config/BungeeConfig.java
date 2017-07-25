package net.eduard.api.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeConfig {

	private Plugin plugin;
	private String name;
	private File file;
	private Configuration config;

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}

	public Configuration getConfig() {
		return config;
	}

	public BungeeConfig(String name, Plugin plugin) {
		file = new File(plugin.getDataFolder(), name);
		this.name = name;
		reloadConfig();
	}
	
	
	
	public void reloadConfig() {
		try {
			saveDefaultConfig();
			getProvider().load(file);
		} catch (Exception e) {
		}
	}
	public void saveDefaultConfig() {
		try {
			if (!plugin.getDataFolder().exists())
				plugin.getDataFolder().mkdir();

			if (!file.exists()) {
				InputStream in = plugin.getResourceAsStream(name);
				Files.copy(in, file.toPath());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private ConfigurationProvider getProvider() {
		return ConfigurationProvider.getProvider(YamlConfiguration.class);
	}



	public BungeeConfig saveConfig() {
		try {
			getProvider().save(config, file);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public String message(String path) {
		return ChatColor.translateAlternateColorCodes('&',
				getConfig().getString(path));
	}


	public void remove(String path) {
		config.set(path, null);
	}


	public void setItem(String path, ItemStack item) {
		setItem(create(path), item);
	}
	public ItemStack getItem(String path) {
		return getItem(getSection(path));
	}
	public void setLocation(String path, Location location) {
		setLocation(create(path), location);
	}
	public Location getLocation(String path) {
		return getLocation(getSection(path));
	}
	@SuppressWarnings("deprecation")
	public static void setItem(Configuration section, ItemStack item) {
		section.set("id", item.getTypeId());
		section.set("data", item.getDurability());
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName()) {
				section.set("name", meta.getDisplayName());
			}
			if (meta.hasLore()) {
				List<String> lines = new ArrayList<>();
				for (String line : meta.getLore()) {
					lines.add(line);
				}
				section.set("lore", lines);
			}
		}
		StringBuilder text = new StringBuilder();
		for (Entry<Enchantment, Integer> enchant : item.getEnchantments()
				.entrySet()) {
			text.append(
					enchant.getKey().getId() + "-" + enchant.getValue() + ",");
		}
		section.set("enchant", text.toString());
	}

	public static void setLocation(Configuration section,
			Location location) {
		section.set("world", location.getWorld().getName());
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());
	}

	public static Location getLocation(Configuration section) {
		World world = Bukkit.getWorld(section.getString("world"));
		double x = section.getDouble("x");
		double y = section.getDouble("y");
		double z = section.getDouble("z");
		float yaw = (float) section.getDouble("yaw");
		float pitch = (float) section.getDouble("pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static Location toLocation(String text) {
		String[] split = text.split(",");
		World world = Bukkit.getWorld(split[0]);
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		float yaw = Float.parseFloat(split[4]);
		float pitch = Float.parseFloat(split[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static String toChatMessage(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	public static String saveLocation(Location location) {
		StringBuilder text = new StringBuilder();
		text.append(location.getWorld().getName() + ",");
		text.append(location.getX() + ",");
		text.append(location.getY() + ",");
		text.append(location.getZ() + ",");
		text.append(location.getYaw() + ",");
		text.append(location.getPitch());
		return text.toString();
	}

	public  String toConfigMessage(String text) {
		return text.replace("§", "&");
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItem(Configuration section) {
		ItemStack item = new ItemStack(section.getInt("id"),
				section.getInt("data"));
		ItemMeta meta = item.getItemMeta();
		if (section.contains("name")) {
			meta.setDisplayName(toChatMessage(section.getString("name")));
		}
		if (section.contains("lore")) {
			List<String> lines = new ArrayList<>();
			for (String line : meta.getLore()) {
				lines.add(toChatMessage(line));
			}
		}
		if (section.contains("enchant")) {
			for (String value : section.getString("enchant").split(",")) {
				if (value == null)
					continue;
				if (value.isEmpty())
					continue;
				if (value.contains("-")) {
					String[] split = value.split("-");
					item.addUnsafeEnchantment(
							Enchantment.getById(Integer.valueOf(split[0])),
							Integer.valueOf(split[1]));
				}
			}
		}
		return item;
	}

	public boolean delete() {
		return file.delete();
	}

	public boolean exists() {
		return file.exists();
	}


	public boolean contains(String path) {
		return config.contains(path);
	}

	public Configuration create(String path) {
		return config.getSection(path);
	}

	public Object get(String path) {
		return config.get(path);
	}

	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}


	public double getDouble(String path) {
		return config.getDouble(path);
	}

	public int getInt(String path) {
		return config.getInt(path);
	}

	public List<Integer> getIntegerList(String path) {
		return config.getIntList(path);
	}

	public ItemStack getItemStack(String path) {
		return getItemStack(path);
	}

	public Collection<String> getKeys() {
		return config.getKeys();
	}

	public List<?> getList(String path) {
		return config.getList(path);
	}

	public long getLong(String path) {
		return config.getLong(path);
	}

	public List<Long> getLongList(String path) {
		return config.getLongList(path);
	}


	public String getString(String path) {
		return config.getString(path);
	}

	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	public Configuration getSection(String path) {
		return config.getSection(path);
	}

	public void set(String path, Object value) {
		config.set(path, value);
	}

}