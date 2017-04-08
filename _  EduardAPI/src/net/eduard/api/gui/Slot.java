package net.eduard.api.gui;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.player.Events;

public class Slot extends Events {
	public static final Slot DEFAULT = new Slot();
	private ItemStack item;

	private int slot;

	public Slot() {

	}
	public Slot(ItemStack item) {
		this.item = item;
	}

	public Slot(ItemStack item, int id) {
		this.item = item;
		this.slot = id;
	}

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
	public Slot setItem(Material type) {
		this.item = new ItemStack(type);
		return this;
	}


	public int getSlot() {
		return slot;
	}

	public Slot setSlot(int id) {
		this.slot = id;
		return this;
	}
	public Slot clone() {
		return (Slot) super.clone();
	}
}
