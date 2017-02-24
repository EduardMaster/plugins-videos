package net.eduard.api.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.EntityType;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Drop implements Save {

	public static final Map<World, Map<EntityType, Drop>> ALL_DROPS = new HashMap<>();

	public static final Map<World, Boolean> CAN_DROP = new HashMap<>();

	public static void enable(World world) {
		CAN_DROP.put(world, true);
	}

	public static void disable(World world) {
		CAN_DROP.put(world, false);
	}

	public static void setDrop(World world, EntityType type, Drop drop) {
		Map<EntityType, Drop> all = null;
		if (!ALL_DROPS.containsKey(world)) {
			all = new HashMap<>();
			ALL_DROPS.put(world, all);
		} else {
			all = ALL_DROPS.get(world);
		}
		all.put(type, drop);
	}

	public static Drop getDrop(World world, EntityType type) {
		Map<EntityType, Drop> all = null;
		if (!ALL_DROPS.containsKey(world)) {
			all = new HashMap<>();
			ALL_DROPS.put(world, all);
		} else {
			all = ALL_DROPS.get(world);
		}
		Drop value = null;
		if (!all.containsKey(type)) {
			value = new Drop();
			all.put(type, value);
		} else {
			value = all.get(type);
		}
		return value;
	}

	private boolean enable;

	private boolean normalDrops;

	private int minXp;

	private int maxXp;

	private List<DropItem> drops=new ArrayList<>();

	public Drop(boolean enable, boolean drop, int min, int max, List<DropItem> drops) {
		setEnable(enable);
		setNormalDrops(drop);
		setMinXp(min);
		setMaxXp(max);
		setDrops(drops);
	}

	public Drop() {
	}

	public List<DropItem> getDrops() {

		return drops;
	}

	public int getMaxXp() {

		return maxXp;
	}

	public int getMinXp() {

		return minXp;
	}

	public int getRandomXp() {

		return API.getRandomInt(getMinXp(), getMaxXp());
	}

	public boolean isEnable() {

		return enable;
	}

	public boolean isNormalDrops() {

		return normalDrops;
	}

	public void setDrops(List<DropItem> drops) {

		this.drops = drops;
	}

	public void setEnable(boolean enable) {

		this.enable = enable;
	}

	public void setMaxXp(int maxXp) {

		this.maxXp = maxXp;
	}

	public void setMinXp(int minXp) {

		this.minXp = minXp;
	}

	public void setNormalDrops(boolean normalDrops) {

		this.normalDrops = normalDrops;
	}

	public void save(Section section, Object value) {

	}

	public Object get(Section section) {
		drops = new ArrayList<>();
		for (Section sec : section.getSection("drops").getValues()) {
			drops.add((DropItem) sec.getValue());
		}
		return null;
	}

}