
package net.eduard.kitpvp.event;

import java.util.Iterator;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import net.eduard.api.API;
import net.eduard.api.dev.Tag;
import net.eduard.api.manager.EventAPI;
import net.eduard.kitpvp.KitPvP;
import net.eduard.kitpvp.Main;

public class KitPvPEvent extends EventAPI {

	@EventHandler
	public void event(PlayerMoveEvent e) {

	}

	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!KitPvP.chat) {
			p.sendMessage("§cO Chat esta desabilitado!");
			e.setCancelled(true);
			return;
		}
		String message = e.getMessage();
		if (!Main.kit.isCanRepeteWords()&&KitPvP.messages.containsKey(p)&&KitPvP.messages.get(p).equals(message)) {
			e.setCancelled(true);
			return;
		}
		KitPvP.messages.put(p, message);
		Tag tag = API.getTag(p);
		
		String staff = "";
		if (KitPvP.staffChat.contains(e.getPlayer())) {
			staff = API.toChatMessage(Main.kit.getStaff());
			Iterator<Player> i = e.getRecipients().iterator();
			while (i.hasNext()) {
				if (!i.next().hasPermission("kitpvp.staffchat"))
					i.remove();
			}
		}
		
		e.setFormat(API.toChatMessage(Main.kit.getFormat().replace("$staff", staff).replace("$player", p.getDisplayName())
				.replace("$message", e.getMessage()).replace("$prefix", tag.getPrefix())
				.replace("$suffix", tag.getSuffix())));

	}

	@EventHandler
	public void event(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void event(ServerListPingEvent e) {
		e.setMaxPlayers(Main.kit.getMaxPlayers());
		StringBuilder builder = new StringBuilder();
		for (String line : Main.kit.getMotd()) {
			builder.append(line + "\n");
		}
		e.setMotd(builder.toString());
	}

	@EventHandler
	public void event(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void event(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void event(PlayerDropItemEvent e) {
		if (KitPvP.canDrop.contains(e.getItemDrop().getItemStack().getType())) {
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void event(PlayerPickupItemEvent e) {
		if (KitPvP.canPickUp.contains(e.getItem().getItemStack().getType())) {
			return;
		}
		e.setCancelled(true);
		
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		e.setJoinMessage(Main.kit.getJoinMessage().replace("$player", e.getPlayer().getDisplayName()));
	}

	@EventHandler
	public void event(PlayerQuitEvent e) {
		e.setQuitMessage(Main.kit.getQuitMessage().replace("$player", e.getPlayer().getDisplayName()));
	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!KitPvP.pvp)e.setCancelled(true);
			Player p = (Player) e.getEntity();
			if (p.getGameMode() == GameMode.ADVENTURE)
				e.setCancelled(true);
			
			
		}
		
		
	}

	@EventHandler
	public void event(EntityExplodeEvent e) {
		e.setCancelled(true);
	}

}
