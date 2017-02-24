
package net.eduard.template;

import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvent;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static FileConfiguration config;
	public void onEnable() {
		plugin=this;
		config = plugin.getConfig();
		saveDefaultConfig();
		
//		List<String> textos = new ArrayList<>();
//		textos.add("Eduard");
//		textos.add("Bruno");
//		textos.add("Leo");
//		getConfig().addDefault("lista", textos);
//		getConfig().options().copyDefaults(true);
	
		
		
		List<String> textos = getConfig().getStringList("lista");
		for (String texto:textos) {
			Bukkit.getConsoleSender().sendMessage("§c"+texto);
		}
		textos.add("Testando");
		getConfig().set("lista", textos);
		saveConfig();
		
//		config();
		events();
		commands();
		
	}
	public void onDisable() {
		
	}
	public void config() {
		config.addDefault("teste key", "teste value");
		config.options().copyDefaults(true);
		saveConfig();
	}
	public void events() {
		event(new TemplateEvent());
	}
	public void event(Listener event) {
		Bukkit.getPluginManager().registerEvents(event, plugin);
	}
	public void commands() {
		command("comando", new TemplateCommand());
	}
	public void command(String command,CommandExecutor cmd) {
		getCommand(command).setExecutor(cmd);
	}
	
}
