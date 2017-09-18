package net.eduard.hg;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.hg.apis.ConfigAPI;

public class Main extends JavaPlugin {

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	private static Main plugin;
	private HG hg;
	private ConfigAPI config;
	public String message(String path) {
		return config.message(path)
				.replace("$status", "" + hg.getPlayers().size())
				.replace("$max", "" + hg.getMaxPlayersAmount())
				.replace("$time", "" + hg.getTime());
	}
	public ConfigAPI config() {
		return config;
	}
	public static Main getInstance() {
		return plugin;
	}
	@Override
	public void onEnable() {
		plugin = this;
		hg = new HG(this);
		config = new ConfigAPI("config.yml", this);
		config.add("join", "&aVoce entrou no torneio de HG ($status/$max)");
		config.add("already started",
				"&cO torneio ja começou para assistir compre vip!");
		config.add("starting", "&aO tornerno vai começar em $time segundos!");
		config.saveConfig();
		Bukkit.getScheduler().runTaskTimer(this, hg, 20, 20);
		Bukkit.getPluginManager().registerEvents(hg, plugin);
		Bukkit.getPluginManager().registerEvents(new Eventos(), plugin);
	}
	public HG getHg() {
		return hg;
	}
	public void setHg(HG hg) {
		this.hg = hg;
	}

}
