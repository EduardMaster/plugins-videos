
package me.eduard.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
	public void Abrir(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR
			&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.hasPermission("kitpvp.open")) {
				if (e.getMaterial() == Material.COMPASS) {
					open(p);
				}
			}
		}
	}

	@EventHandler
	public void Clicar(InventoryClickEvent e) {

		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			PlayerInventory inv = p.getInventory();
			if (e.getCurrentItem() == null) {
				return;
			}
			if (inv.getTitle().equalsIgnoreCase("§4§lKIT SELECTOR")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§6Archer")) {
					Kit.getKit(p, KitType.ARCHER);
					Kit.setKit(p, KitType.ARCHER);
				} else if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§6EnderMage")) {
					Kit.getKit(p, KitType.ENDERMAGE);
					Kit.setKit(p, KitType.ENDERMAGE);
				}
				p.closeInventory();
			}
		}
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

	private void open(Player p) {

		Inventory inv = Bukkit.createInventory(null, 6 * 9, "§4§lKIT SELECTOR");

		for (KitType kit : KitType.values()) {
			switch (kit) {
			case ARCHER:
				ItemStack item = item(Material.BOW, 1, "§6Archer");
				item.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
				add(inv, item);
				break;
			case ENDERMAGE:
				add(inv, item(Material.ENDER_PEARL, 1, "§6EnderMage"));
				break;
			case STOMPER:
				break;
			default:
				break;
			}
		}
		p.openInventory(inv);
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
