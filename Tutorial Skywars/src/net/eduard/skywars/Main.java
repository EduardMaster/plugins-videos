
package net.eduard.skywars;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.skywars.command.SkywarsCommand;
import net.eduard.skywars.event.TemplateEvent;
import net.eduard.skywars.kits.TeleportBowKit;
import net.eduard.skywars.manager.ConfigAPI;
import net.eduard.skywars.manager.EmptyWorld;

public class Main extends JavaPlugin implements Listener {
	
	
	public static Main plugin;
	public static ConfigAPI messages;
	public void onEnable() {
		plugin = this;
		messages = new ConfigAPI("messages.yml", this);
		messages.saveDefault();
		commands();
		events();
		registerKits();
	}
	public static String message(String path, String arena) {
		return messages.message(path).replace("$map", arena).replace("$tag",
				messages.message("server-tag"));
	}
	public void registerKits(){
		new TeleportBowKit();
	}

	
	public void commands(){
		getCommand("skywars").setExecutor(new SkywarsCommand());
	}
	public void events(){
		Bukkit.getPluginManager().registerEvents(new TemplateEvent(), this);
	}
	public static ItemStack newItem(String name, Material material,
			String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public void onDisable() {

	}
	public static World newEmptyWorld(String worldName) {
		World world = Bukkit.createWorld(
				new WorldCreator(worldName).generateStructures(false)
						.generator(new EmptyWorld()));
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
				p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()
						.setDirection(p.getLocation().getDirection()));
			}
			Bukkit.unloadWorld(world, false);
			
		}
		try {
			deleteFolder(world.getWorldFolder());
		} catch (Exception e) {
		}
	}
}
