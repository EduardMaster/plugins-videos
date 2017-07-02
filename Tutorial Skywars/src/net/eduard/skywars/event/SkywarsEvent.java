
package net.eduard.skywars.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.skywars.manager.Room;
import net.eduard.skywars.manager.RoomState;
import net.eduard.skywars.manager.Skywars;

public class SkywarsEvent implements Listener {

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
	}
	@EventHandler
	public void event(EntityDamageEvent e){
		if (e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if (Skywars.isPlaying(p)){
				Room game = Skywars.getGame(p);
				if (e.getCause()== DamageCause.FALL){
					if (game.getState() == RoomState.PLAYING){
						e.setCancelled(true);
					}
				}
				
			}
			
		}
	}
	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		e.setMessage(
				ChatColor.translateAlternateColorCodes('&', e.getMessage()));

	}
}
