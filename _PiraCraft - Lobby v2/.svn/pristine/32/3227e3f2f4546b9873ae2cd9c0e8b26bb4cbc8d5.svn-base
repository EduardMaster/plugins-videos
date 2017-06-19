package br.com.piracraft.lobby2.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.piracraft.api.Main;
import br.com.piracraft.lobby2.menu.Menu;
import br.com.piracraft.lobby2.menu.MenuSevenDays;
import br.com.piracraft.lobby2.utils.Cor;
import br.com.piracraft.lobby2.utils.LobbyAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class GameManager implements Listener {

	@EventHandler
	public void event(WeatherChangeEvent e) {
		if (e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
	

	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void dano(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	public String Faction = "§aFaction";
	public String Hardcore = "§aHardCore";
	public String Kitpvp = "§aKitPvP";
	public String Setedias = "§aSalas-SeteDias";
	public String Skywars = "§aSkyWars";
	public String Shop = "§aSete Dias";

	@EventHandler
	public void teste(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().equalsIgnoreCase("/teste")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					Faction + Hardcore + Kitpvp + Setedias + Skywars);
		}
	}

	@EventHandler
	public void clicarDireitoNPC(NPCRightClickEvent event) {
		if (event.getNPC().getName().equalsIgnoreCase(Faction)) {
			if (Main.isStaff.get(event.getClicker())) {
				LobbyAPI.enviar(event.getClicker(), "factions01");
			} else {
				LobbyAPI.enviarAlertaPlayer(event.getClicker(), "Alerta",
						"Servidor em desenvolvimento!", Cor.Azul);
				return;
			}
		}
		if (event.getNPC().getName().equalsIgnoreCase(Hardcore)) {
			Menu.getMenu("Hg").show(event.getClicker());
			return;
		}
		if (event.getNPC().getName().equalsIgnoreCase(Kitpvp)) {
			Menu.getMenu("KitPvp").show(event.getClicker());
			return;
		}
		if (event.getNPC().getName().equalsIgnoreCase(Setedias)) {
			MenuSevenDays.openinvservers(event.getClicker());
			return;
		}
		if (event.getNPC().getName().equalsIgnoreCase(Skywars)) {
			Menu.getMenu("Skywars").show(event.getClicker());
			return;
		}
		if (event.getNPC().getName().equalsIgnoreCase(Shop)) {
			MenuSevenDays.openinv(event.getClicker());
			return;
		}
	}
}
