package net.eduard.teleportbow.system;

import org.bukkit.Sound;

public class TeleportBow {

	private boolean enabled;
	private Sound sound;
	
	public TeleportBow(boolean enabled, Sound sound) {
		this.enabled = enabled;
		this.sound = sound;
	}
	public TeleportBow() {
		this(true,Sound.ENDERMAN_TELEPORT);
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Sound getSound() {
		return sound;
	}
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	
}
