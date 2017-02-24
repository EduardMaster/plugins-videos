package net.eduard.api.dev;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import net.eduard.api.util.FireworkType;

public class Fireworks {

	private List<FireworkEffect> effects = new ArrayList<>();
	private Location spawn;

	private int power;

	public Builder addEffect(boolean trail, boolean flicker, FireworkType type, Color firstColor, Color lastColor) {
		Builder effect = FireworkEffect.builder().trail(trail).flicker(flicker)
				.with(FireworkEffect.Type.valueOf(type.name())).withColor(firstColor).withFade(lastColor);
		effects.add(effect.build());
		return effect;
	}

	public Builder addEffect(FireworkType type, Color firstColor, Color lastColor) {

		return addEffect(true, true, type, firstColor, lastColor);
	}

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

	public Fireworks setPower(int power) {

		this.power = power;
		return this;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

}
