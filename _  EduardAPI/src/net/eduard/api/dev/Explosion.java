package net.eduard.api.dev;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Explosion implements Save {
	public static final float TNT = 4F;
	public static final float CREEPER = 3F;
	private float power = 3F;
	private boolean breakBlocks;
	private boolean makeFire;

	public Explosion() {

	}

	public Explosion(float power) {
		this(power,true);
	}
	public Explosion(float power, boolean breakBlocks) {
		this(power,breakBlocks,true);
	}
	public Explosion(float power, boolean breakBlocks, boolean makeFire) {
		this.power = power;
		this.breakBlocks = breakBlocks;
		this.makeFire = makeFire;
	}

	public float getPower() {
		return power;
	}

	public Explosion setPower(float power) {
		this.power = power;
		return this;
	}

	public Explosion power(float power) {
		setPower(power);
		return this;
	}

	public Explosion breakBlocks(boolean breakBlocks) {
		setBreakBlocks(breakBlocks);
		return this;
	}

	public Explosion makeFire(boolean makeFire) {
		setMakeFire(makeFire);
		return this;
	}

	public boolean isBreakBlocks() {
		return breakBlocks;
	}

	public Explosion setBreakBlocks(boolean breakBlocks) {
		this.breakBlocks = breakBlocks;
		return this;
	}

	public boolean isMakeFire() {
		return makeFire;
	}

	public Explosion setMakeFire(boolean makeFire) {
		this.makeFire = makeFire;
		return this;
	}

	public Explosion create(Entity entity) {
		return create(entity.getLocation());
	}

	public Explosion create(Location location) {

		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		location.getWorld().createExplosion(x, y, z, power, makeFire, breakBlocks);
		return this;
	}

	public void save(Section section, Object value) {
	}

	public Explosion get(Section section) {
		return null;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (breakBlocks ? 1231 : 1237);
		result = prime * result + (makeFire ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(power);
		return result;
	}

	public String toString() {
		return "Explosion [power=" + power + ", breakBlocks=" + breakBlocks + ", makeFire=" + makeFire + "]";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Explosion other = (Explosion) obj;
		if (breakBlocks != other.breakBlocks)
			return false;
		if (makeFire != other.makeFire)
			return false;
		if (Float.floatToIntBits(power) != Float.floatToIntBits(other.power))
			return false;
		return true;
	}

}
