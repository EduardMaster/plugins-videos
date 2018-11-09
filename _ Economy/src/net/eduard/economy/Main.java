
package net.eduard.economy;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.server.EduardPlugin;
import net.eduard.economy.command.TemplateCommand;
import net.eduard.economy.event.TemplateEvents;

public class Main extends EduardPlugin  {
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	@Override
	public void onEnable() {
		plugin = this;
		reload();
		new TemplateEvents().register(this);
		new TemplateCommand().register();
	}
	public void save() {
		
	}
	public void reload() {
		
	}
	@Override
	public void onDisable() {
		save();
	}

}
