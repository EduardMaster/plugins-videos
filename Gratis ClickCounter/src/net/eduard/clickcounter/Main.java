
package net.eduard.clickcounter;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.clickcounter.command.TestClickCommand;
import net.eduard.clickcounter.event.CPSCounter;
import net.eduard.eduardapi.EduardPlugin;

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
		new TestClickCommand().register();
		new CPSCounter().register(this);
	}

	@Override
	public void onDisable() {
	}

}
