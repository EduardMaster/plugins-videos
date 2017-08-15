package net.eduard.api.config.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LocationSerial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double x, y, z, yaw, pitch;
	private String world;

	public void setLocation(Location location) {
		this.world = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}
	public LocationSerial(Location location) {
		setLocation(location);
	}

	public LocationSerial(double x, double y, double z, World world) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world.getName();
	}
	public LocationSerial(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public LocationSerial(double x, double y, double z, String world) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	public LocationSerial(double x, double y, double z, double yaw,
			double pitch, String world) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.world = world;
	}
	public LocationSerial(Block block) {
		setLocation(block.getLocation());
	}
	public Location getLocation() {

		return new Location(getLocationWorld(), x, y, z, (float) yaw,
				(float) pitch);
	}
	public boolean isWorldLoaded() {
		return getLocation() != null;
	}
	public World getLocationWorld() {
		return Bukkit.getWorld(world);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public double getYaw() {
		return yaw;
	}
	public void setYaw(double yaw) {
		this.yaw = yaw;
	}
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}

}
