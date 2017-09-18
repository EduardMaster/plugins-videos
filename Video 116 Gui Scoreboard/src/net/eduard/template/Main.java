
package net.eduard.template;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
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
		API.event(new Teste(this));
	}

	@Override
	public void onDisable() {

	}
	public void commands(){
		new TemplateCommand();
	}
	

}
