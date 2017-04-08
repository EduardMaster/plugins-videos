
package net.eduard.template;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.ConfigAPI;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvent;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static ConfigAPI config;
	public static ConfigAPI messages;

	public void onEnable() {
		plugin = this;
		config = new ConfigAPI("config.yml");
		messages = new ConfigAPI("messages.yml");
		new TemplateCommand();
		new TemplateEvent().register(this);
	}
	public void save() {
		config.saveDefault();
		config.saveDefault();
	}

	public void onDisable() {

	}

}
