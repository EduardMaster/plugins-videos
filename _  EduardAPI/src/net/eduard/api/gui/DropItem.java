package net.eduard.api.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class DropItem implements Save{

	private int minAmount=1;

	private int maxAmount=1;

	private double chance=1;

	private ItemStack item;

	public ItemStack getItem() {

		return this.item;
	}

	public void setItem(ItemStack item) {

		this.item = item;
	}

	public DropItem(ItemStack item, int min, int max, double chance) {
		setMinAmount(min);
		setMaxAmount(max);
		setChance(chance);
		setItem(item);
	}

	public DropItem() {
	}

	public ItemStack create() {

		int amount = API.getRandomInt(getMinAmount(), getMaxAmount());
		if (API.getChance(chance)) {
			item.setAmount(amount);
			return item;
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

	public DropItem setChance(double chance) {

		this.chance = chance;
		return this;
	}

	public DropItem setMaxAmount(int maxAmount) {

		this.maxAmount = maxAmount;
		return this;
	}

	public DropItem setMinAmount(int minAmount) {

		this.minAmount = minAmount;

	return this;
	}

	public void save(Section section, Object value) {
		
	}

	public Object get(Section section) {
		return null;
	}

}

