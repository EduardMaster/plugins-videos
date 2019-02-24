
package net.eduard.usando.eduapi;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.server.EduardPlugin;
import net.eduard.usando.eduapi.command.ComandoExemplo;

public class Main extends EduardPlugin {
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	@Override
	public void onEnable() {
		plugin = this;
		new ComandoExemplo().register();
	}

	@Override
	public void onDisable() {
	}

}
