package net.eduard.api.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fireworks {

	private List<FireworkEffect> effects = new ArrayList<>();
	private Location spawn;
	private int power;

	public Firework create(Location location) {
		Firework firework = location.getWorld().spawn(location, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffects(effects);
		meta.setPower(getPower());
		firework.setFireworkMeta(meta);
		return firework;
	}

	public List<FireworkEffect> getEffects() {

		return effects;
	}

	public int getPower() {

		return power;
	}

	public void setPower(int power) {

		this.power = power;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

}
