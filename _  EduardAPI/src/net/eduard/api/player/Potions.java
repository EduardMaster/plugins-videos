package net.eduard.api.player;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.config.Save;
import net.eduard.api.config.Section;

public class Potions implements Save {

	private int level;

	private int time = 20;

	private PotionEffectType type;

	public Potions() {
	}

	public Potions(PotionEffectType type, int level, int time) {
		this.type = type;
		this.level = level;
		this.time = time;
	}

	public PotionEffect create(LivingEntity entity) {
		PotionEffect effect = new PotionEffect(getType(), getTime(), getLevel(), true);
		entity.addPotionEffect(effect, true);
		return effect;
	}

	public int getLevel() {

		return level;
	}

	public int getTime() {

		return time;
	}

	public PotionEffectType getType() {

		return type;
	}

	public Potions setLevel(int level) {

		this.level = level;
		return this;
	}

	public Potions setTime(int time) {

		this.time = time;
		return this;
	}

	public Potions setType(PotionEffectType type) {

		this.type = type;
		return this;
	}

	public void save(Section section, Object value) {
		Potions potion = (Potions) value;
		section.set("type", potion.getType().getName());
	}

	public Potions get(Section section) {
		type = PotionEffectType.getByName(section.getString("type"));
		return null;
	}

}
