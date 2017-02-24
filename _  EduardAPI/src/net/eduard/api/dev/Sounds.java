package net.eduard.api.dev;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Sounds implements Save {
	public void save(Section section, Object value) {

	}

	public Object get(Section section) {
		return null;
	}

	public String toString() {
		return "Sounds [sound=" + sound + ", volume=" + volume + ", pitch=" + pitch + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(pitch);
		result = prime * result + ((sound == null) ? 0 : sound.hashCode());
		result = prime * result + Float.floatToIntBits(volume);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sounds other = (Sounds) obj;
		if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch))
			return false;
		if (sound != other.sound)
			return false;
		if (Float.floatToIntBits(volume) != Float.floatToIntBits(other.volume))
			return false;
		return true;
	}

	private Sound sound;

	private float volume = 2;

	private float pitch = 1;

	public Sounds(Sound sound, float volume, float pitch) {
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	public static Sounds create(Sound sound) {
		try {
			return new Sounds(sound);
		} catch (Exception ex) {
			return new Sounds(Sound.values()[0]);
		}
	}

	public Sounds(Sound sound) {
		this(sound, 1, 1);
	}

	public Sounds() {
	}

	public Sound getSound() {

		return this.sound;
	}


	public Sounds setSound(Sound sound) {

		this.sound = sound;
		return this;
	}

	public float getVolume() {

		return this.volume;
	}

	public Sounds setVolume(float volume) {

		this.volume = volume;
		return this;
	}

	public float getPitch() {

		return this.pitch;
	}

	public Sounds setPitch(float pitch) {

		this.pitch = pitch;
		return this;
	}

	public Sounds create(Player player) {
		player.playSound(player.getLocation(), sound, volume, pitch);
		return this;
	}

	public Sounds create(Player player, Location location) {

		player.playSound(location, sound, volume, pitch);
		return this;
	}

	public Sounds create(Location location) {

		location.getWorld().playSound(location, sound, volume, pitch);
		return this;

	}

}
