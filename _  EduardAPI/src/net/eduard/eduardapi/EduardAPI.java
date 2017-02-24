package net.eduard.eduardapi;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.gui.KitManager;
import net.eduard.eduardapi.command.DeleteWorldCommand;
import net.eduard.eduardapi.command.GamemodeCommand;
import net.eduard.eduardapi.command.GotoCommand;
import net.eduard.eduardapi.event.AutoRespawn;
import net.eduard.eduardapi.event.CustomJoinMessage;
import net.eduard.eduardapi.event.CustomQuitMessage;
import net.eduard.eduardapi.event.NoJoinMessage;
import net.eduard.eduardapi.event.NoQuitMessage;
import net.eduard.eduardapi.manager.Info;
import net.eduard.net.BarAPI;
import net.eduard.test.Test;

public class EduardAPI extends JavaPlugin{
	private static JavaPlugin plugin;

	public static JavaPlugin getInstance() {
		return plugin;
	}

	public static Config config;

	public void onEnable() {
		plugin = this;
		config = new Manager();
		if (config.getBoolean("Test")) {
			new Test();
			Config ex = new Config("b.yml");
			if (ex.contains("Kits")) {
				m = (KitManager) ex.get("Kits");
			}else {
				m = new KitManager();
				m.reloadDefaults();
//				m.selectKits(KitType.KANGAROO,KitType.MONK);
				try {
					ex.set("Kits", m);
					ex.saveConfig();
				} catch (Exception ex2) {
					ex2.printStackTrace();
				}
				
			}
		}
		Info.saveObjects();
		API.console("§bEduardAPI §fhabilitado!");
		API.resetScoreboards();
		BarAPI.enable(this);
		new GamemodeCommand();
		new GotoCommand();
		new DeleteWorldCommand();
		new AutoRespawn();
		new CustomQuitMessage();
		new CustomJoinMessage();
		new NoJoinMessage();
		new NoQuitMessage();
		

	}
	public static String message(String key) {
		return config.message(key);
	}
	public static boolean option(String key) {
		return config.getBoolean(key);
	}
	public static KitManager m;

	public void onDisable() {
		API.console("§bEduardAPI §fdesabilitado!");
	}

}
