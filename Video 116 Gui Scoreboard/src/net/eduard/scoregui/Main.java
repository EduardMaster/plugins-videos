
package net.eduard.scoregui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.scoregui.event.ScoreboardTutorial;

public class Main extends JavaPlugin {
	private static Main plugin;
	@Override
	public void onEnable() {
		setPlugin(this);
		Bukkit.getPluginManager().registerEvents(new ScoreboardTutorial(this),this);
	}

	@Override
	public void onDisable() {

	}
	public void commands(){
	}

	public static Main getPlugin() {
		return plugin;
	}

	public static void setPlugin(Main plugin) {
		Main.plugin = plugin;
	}
	

}
