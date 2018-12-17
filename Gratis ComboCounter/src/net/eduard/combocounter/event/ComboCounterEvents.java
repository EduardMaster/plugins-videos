
package net.eduard.combocounter.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.combocounter.Main;

public class ComboCounterEvents extends EventsManager {

	@EventHandler
	public void event(PlayerMoveEvent e) {

	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
	}

	public static Map<Player, Long> ultimoTapa = new HashMap<>();

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player entity = (Player) e.getEntity();
			if (Main.getInstance().getCombos().containsKey(entity)) {
				entity.sendMessage("§aSeu combo final foi este: §2"
						+ Main.getInstance().getCombos().get(entity));
				Main.getInstance().getCombos().put(entity, 0);

			}

		}
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if (Main.getInstance().getCombos().containsKey(p)) {
				if (ultimoTapa.containsKey(p)) {
					Long tempo = ultimoTapa.get(p);
					long agora = Mine.getNow();
					if (tempo + 50 * 1 > agora) {
						return;
					}
				}
				int combo = 1;
				Main.getInstance().getCombos().put(p,
						combo = (Main.getInstance().getCombos().get(p) + 1));
				Mine.sendActionBar(p, "§aVoce acertou mais um combo! §cCombo atual: " + combo);
				ultimoTapa.put(p, System.currentTimeMillis());
			}

		}
	}

}
