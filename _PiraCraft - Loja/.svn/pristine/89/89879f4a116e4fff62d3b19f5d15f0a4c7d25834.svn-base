package loja;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.piracraft.api.PiraCraftAPI;

public class Main extends JavaPlugin {

	public static Plugin plugin;

	public static String server = null;

	public void onEnable() {
		plugin = this;

		Bukkit.getPluginManager().registerEvents(new Evento(), this);

		server = PiraCraftAPI.getIdNomeSala(Bukkit.getPort(),0)[1];
	}

}