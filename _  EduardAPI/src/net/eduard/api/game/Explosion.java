package net.eduard.api.game;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import net.eduard.api.config.Save;
import net.eduard.api.config.Section;

public class Explosion implements Save{

	private float power;
	private boolean breakBlocks;
	private boolean makeFire;
	public Explosion() {
	}
	public Explosion(float power, boolean breakBlocks, boolean makeFire) {
		super();
		this.power = power;
		this.breakBlocks = breakBlocks;
		this.makeFire = makeFire;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public boolean isBreakBlocks() {
		return breakBlocks;
	}
	public void setBreakBlocks(boolean breakBlocks) {
		this.breakBlocks = breakBlocks;
	}
	public boolean isMakeFire() {
		return makeFire;
	}
	public void setMakeFire(boolean makeFire) {
		this.makeFire = makeFire;
	}

	public Explosion create(Entity entity) {
		create(entity.getLocation());
		return this;
	}

	public Explosion create(Location location) {
		location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), power, makeFire, breakBlocks);
		return this;
	}
	@Override
	public Object get(Section section) {
		return null;
	}
	@Override
	public void save(Section section, Object value) {
		
	}
	
	
}
