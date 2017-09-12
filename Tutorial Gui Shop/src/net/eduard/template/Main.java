
package net.eduard.template;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.eduardapi.EduardPlugin;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvents;

public class Main extends EduardPlugin {
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	@Override
	public void onEnable() {
		plugin = this;
		new TemplateEvents().register(this);
		new TemplateCommand().register();
		
		Bukkit.getPluginManager().registerEvents(new ExemploGui(), this);
		Bukkit.getPluginManager().registerEvents(new ShopGui(), this);
		Bukkit.getPluginManager().registerEvents(new ExemploMenuGUi(), this);
		Bukkit.getPluginManager().registerEvents(new ExemploMenuShop(),this);
	}

	@Override
	public void onDisable() {
	}

}
