package net.eduard.api.config.save;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.util.Save;

public class SaveConfig implements Save {

	@Override
	public Object get(ConfigSection CS) {

		return new Config(
				(JavaPlugin) Bukkit.getPluginManager()
						.getPlugin(CS.getString("plugin")),
				CS.getString("name"));
	}

	@Override
	public void save(ConfigSection CS, Object value) {
		if (value instanceof Config) {
			Config config = (Config) value;
			CS.set("name", config.getNameComplete());
			CS.set("plugin", config.getPlugin().getName());

		}
	}

}
