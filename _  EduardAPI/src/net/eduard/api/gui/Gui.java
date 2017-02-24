
package net.eduard.api.gui;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.ClickCheck;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.ClickType;

public class Gui extends Click {
	public void register() {

		API.GUIS.add(this);
	}
	public void unregister() {

		API.GUIS.remove(this);
	}

	public void open(Player player) {
		player.openInventory(inventory);
	}

	public void clear() {
		inventory.clear();
		effects.clear();
	}

	private boolean canOpen = true;

	private Inventory inventory;

	private HashMap<Integer, Events> effects = new HashMap<>();
	
	private int getId(int id) {
		return id<0?0:id>=inventory.getSize()?0:id;
	}
	
	public Gui aset(Slot slot) {
		int id = getId(slot.getSlot());
		inventory.setItem(id, slot.getItem());
		return this;
	}

	public Gui(int size, String name) {
		super(Material.CHEST);
		setType(ClickType.RIGHT_CLICK);
		setCheck(ClickCheck.IGNORING_AMOUNT);
		setInventory(API.newInventory(name, size * 9));
		setEffect(new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				if (canOpen) {
					open(e.getPlayer());
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				if (canOpen) {
					open(e.getPlayer());
				}
			}
		});
	}

	public boolean set(int id, ItemStack item) {
		if (id == -1)
			return false;
		if (id > getInventory().getSize()) {
			return false;
		}
		inventory.setItem(id, item);
		return true;
	}

	public boolean add(ItemStack item) {
		return set(inventory.firstEmpty(), item);
	}

	public boolean set(int id, ItemStack item, Events effect) {
		if (id == -1)
			return false;
		if (id > getInventory().getSize()) {
			return false;
		}
		inventory.setItem(id, item);
		if (effect == null) {
			return false;
		}
		effects.put(id, effect);
		return true;
	}

	public boolean add(ItemStack item, Events effect) {
		return set(inventory.firstEmpty(), item, effect);
	}

	public boolean add(Slot slot) {
		return add(slot.getItem(), slot);
	}

	public boolean set(Slot slot) {
		return set(slot, slot);
	}

	public boolean set(Slot slot, Events event) {
		return set(slot.getSlot(), slot.getItem(), event);
	}

	public Gui remove(int id) {
		effects.remove(id);
		inventory.setItem(id, null);
		return this;
	}

	public Inventory getInventory() {

		return inventory;
	}

	public Events getEvent(int id) {

		return effects.get(id);
	}

	public ItemStack getItem(int id) {
		return inventory.getItem(id);
	}

	private void setInventory(Inventory inventory) {

		this.inventory = inventory;
	}

	public Gui canOpen(boolean canOpen) {
		this.canOpen = canOpen;
		return this;
	}

	public boolean canOpen() {
		return canOpen;
	}

	public boolean isRegistred() {
		return API.GUIS.contains(this);
	}
	public void save(Section section, Object value) {
		section.remove("Drops");
		int index = 0;
		for (ItemStack item:inventory.getContents()) {
			String tag = "Drops.drop"+index;
			section.set(tag+".item",item);
			section.set(tag+".slot",index);
			section.set(tag+".action",effects.get(index));
			index++;
		}
		
	}
	public Object get(Section section) {
		for (Section sub:section.getSection("Drops").getValues()) {
			Integer index = sub.getInt("slot");
			inventory.setItem(index, sub.getItem("item"));
			if (sub.contains("action")) {
				effects.put(index, (Events) sub.get("action"));
			}
		}
		return null;
	}

}
