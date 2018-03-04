package net.eduard.live;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static SimpleConfig config;

	public void onEnable() {
		config = new SimpleConfig("config.yml", this);
		getCommand("tempban").setExecutor(new ComandoTempBan());
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
	}

}
