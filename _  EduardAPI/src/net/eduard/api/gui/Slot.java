package net.eduard.api.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Slot extends Events{

	public Slot() {

	}

	public Slot(ItemStack item, int id) {
		this.item = item;
		this.slot = id;
	}

	private ItemStack item;

	private int slot;
	
	public Slot give(Inventory inv) {
		inv.setItem(slot, item);
		return this;
	}

	public ItemStack getItem() {
		return item;
	}

	public Slot setItem(ItemStack item) {
		this.item = item;
		return this;
	}

	public int getSlot() {
		return slot;
	}

	public Slot setSlot(int id) {
		this.slot = id;
		return this;
	}
}
