
package net.eduard.template;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.template.command.SkywarsCommand;
import net.eduard.template.kits.TeleportBowKit;

public class Main extends JavaPlugin implements Listener {
	
	
	public static Main plugin;
	public static FileConfiguration config;

	@Override
	public void onEnable() {
		plugin = this;
		config = plugin.getConfig();
		getCommand("skywars").setExecutor(new SkywarsCommand());
		Bukkit.getPluginManager().registerEvents(this, this);
		registerKits();
	}
	public void registerKits(){
		new TeleportBowKit();
	}

	@Override
	public void onDisable() {

	}

}
