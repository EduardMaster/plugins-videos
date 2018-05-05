
package net.eduard.money.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.setup.Mine;
import net.eduard.api.setup.VaultAPI;
import net.eduard.api.setup.manager.TimeManager;

public class TemplateEvent extends TimeManager {

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() == null)
			return;
		if (e.getItem().getType() == Material.DIAMOND) {

			abrirMenu(p);
			e.setCancelled(true);
		}

	}

	public static void abrirMenu(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9 * 1, "§8Loja de Itens");
		{
			ItemStack item = Mine.newItem(Material.IRON_INGOT, "§aFerro", 1,
					0,

					"§aClique Direito para Comprar",
					"§aClique Esquerdo para Vender");

			inv.setItem(Mine.getPosition(1, 5), item);
		}
		{
			ItemStack item = Mine.newItem(Material.GOLD_INGOT, "§aOuro", 1,
					0,

					"§aClique Direito para Comprar",
					"§aClique Esquerdo para Vender");

			inv.setItem(Mine.getPosition(1, 7), item);
		}
		p.openInventory(inv);
	}
	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals("§8Loja de Itens")) {
				e.setCancelled(true);
				if (e.getRawSlot() == Mine.getPosition(1, 5)) {
					int priceBuy = 200;
					int priceSell = 50;
					if (e.getClick() == ClickType.RIGHT) {

						if (VaultAPI.getEconomy().has(p, priceBuy)) {
							VaultAPI.getEconomy().withdrawPlayer(p, priceBuy);

							p.getInventory().addItem(
									new ItemStack(Material.IRON_INGOT));
							p.sendMessage("§aVoce comprou!");
							

						} else {
							p.sendMessage("§cVoce nao tem dinheiro!");
						}

					} else if (e.getClick() == ClickType.LEFT) {

						if (Mine.contains(p.getInventory(),
								new ItemStack(Material.IRON_INGOT), 1)) {
							Mine.remove(p.getInventory(),
									new ItemStack(Material.IRON_INGOT), 1);
							
							VaultAPI.getEconomy().depositPlayer(p, priceSell);
							p.sendMessage("§aVoce vendeu!");
							
						}else
						{
							p.sendMessage("§cVoce nao tem este item no inventario!");
						}

					} else if (e.getClick() == ClickType.SHIFT_LEFT) {
						if (Mine.contains(p.getInventory(),
								new ItemStack(Material.IRON_INGOT), 64)) {
							Mine.remove(p.getInventory(),
									new ItemStack(Material.IRON_INGOT), 64);
							
							VaultAPI.getEconomy().depositPlayer(p, priceSell*64);
							p.sendMessage("§aVoce vendeu!");
							
						}else
						{
							p.sendMessage("§cVoce nao tem este item no inventario!");
						}
					} else if (e.getClick() == ClickType.SHIFT_RIGHT) {
						if (VaultAPI.getEconomy().has(p, priceBuy*64)) {
							VaultAPI.getEconomy().withdrawPlayer(p, priceBuy*64);

							p.getInventory().addItem(
									new ItemStack(Material.IRON_INGOT,64));
							p.sendMessage("§aVoce comprou!");
							

						} else {
							p.sendMessage("§cVoce nao tem dinheiro!");
						}
					}
				}
			}

		}
	}

}
