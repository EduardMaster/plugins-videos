package net.eduard.live;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class KitManager implements Listener {

	private ArrayList<Kit> kits = new ArrayList<>();

	public ArrayList<Kit> getKits() {
		return kits;
	}

	public void setKits(ArrayList<Kit> kits) {
		this.kits = kits;
	}

	public void mostrarKits(Player p) {
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "Kits");

		int indexAtual = 10;
		for (Kit kit : kits) {
			inv.setItem(indexAtual, kit.getIcone());
			indexAtual++;
		}

		p.openInventory(inv);
	}
	@EventHandler
	public void event(PlayerInteractEvent e ) {
		Player p = e.getPlayer();
		if (e.getMaterial() == Material.STONE) {
			mostrarKits(p);
		}
		
	}
	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if (e.getInventory().getName().equals("Kits")) {
				e.setCancelled(true);
				
				if (e.getCurrentItem() == null)return;
				
				for (Kit kit : kits) {
					if (kit.getIcone().equals(e.getCurrentItem())) {
						kit.darKit(p);
						break;
					}
				}
				
				
				
				
			}
			
		}
	}
}
