package net.eduard.hg.event;

import javax.swing.ImageIcon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.hg.Main;
import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGState;

public class HGEvent extends EventAPI {

	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("hg.chatcolor")) {
			e.setMessage(API.toChatMessage(e.getMessage()));
		}
	}

	@EventHandler
	public void event(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (HG.state != HGState.GAMING && !p.hasPermission("hg.spectate")) {
			e.disallow(Result.KICK_OTHER, "§6O jogo esta em andamento e para assitir voce precisa ser Vip!");
			;
		}
	}

	public static int damageReduction = 2;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (API.isUsing(p, "SWORD")) {
				double calc = e.getDamage() - damageReduction;
				e.setDamage(calc <= 1 ? 1 : calc);
				Bukkit.broadcastMessage("damage: " + e.getDamage());
			}
		}
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		API.refreshAll(p);
		if (HG.state == HGState.STARTING) {
			HG.kit.giveItems(p);
		} else {
			HG.toSpectate(p);
		}

	}

	@EventHandler
	public void event(MapInitializeEvent e) {
		if (HG.state == HGState.RESTARTING) {
			MapView map1;
			for (MapRenderer render : (map1 = e.getMap()).getRenderers()) {
				map1.removeRenderer(render);
			}
			map1.addRenderer(new MapRenderer() {
				public void render(MapView view, MapCanvas map, Player player) {
					map.drawText(30, 10, MinecraftFont.Font, "Parabens");
					map.drawText(30, 20, MinecraftFont.Font, player.getName());
					map.drawText(30, 30, MinecraftFont.Font, "Voce venceu!");
					map.drawImage(15, 42,
							new ImageIcon(Main.plugin.getDataFolder().getPath() + "/bolo.png").getImage());
				}
			});
		}
	}

	@EventHandler
	public void event(ServerListPingEvent e) {
		if (HG.state == HGState.STARTING) {
			e.setMotd("§aAguardando Jogadores\n" + "§bPremio 2000 Cash");
		} else if (HG.state == HGState.STARTING) {
			e.setMotd("§cPartida em Andamento\n" + "§bMelhor Jogador §3<player>");
		} else if (HG.state == HGState.RESTARTING) {
			e.setMotd("§6Torneio acabou! §eVai reiniciar em..\n" + "§bVencedor §f<winner");
		}
	}

	@EventHandler
	public void event(BlockBreakEvent e) {
		if (HG.state != HGState.GAMING | HG.specs.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(BlockPlaceEvent e) {
		if (HG.state != HGState.GAMING | HG.specs.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(PlayerDropItemEvent e) {
		if (HG.state != HGState.GAMING | HG.specs.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (HG.state != HGState.GAMING | HG.specs.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
}
