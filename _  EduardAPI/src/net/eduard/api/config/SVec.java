package net.eduard.api.config;

import org.bukkit.util.Vector;

public class SVec extends Serz<Vector> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double x, y, z;

	public void save(Vector value) {
		set(value);
	}

	public Vector reload() {
		if (isNull()) {
			set(new Vector(x, y, z));
		}
		return get();
	}

}
