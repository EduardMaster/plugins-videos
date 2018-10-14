package net.eduard.tutoriais;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Tutorial extends JavaPlugin {

	public void onEnable() {

		Bukkit.getConsoleSender().sendMessage("§aPlugin ativado");
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§cPlugin desativado");
	}

}
