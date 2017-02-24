
package me.eduard.kitpvp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@SuppressWarnings("unused")
public class KitSelector implements Listener {

	private static void add(Inventory inv, ItemStack i) {

		inv.addItem(i);
	}

	private static ItemStack item(Material i) {

		return new ItemStack(i);
	}

	private static ItemStack item(Material i, int qnt) {

		return new ItemStack(i, qnt);
	}

	private static ItemStack item(Material i, int qnt, String n) {

		ItemStack item = new ItemStack(i, qnt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(n);
		item.setItemMeta(meta);
		return item;
	}

	private static ItemStack item(Material i, int qnt, String n, String[] s) {

		ItemStack item = new ItemStack(i, qnt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(n);
		meta.setLore(Arrays.asList(s));
		item.setItemMeta(meta);
		return item;
	}

	@EventHandler
	public void Entrar(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		if (p.hasPermission("kitpvp.join")) {
			inv.clear();
			add(inv, item(Material.COMPASS, 1, "§4§lKIT SELECTOR"));
		}

	}

	@EventHandler
	public void Respawn(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Main.sh.scheduleSyncDelayedTask(Main.m, new Runnable() {

			public void run() {

				if (p.hasPermission("kitpvp.respawn")) {
					inv.clear();
					add(inv, item(Material.COMPASS, 1, "§4§lKIT SELECTOR"));
				}

			}
		});

	}

}
