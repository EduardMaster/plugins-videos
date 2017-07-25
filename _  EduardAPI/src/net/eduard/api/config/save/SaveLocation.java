package net.eduard.api.config.save;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public class SaveLocation implements Save{
	public Location get(ConfigSection section) {
		World world = Bukkit.getWorld(section.getString("world"));
		Double x = section.getDouble("x");
		Double y = section.getDouble("y");
		Double z = section.getDouble("z");
		Float yaw = section.getFloat("yaw");
		Float pitch = section.getFloat("pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public void save(ConfigSection section, Object object) {
		Location location = (Location) object;
		section.set("world", location.getWorld().getName());
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());

	}
}
