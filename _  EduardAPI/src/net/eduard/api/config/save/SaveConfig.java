package net.eduard.api.config.save;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.config.Save;
import net.eduard.api.config.Section;

public class SaveConfig implements Save {

	@Override
	public Object get(Section section) {

		return new Config(
				(JavaPlugin) Bukkit.getPluginManager()
						.getPlugin(section.getString("name")),
				section.getString("name"));
	}

	@Override
	public void save(Section section, Object value) {
		if (value instanceof Config) {
			Config config = (Config) value;
			config.set("name", config.getNameComplete());
			config.set("plugin", config.getPlugin().getName());

		}
	}

}
