package net.eduard.api.player;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.eduard.api.config.Save;
import net.eduard.api.config.Section;

public class SoundEffect implements Save {

	private Sound sound;
	private float volume;
	private float pitch;

	public SoundEffect() {
		this(Sound.values()[0], 2, 1);
	}

	public SoundEffect(Sound sound) {
		this(sound, 2, 1);
	}

	public SoundEffect(Sound sound, float volume, float pitch) {
		super();
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	public static SoundEffect create(Sound sound) {
		try {
			return new SoundEffect(sound);
		} catch (Exception e) {
			return new SoundEffect(Sound.values()[0]);
		}
	}
	public SoundEffect create(Location location) {
		location.getWorld().playSound(location, sound, volume, pitch);
		return this;
	}
	public SoundEffect create(Entity entity) {
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

	public Object get(Section section) {
		return null;
	}

	public void save(Section section, Object value) {

	}

}
