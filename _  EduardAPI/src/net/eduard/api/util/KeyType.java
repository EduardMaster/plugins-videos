package net.eduard.api.util;

import org.bukkit.Location;

public enum KeyType {
	UUID, LETTER, NUMERIC, ALPHANUMERIC;
	public static interface LocationEffect {

		boolean effect(Location location);
	}
}
