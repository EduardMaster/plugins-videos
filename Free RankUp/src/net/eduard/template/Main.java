package net.eduard.template;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main getInstance(){
		return JavaPlugin.getPlugin(Main.class);
	}
	public void create(){
		
	}
	public void delete(){
		
	}
	private static int x;
	public static int getX(){
		return x;
	}
	@Override
	public void onEnable() {
		saveResource("ranks.yml", false);
		RankManager.loadRanks(new File(getDataFolder(),"ranks.yml"));
	}

}
