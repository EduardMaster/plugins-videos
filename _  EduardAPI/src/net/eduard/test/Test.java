package net.eduard.test;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.api.manager.PlayerAPI;
import net.eduard.api.manager.TabAPI;
import net.eduard.api.manager.TitleAPI;
import net.eduard.api.util.TitleType;
import net.eduard.eduardapi.EduardAPI;

public class Test extends EventAPI {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		EduardAPI.m.generateGuis(e.getPlayer());
		API.refreshAll(e.getPlayer());
		EduardAPI.m.giveItems(e.getPlayer());

	}

	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		if (e.getMessage().equalsIgnoreCase("title")) {
			// RexAPI.sendAction(e.getPlayer(), "aewaew");
			TitleAPI.send(e.getPlayer(), TitleType.TITLE, "§6Teste", 20, 20, 20);
			TabAPI.setTabList(e.getPlayer(), "§2Bom dia ping:" + PlayerAPI.getPing(e.getPlayer()), "§eMeu servidor");
			// RexAPI.sendTitle3(e.getPlayer(), 20, 20, 20);
			// PacketPlayOutChat

		}

	}

	

	@EventHandler
	public void kill(PlayerRespawnEvent e) {
		// EduardAPI.m.generateGuis(e.getPlayer());
		// API.refreshAll(e.getPlayer());
		// EduardAPI.m.giveItems(e.getPlayer());
	}
	static {
//		Gui loja = new Gui(3, "§8Venda de MAQUINA");
//		loja.set(11, API.newItem(Material.IRON_ORE, "§aMaquina de Minerio de Ferro"),new Events().effect(new PlayerEffect() {
//			
//			public void effect(Player p) {
//				p.getInventory().addItem(API.newItem(Material.IRON_ORE, "§aMaquina de Minerio de Ferro"));
//				
//			}
//		}));
//		loja.set(13, API.newItem(Material.LAPIS_ORE, "§aMaquina de Mineiro de Lapiz"));
//		loja.register();
	}

}
