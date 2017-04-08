package net.eduard.eduardapi;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.barapi.BarAPI;
import net.eduard.api.command.EduardCommand;
import net.eduard.api.command.GamemodeCommand;
import net.eduard.api.command.GotoCommand;
import net.eduard.api.manager.Information;

public class Main extends JavaPlugin{
	private static JavaPlugin plugin;

	public static JavaPlugin getInstance() {
		return plugin;
	}

	

	public void onEnable() {
		plugin = this;
	
		Information.saveObjects();
		API.console("§bEduardAPI §fhabilitado!");
		API.resetScoreboards();
		BarAPI.enable(this);
		API.loadMaps();
		new GamemodeCommand();
		new GotoCommand();
		new EduardCommand();

	}

	public void onDisable() {
		API.saveMaps();
		API.console("§bEduardAPI §fdesabilitado!");
	}

}
