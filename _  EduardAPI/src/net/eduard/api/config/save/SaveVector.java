package net.eduard.api.config.save;

import org.bukkit.util.Vector;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public class SaveVector implements Save{

	@Override
	public Object get(ConfigSection section) {
		return new Vector(section.getDouble("x"),section.getDouble("y") , section.getDouble("z"));
	}

	@Override
	public void save(ConfigSection section, Object value) {
		if (value instanceof Vector) {
			Vector vector = (Vector) value;
			section.set("x",vector.getX());
			section.set("y",vector.getY());
			section.set("z",vector.getZ());
			
		}
	}

}
