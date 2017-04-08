
package net.eduard.parkour;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.Section;
import net.eduard.parkour.command.ParkourCMD;
import net.eduard.parkour.event.ParkourEvent;

public class Main extends JavaPlugin {

	public static Config config;

	public void onEnable() {

		config = new Config(this);
		Section.register("ParkourStats", new Stats());
		Section.register("ParkourArena", new Arena());
		for (Player p : API.getPlayers()) {
			Arena.join(p);
		}
		Arena.reload();
		new ParkourCMD();
		new ParkourEvent().register(this);

	}

	public void onDisable() {
		Arena.saveStats();
		Arena.save();
	}

}
