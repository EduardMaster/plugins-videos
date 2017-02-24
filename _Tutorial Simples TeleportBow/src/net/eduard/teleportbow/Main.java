
package net.eduard.teleportbow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.teleportbow.event.TeleportBowEvent;

public class Main extends JavaPlugin {


	public void onEnable() {
	
		Bukkit.getPluginManager().registerEvents(new TeleportBowEvent(), this);
	}


}
