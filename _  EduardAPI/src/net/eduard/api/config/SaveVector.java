package net.eduard.api.config;

import org.bukkit.util.Vector;

import net.eduard.api.util.Save;

public class SaveVector implements Save{

	public Object get(Section section) {
		return new Vector(section.getDouble("x"),section.getDouble("y") , section.getDouble("z"));
	}

	public void save(Section section, Object value) {
		if (value instanceof Vector) {
			Vector vector = (Vector) value;
			section.set("x",vector.getX());
			section.set("y",vector.getY());
			section.set("z",vector.getZ());
			
		}
	}

}
