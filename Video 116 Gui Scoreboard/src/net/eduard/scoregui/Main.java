
package net.eduard.scoregui;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.config.Config;
import net.eduard.scoregui.command.TemplateCommand;
import net.eduard.scoregui.event.Teste;

public class Main extends JavaPlugin {
	private static Main plugin;
	private static Config config;
	@Override
	public void onEnable() {
		plugin = this;
		config = new Config(this);
		config.saveConfig();
		Mine.registerEvents(new Teste(this),this);
	}

	@Override
	public void onDisable() {

	}
	public void commands(){
		new TemplateCommand();
	}
	

}
