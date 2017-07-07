package net.eduard.eduardapi;

import net.eduard.eduardapi.config.ConfigAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class Eduard extends Plugin{
	
	private static Eduard plugin;
	private ConfigAPI config;
	public static Eduard getInstance(){
		return plugin;
	}
	public ConfigAPI getConfig(){
		return config;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		config = new ConfigAPI("config.yml", this);
		
	}
	

}
