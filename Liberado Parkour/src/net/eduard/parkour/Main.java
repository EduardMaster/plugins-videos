
package net.eduard.parkour;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.ConfigSection;
import net.eduard.parkour.command.ParkourCMD;
import net.eduard.parkour.event.ParkourEvent;
/**
 * Preço: 35
 * @author Eduard-PC
 *
 */
public class Main extends JavaPlugin {

	public static Config config;

	@Override
	public void onEnable() {

		config = new Config(this);
		ConfigSection.register("ParkourStats", new Stats());
		ConfigSection.register("ParkourArena", new Arena());
		for (Player p : API.getPlayers()) {
			Arena.join(p);
		}
		Arena.reload();
		new ParkourCMD();
		new ParkourEvent().register(this);
	}

	@Override
	public void onDisable() {
		Arena.saveStats();
		Arena.save();
	}

}
