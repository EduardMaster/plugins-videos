
package net.eduard.launchpad;

import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.eduard.api.config.Config;
import net.eduard.api.config.Section;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.LaunchPad;
import net.eduard.api.dev.Sounds;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static Config config;

	public void onEnable() {

		plugin = this;
		config = new Config(this);
		config.add("Pads.sponge",
				new LaunchPad(19, -1, new Jump(new Vector(0, 2, 0), new Sounds(Sound.EXPLODE, 1, 1))));
		config.saveConfig();
		for (Section sec : config.getSection("Pads").getValues()) {
			((LaunchPad) sec.getValue()).register();
		}
	}
}
