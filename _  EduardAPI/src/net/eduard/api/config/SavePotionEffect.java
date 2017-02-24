package net.eduard.api.config;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.util.Save;

public class SavePotionEffect implements Save{

	public Object get(Section section) {
		boolean ambient = section.getBoolean("ambient");
		PotionEffectType type = PotionEffectType.getByName(section.getString("type"));
		Integer duration = section.getInt("duration");
		Integer amplifier = section.getInt("amplifier");
		return new PotionEffect(type, duration, amplifier, ambient);
	}

	public void save(Section section, Object value) {
		if (value instanceof PotionEffect) {
			PotionEffect potionEffect = (PotionEffect) value;
			section.set("ambient",potionEffect.isAmbient());
			section.set("type",potionEffect.getType());
			section.set("duration",potionEffect.getDuration());
			section.set("amplifier",potionEffect.getAmplifier());
		}
	}

}
