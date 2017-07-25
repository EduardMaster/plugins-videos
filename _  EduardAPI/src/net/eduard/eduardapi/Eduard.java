package net.eduard.eduardapi;

import net.eduard.api.config.BungeeConfig;
import net.md_5.bungee.api.plugin.Plugin;

public class Eduard extends Plugin{
	
	private static Eduard plugin;
	private BungeeConfig config;
	public static Eduard getInstance(){
		return plugin;
	}
	public BungeeConfig getConfig(){
		return config;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		config = new BungeeConfig("config.yml", this);
		
	}
	

}
