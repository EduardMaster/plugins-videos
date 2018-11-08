package net.eduard.essentials.event;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.eduard.api.lib.manager.EventsManager;

public class ReduceChatLag extends EventsManager {
	public static HashMap<Player, Long> delay = new HashMap<>();
	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("comando.rapido")) {
			if (delay.containsKey(p)) {
				Long teste = delay.get(p);
				long agora = System.currentTimeMillis();
				boolean test = agora > (teste + 1000 * 3);
				if (!test) {
					p.sendMessage(
							"§cEspere um momento para digitar o comando novamente!");
					e.setCancelled(true);

				} else {
					delay.put(p, System.currentTimeMillis());
				}
			} else {
				delay.put(p, System.currentTimeMillis());
			}
		}
	}

}
