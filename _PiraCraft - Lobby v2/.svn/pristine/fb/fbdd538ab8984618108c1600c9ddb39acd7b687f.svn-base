package br.com.piracraft.lobby2.menu;

import java.sql.ResultSet;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.util.MySQL;
import br.com.piracraft.lobby2.utils.Cor;
import br.com.piracraft.lobby2.utils.LobbyAPI;

public class MenuSkywars extends Menu {

	public MenuSkywars() {
		super("§8Skywars");
		update();

	}

	public void update() {
		for (int i = 0; i < 10; i++) {
			getMenu().setItem(i, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1,
					15, " ", false));
		}
		getMenu().setItem(17,
				ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 15, " ", false));
		getMenu().setItem(18,
				ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 15, " ", false));
		getMenu().setItem(20,
				ItemAPI.Criar(Material.INK_SACK, 1, 1, "§6§lEntrar no Jogo",
						false, " ", "§7Click para conectar rapidamente",
						"§7a uma sala disponivel de skywars."));
		getMenu().setItem(26,
				ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 15, " ", false));
		getMenu().setItem(27,
				ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 15, " ", false));
		getMenu().setItem(24, ItemAPI.Criar(Material.IRON_BLOCK, 1, 15,
				"§6§lTodas as Salas", false));
		for (int i = 35; i < 45; i++) {
			getMenu().setItem(i, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1,
					15, " ", false));
		}
	}
	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals(getMenu().getTitle())) {
				e.setCancelled(true);
				if (e.getSlot() == 24) {
					Menu.getMenu("Skywars2").show(p);
				}
				if (e.getSlot() == 20) {
					try {
						ResultSet rs = MySQL.getQueryResult("select * from "
								+ "V_MENU_SERVIDORES where ID_MINIGAME = '2' "
								+ "and SALA_TIPO = '2' and ID_NETWORK = '1' and JOGANDO = '1';");
						if (rs.next()) {
							String server = rs.getString("NOME_BUNGEECORD");
							LobbyAPI.enviarAlertaPlayer(p, "SkyWars",
									"Voce esta sendo enviado para " + server,
									Cor.Verde);
							LobbyAPI.enviar(p, server);
						} else {

						}
					} catch (Exception ex) {
					}

				}
			}

		}
	}

}
