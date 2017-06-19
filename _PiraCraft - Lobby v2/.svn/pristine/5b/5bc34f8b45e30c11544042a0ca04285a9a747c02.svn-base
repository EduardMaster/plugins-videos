package br.com.piracraft.lobby2.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.piracraft.api.caixas.ItemAPI;

public class MenuLobby extends Menu{
	public MenuLobby() {
		super("§8Servidores");
	}
	public void update() {
		for (int i = 0; i < 10; i++) {
			getMenu().setItem(i, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 15,
					" ", false));
		}
		getMenu().setItem(10, ItemAPI.Criar(Material.GRASS, 1, 0, "§6§lSkyWars",
				false, " ", "§7Click para jogar"));
		getMenu().setItem(11, ItemAPI.Criar(Material.IRON_SWORD, 1, 0, "§6§lKit Pvp",
				false, " ", "§7Click para jogar"));
		getMenu().setItem(12, ItemAPI.Criar(Material.MUSHROOM_SOUP, 1, 0,
				"§6§lHardcore Games", false, " ", "§7Click para jogar"));
		getMenu().setItem(13, ItemAPI.Criar(Material.LAVA_BUCKET, 1, 0, "§6§l7 Dias",
				false, " ", "§7Click para jogar"));
		getMenu().setItem(14, ItemAPI.Criar(Material.DIAMOND_PICKAXE, 1, 0,
				"§6§lFactions", false, " ", "§cEm desenvolvimento"));
		getMenu().setItem(15,
				ItemAPI.Criar(Material.BARRIER, 1, 0, "§6§lEm breve", false));
		getMenu().setItem(16,
				ItemAPI.Criar(Material.BARRIER, 1, 0, "§6§lEm breve", false));
		for (int i = 17; i < 27; i++) {
			getMenu().setItem(i, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 15,
					" ", false));
		}

	}
	@EventHandler
	public void clicar(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().toString().contains("RIGHT")
				&& (event.getItem() != null)) {
			if (event.getItem().getType() == Material.COMPASS) {
				show(player);
			}
		}
	}

	@EventHandler
	public void click(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getName().equalsIgnoreCase("§aMenu")
				&& (event.getCurrentItem() != null)) {
			event.setCancelled(true);
			if (event.getCurrentItem().getType() == Material.GRASS) {
				Menu.getMenu("Skywars").show(player);
			}
			if (event.getCurrentItem().getType() == Material.IRON_SWORD) {
				Menu.getMenu("KitPvp").show(player);
			}
		}
	}
}
