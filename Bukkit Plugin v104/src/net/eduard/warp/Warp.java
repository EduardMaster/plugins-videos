package net.eduard.warp;

import org.bukkit.Location;

public class Warp {
	private Location warpLocation;
	private String warpName;
	private String warpMessage;

	public Location getWarpLocation() {
		return warpLocation;
	}

	public void setWarpLocation(Location warpLocation) {
		this.warpLocation = warpLocation;
	}

	public String getWarpName() {
		return warpName;
	}

	public void setWarpName(String warpName) {
		this.warpName = warpName;
	}

	public String getWarpMessage() {
		return warpMessage;
	}

	public void setWarpMessage(String warpMessage) {
		this.warpMessage = warpMessage;
	}
}
