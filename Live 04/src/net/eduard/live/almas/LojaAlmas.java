package net.eduard.live.almas;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.Listener;

public class LojaAlmas implements Listener {

	@EventHandler
	public void controle(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();

			if (e.getInventory().getName().equals("Mercado Negro")) {
				e.setCancelled(true);
				int posicao = e.getRawSlot();
				if (posicao == 10) {
					
					ItemStack cenoura = new ItemStack(Material.GOLDEN_CARROT);
					if (AlmasAPI.getAlmas(p)>1) {
						AlmasAPI.removeAlmas(p, 1);
						p.getInventory().addItem(cenoura);
					}else {
						p.sendMessage("§cVoce não tem almas suficientes");
					}
					
					
					
				}
			}
		}
	}

	public static void abrirLoja(Player player) {
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "Mercado Negro");

		{
			ItemStack item = new ItemStack(Material.GOLDEN_CARROT);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§aComprar Cenoura Dourada");
			item.setItemMeta(meta);
			inv.setItem(10, item);
		}

		player.openInventory(inv);
	}

	@EventHandler
	public void event(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Villager) {
			Villager npc = (Villager) e.getRightClicked();
			if (npc.getCustomName() != null) {
				p.sendMessage("§cNome do mercadao: §r"+npc.getCustomName());
				e.setCancelled(true);
				if (npc.getCustomName().contains("§fMercadao")) {
					e.setCancelled(true);
					p.sendMessage("§cDeu merda");
					abrirLoja(p);
				}
			}

		}
	}

	public static void spawnarMercadao(Location local) {
		Villager npc = (Villager) local.getWorld().spawnEntity(local, EntityType.VILLAGER);

		npc.setCustomNameVisible(true);
		npc.setCustomName("§fMercadao");

	}

}
