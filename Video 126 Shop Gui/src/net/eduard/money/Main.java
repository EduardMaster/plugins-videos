
package net.eduard.money;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.server.EduardPlugin;
import net.eduard.money.command.TemplateCommand;
import net.eduard.money.event.TemplateEvent;

public class Main extends EduardPlugin {
	private static Main plugin;
	public static  Main getInstance() {
		return plugin;
	}
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	public void onEnable() {
		plugin = this;
		new TemplateEvent().register(this);
		new TemplateCommand().register();
	}
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
