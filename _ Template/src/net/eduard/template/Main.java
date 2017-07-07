
package net.eduard.template;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.manager.EduardPlugin;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvent;

public class Main extends EduardPlugin {
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	public void commands() {
	}
	public void events() {

	}
	public void init() {
		plugin = this;
		new TemplateEvent().register(this);
		new TemplateCommand().register();
	}

	public void onDisable() {
	}

}
