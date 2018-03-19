package net.eduard.live;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		getCommand("admin").setExecutor(new ComandoAdmin());
		getCommand("reiniciar").setExecutor(new ComandoReiniciar());
	}
}
