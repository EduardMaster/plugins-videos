
package net.eduard.hg;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.hg.manager.HG;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static Config config;

	public void onEnable() {
		plugin = this;
		config = new Config(this);
		new HG();
	}

	public void onDisable() {
	
	}

}
