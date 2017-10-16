
package net.eduard.template;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.setup.Mine;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.Teste;

public class Main extends JavaPlugin {
	private static Main plugin;
	private static Config config;
	@Override
	public void onEnable() {
		plugin = this;
		config = new Config(this);
		config.saveConfig();
		Mine.event(new Teste(this),this);
	}

	@Override
	public void onDisable() {

	}
	public void commands(){
		new TemplateCommand();
	}
	

}
