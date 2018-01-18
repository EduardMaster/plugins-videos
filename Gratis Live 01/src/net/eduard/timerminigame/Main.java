package net.eduard.timerminigame;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable() {

		new TimerDeMinigame().runTaskTimerAsynchronously(this, 20, 20);
		
		
	}
}
