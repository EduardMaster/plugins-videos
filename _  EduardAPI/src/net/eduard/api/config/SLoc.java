package net.eduard.api.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SLoc extends Serz<Location> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String world;
	private double x, y, z;
	private float yaw, pitch;

	public void save(Location value) {
		set(value);
		this.world = value.getWorld().getName();
	}

	public Location reload() {
		if (isNull()) {
			set(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));
		}
		return get();
	}

}
