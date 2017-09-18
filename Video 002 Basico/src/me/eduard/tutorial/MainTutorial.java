
package me.eduard.tutorial;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MainTutorial extends JavaPlugin {

	@Override
	public void onDisable() {

		getLogger().info("Esse plugin foi desabilitado!");
		// ou
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Esse plugin foi desabilitado!");
	}

	@Override
	public void onEnable() {

		getLogger().info("Esse plugin foi habilitado!");
		// ou
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Esse plugin foi habilitado!");

	}
}
