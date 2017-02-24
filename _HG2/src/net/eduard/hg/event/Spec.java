package net.eduard.hg.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.eduard.api.manager.EventAPI;
import net.eduard.hg.manager.HG;

public class Spec extends EventAPI {

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (HG.specs.contains(p)) {
			if (e.getMaterial() == Material.ENDER_PEARL) {
				show(p);
				e.setCancelled(true);
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals(HG.specGuiTitle)) {
				if (e.getCurrentItem() != null) {
					SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
					p.teleport(Bukkit.getPlayer(meta.getOwner()));
					p.closeInventory();
					e.setCancelled(true);
				}
			}
		}
	}

	public void show(Player p) {
		{
			Inventory inv = Bukkit.createInventory(p, 6 * 9, HG.specGuiTitle);
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player != p) {
					if (HG.players.contains(player)) {
						ItemStack item = new ItemStack(Material.SKULL_ITEM);
						SkullMeta meta = (SkullMeta) item.getItemMeta();
						meta.setOwner(player.getName());
						meta.setDisplayName("§6" + player.getName());
						item.setItemMeta(meta);
						inv.addItem(item);
					}
				}
			}
		}
	}

}
