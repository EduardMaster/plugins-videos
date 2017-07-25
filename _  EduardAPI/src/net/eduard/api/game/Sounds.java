package net.eduard.api.game;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public class Sounds implements Save {

	private Sound sound;
	private float volume;
	private float pitch;

	public Sounds() {
		this(Sound.values()[0], 2, 1);
	}

	public Sounds(Sound sound) {
		this(sound, 2, 1);
	}

	public Sounds(Sound sound, float volume, float pitch) {
		super();
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	public static Sounds create(Sound sound) {
		try {
			return new Sounds(sound);
		} catch (Exception e) {
			return new Sounds(Sound.values()[0]);
		}
	}
	public Sounds create(Location location) {
		location.getWorld().playSound(location, sound, volume, pitch);
		return this;
	}
	public Sounds create(Entity entity) {
		if (entity instanceof Player){
			Player p = (Player) entity;
			p.playSound(p.getLocation(), sound, volume, pitch);
			return this;
		}
		return create(entity.getLocation());
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public Object get(ConfigSection section) {
		return null;
	}

	public void save(ConfigSection section, Object value) {

	}

}
