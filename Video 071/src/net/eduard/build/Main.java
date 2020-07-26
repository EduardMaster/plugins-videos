
package net.eduard.build;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main instance;

	public static ArrayList<Player> build = new ArrayList<>();

	@Override
	public void onEnable() {

		Main.instance = this;
		PluginCommand build_command = getCommand("build");
		build_command.setExecutor(new BuildCommand());
		build_command.setPermission("build.admin");
		build_command.setPermissionMessage("§cVoce nao pode usar este comando!");
		build_command
			.setDescription("§aEsse comando faz ativar e desativar o Modo Build!");
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BuildEvent(), this);
	}

}
