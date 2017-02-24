
package net.eduard.template;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvent;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static Config config;

	public void onEnable() {

		plugin = this;
		config = new Config(this);
		new TemplateEvent();
		new TemplateCommand();
	}

	public void onDisable() {

	}
}
