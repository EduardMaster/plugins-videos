package net.eduard.api.manager;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.config.Configs;

public abstract class EduardPlugin extends JavaPlugin {

	protected Manager time;

	protected Configs messages;

	protected Config config;

	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	public String message(String path) {
		return messages.message(path);
	}

	protected void configs() {

	}
	protected void commands() {

	}
	protected void events() {

	}
	protected void setup() {

	}
	public void onEnable() {
		
		config = new Config(this,"config.yml");
		config.saveConfig();
		messages = new Configs("messages.yml", this);
		messages.saveDefaultConfig();
		configs();
		time = new Manager();
		setup();
		commands();
		events();

	}
	public void onDisable() {
	}

	public Manager getTime() {
		return time;
	}
	public Configs getMessages() {
		return messages;
	}
	public Config getConfigs() {
		return config;
	}

}
