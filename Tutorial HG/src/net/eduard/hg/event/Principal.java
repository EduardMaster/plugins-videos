package net.eduard.hg.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;

import net.eduard.hg.Events;
import net.eduard.hg.HgPlugin;
import net.eduard.hg.setup.HgRoom;
import net.eduard.hg.setup.HgState;

public class Principal extends Events {

	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (HgPlugin.hg.getPlayers().contains(p)){
		}
	}
	@EventHandler
	public void showMotd(ServerListPingEvent e) {
		HgRoom room = HgPlugin.hg;
		HgState state = room.getState();
		if (state == HgState.PRE_GAME) {
			// lembre de por o "\n"
			e.setMotd(ChatColor.GREEN + "O torneio ira começar em "
					+ room.getTime() + " segundos!\n" + ChatColor.GOLD
					+ "O premio será de 500 cash e 20000 coins");
		} else if (state == HgState.NO_PVP) {
			e.setMotd(ChatColor.GREEN
					+ "O torneio começou! Assista agora mesmo!!\n"
					+ ChatColor.GOLD + "A invunerabilidade vai acabar em "
					+ room.getTime() + " segundos!");
		} else if (state == HgState.GAME) {
			e.setMotd(ChatColor.GREEN
					+ "O torneio começou! Assista agora mesmo!!\n"
					+ ChatColor.GOLD + "O torneio vai acabar em "
					+ room.getTime() + " segundos!");
		} else {
			e.setMotd(ChatColor.GREEN + "O torneio acabou! Vencedor:\n"
					+ ChatColor.GOLD + "" + room.getPlayers().get(0));
			// pega o ultimo player vivo
		}
	}
	@EventHandler
	public void event(PlayerLoginEvent e) {
		if (!(HgPlugin.hg.getState() == HgState.PRE_GAME)) {
			if (!e.getPlayer().hasPermission("hg.spectate")) {
				e.setKickMessage(ChatColor.GREEN
						+ "Para assistir voce deve possuir um pacote Vip");
				e.setResult(Result.KICK_OTHER);
				return;
			}
			HgPlugin.hg.getSpectators().add(e.getPlayer());
		} else
			HgPlugin.hg.getPlayers().add(e.getPlayer());
	}
	@EventHandler
	public void event(AsyncPlayerPreLoginEvent e) {
		if (!(HgPlugin.hg.getState() == HgState.PRE_GAME)) {
		}
	}
	@EventHandler
	public void noPvPBySpecs(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (HgPlugin.setup.getPlayers().containsKey(p)) {
				HgRoom room = HgPlugin.setup.getPlayers().get(p);
				if (room.getState() == HgState.GAME) {
					if (room.getSpectators().contains(p))
						e.setCancelled(true);
				}
			}

		}

	}
	@EventHandler
	public void removePvP(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (HgPlugin.setup.getPlayers().containsKey(p)) {
				HgState state = HgPlugin.setup.getPlayers().get(p).getState();
				if (state == HgState.PRE_GAME | state == HgState.NO_PVP
						| state == HgState.END_GAME) {
					e.setCancelled(true);
				}
			}
		}

	}
}
