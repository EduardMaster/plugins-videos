
package net.eduard.template;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.MasterConfig;
import net.eduard.api.manager.Manager;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvent;

public class Main extends JavaPlugin {
	private static Main plugin;
	private static Config config;
	private static Config messages;
	private static Manager time;
	
	public static Main getInstance(){
		return plugin;
	}
	public static Config getMessages(){
		return messages;
	}
	public static Config getConfigs(){
		return config;
	}
	public static Manager getTime() {
		return time;
	}
	@Override
	public void onEnable() {
		plugin = this;
		config = new Config(this);
		config.saveConfig();
		messages = new Config("messages.yml");
		time = new Manager(this);
		API.event(new net.eduard.template.event.Teste());
		commands();
		events();
	}

	@Override
	public void onDisable() {

	}
	public void commands(){
		new TemplateCommand();
	}
	public void events(){
		new TemplateEvent().register(this);
	}
	

}
