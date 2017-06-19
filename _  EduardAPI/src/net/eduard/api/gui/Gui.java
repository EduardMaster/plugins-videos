
package net.eduard.api.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import net.eduard.api.API;
import net.eduard.api.config.Section;

public class Gui extends Click {

	private boolean open = true;
	
	private int size;
	
	private String title;

	private transient Map<Integer, Slot> slots = new HashMap<>();

	private transient Inventory inventory;

	/**
	 * e.getView().getTopInventory() == e.getInventory()
	 * e.getView().getBottomInventory() == Inventario do Jogador
	 * 
	 * @param e
	 */
	@EventHandler
	private void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (inventory.equals(e.getInventory())) {
				e.setCancelled(true);
//				if (e.getAction() == InventoryAction.HOTBAR_SWAP){
//					e.setCancelled(true);
//				}
//				if (e.getCurrentItem() == null) {
//					return;
//				}
//				if (e.getCurrentItem().getType() == Material.AIR) {
//					return;
//				}
//				
//				
//				if (e.getRawSlot() > e.getSlot()) {
//					return;
//				}
				Slot slot = slots.get(e.getRawSlot());
				if (slot != null) {
					slot.effect(p);
				}
			}
		}

	}

	public Gui() {
	}
	public Gui(int size, String name) {
		this.size = size;
		this.title = name;
		init();
	}
	
	public Gui open(Player player) {
		player.openInventory(inventory);
		return this;
	}

	public Gui clear() {
		inventory.clear();
		slots.clear();
		return this;
	}
	private int getId(int id) {
		return id < 0 ? 0 : id >= inventory.getSize() ? 0 : id;
	}
	public Gui add(Slot slot) {
		int first = inventory.firstEmpty();
		if (first != -1) {
			first = getId(first);
			inventory.setItem(first, slot.getItem());
			slots.put(first, slot);
		}
		return this;
	}

	public Gui set(Slot slot) {
		int id = getId(slot.getSlot());
		inventory.setItem(id, slot.getItem());
		slots.put(id, slot);
		return this;
	}

	public Gui remove(int id) {
		slots.remove(id);
		inventory.setItem(id, null);
		return this;
	}

	public Inventory getInventory() {

		return inventory;
	}

	public boolean isOpen() {
		return open;
	}

	public Gui setOpen(boolean canOpen) {
		this.open = canOpen;
		return this;
	}

	public Map<Integer, Slot> getSlots() {
		return slots;
	}

	public Gui setSlots(Map<Integer, Slot> slots) {
		this.slots = slots;
		return this;

	}
	public void init(){
		this.inventory = API.newInventory(title, size * 9);
		setClick(new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				if (open) {
					open(e.getPlayer());
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				if (open) {
					open(e.getPlayer());
				}
			}
		});
	}

	public void save(Section section, Object value) {
		section.remove("Slots");
		for (Entry<Integer, Slot> map : slots.entrySet()) {
			Integer id = map.getKey();
			Slot slots = map.getValue();
			slots.setSlot(id);
			section.set("Slots.slot" + id,slots);
		}

	}
	public Object get(Section section) {
		if (inventory == null){
			init();
		}
		for (Section sub : section.getSection("Slots").getValues()) {
			Slot slot = (Slot) sub.getValue();
			set(slot);
		}
		return null;
	}

}
