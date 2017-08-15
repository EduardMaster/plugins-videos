package net.eduard.api.manager;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;

public abstract class EduardPlugin extends JavaPlugin {

	protected Manager time;

	protected Config messages;

	protected Config config;


	protected void configs() {
	}
	protected void commands() {
	}
	protected void events() {
	}
	protected void setup() {
	}
	public void save() {

	}
	public void reload() {
	}

	@Override
	public void onEnable() {

		config = new Config(this, "config.yml");
		messages = new Config(this, "messages.yml");
		time = new Manager();
		configs();
		setup();
		commands();
		events();
	}
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	public String message(String path) {
		return messages.message(path);
	}

	@Override
	public void onDisable() {
	}
	public Manager getTime() {
		return time;
	}
	public Config getMessages() {
		return messages;
	}
	public Config getConfigs() {
		return config;
	}
}
