package net.eduard.api.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public class DropItem implements Save {

	private int minAmount = 1;

	private int maxAmount = 1;

	private double chance = 1;

	private ItemStack item;

	public DropItem() {
	}

	public DropItem(ItemStack item, int min, int max, double chance) {
		setMinAmount(min);
		setMaxAmount(max);
		setChance(chance);
		setItem(item);
	}

	public ItemStack create() {
		ItemStack clone = item.clone();
		int amount = API.getRandomInt(getMinAmount(), getMaxAmount());
		if (API.getChance(chance)) {
			clone.setAmount(amount);
			return clone;
		}
		return new ItemStack(Material.AIR);

	}

	public double getChance() {

		return chance;
	}

	public int getMaxAmount() {

		return maxAmount;
	}

	public int getMinAmount() {

		return minAmount;
	}

	public ItemStack getItem() {

		return this.item;
	}

	public void setItem(ItemStack item) {

		this.item = item;
	}

	public void setChance(double chance) {

		this.chance = chance;
	}

	public void setMaxAmount(int maxAmount) {

		this.maxAmount = maxAmount;
	}

	public void setMinAmount(int minAmount) {

		this.minAmount = minAmount;

	}

	public void save(ConfigSection section, Object value) {

	}

	public Object get(ConfigSection section) {
		return null;
	}

}
