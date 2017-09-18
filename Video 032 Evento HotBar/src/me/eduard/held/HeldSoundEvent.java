
package me.eduard.held;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class HeldSoundEvent implements Listener {

	@EventHandler
	public void HeldEventSound(PlayerItemHeldEvent e) {

		Player p = e.getPlayer();
		p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);

	}
}
