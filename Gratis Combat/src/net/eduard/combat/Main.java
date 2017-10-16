
package net.eduard.combat;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.EduardPlugin;
import net.eduard.combat.event.Combat;

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
		new Combat().register(this);
	}

	@Override
	public void onDisable() {
	}

}
