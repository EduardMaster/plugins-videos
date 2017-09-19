package net.eduard.hg.manager;

import net.eduard.hg.HG;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Kit implements Listener{

	public static ItemStack kitSelector() {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> list = new ArrayList<>();
		list.add("§eSelecione seu Kit!");
		meta.setLore(list);
		meta.setDisplayName("§6§lKitSelector");
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack archer() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> list = new ArrayList<>();
		list.add("§bVoce ganha um arco e flecha!");
		meta.setLore(list);
		meta.setDisplayName("§6Kit Archer");
		item.setItemMeta(meta);
		return item;
	}
	@EventHandler
	public void gui(PlayerInteractEvent e) {
		if (e.getAction()==Action.RIGHT_CLICK_AIR) {
			if (e.getItem()!=null) {
				if (e.getItem().equals(kitSelector())) {
					e.setCancelled(true);
					Inventory inv = Bukkit.createInventory(null, 9,"§8KitSelector");
					inv.addItem(archer());
					e.getPlayer().openInventory(inv);
				}
			}
		}
	}
	@EventHandler
	public void selecionarKit(InventoryClickEvent e)
	{
		Player p = (Player)e.getWhoClicked();
		if (e.getInventory().getName().equals("§8KitSelector")) {
			if (e.getCurrentItem()!=null) {
				if (e.getCurrentItem().equals(archer())) {
					HG.kits.put(p,KitType.ARCHER);
				}
				e.setCancelled(true);
			}
		}
	}
}
