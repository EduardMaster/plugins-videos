
package net.eduard.skywars;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.skywars.command.SkywarsCommand;
import net.eduard.skywars.event.TemplateEvent;
import net.eduard.skywars.kits.TeleportBowKit;
import net.eduard.skywars.util.ConfigAPI;

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
}
