package net.eduard.api.tutorial.sistemas;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class NaosVeremPlugins implements Listener {
	@EventHandler
	public void NaoProcurarOsPluginsDoServer(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("/pl")
			|| e.getMessage().equalsIgnoreCase("/plugins")
			|| e.getMessage().equalsIgnoreCase("/bukkit:?")
			|| e.getMessage().equalsIgnoreCase("/bukkit:plugins")
			|| e.getMessage().equalsIgnoreCase("/bukkit:help")
			|| e.getMessage().equalsIgnoreCase("/bukkit:pl")
			|| e.getMessage().equalsIgnoreCase("/ver")
			|| e.getMessage().equalsIgnoreCase("/help")
			|| e.getMessage().equalsIgnoreCase("/bukkit:ver")
			|| e.getMessage().equalsIgnoreCase("/logout")
			|| e.getMessage().equalsIgnoreCase("/?")) {
			p.sendMessage("§6Nao tente procurar nossos Plugins");
			e.setCancelled(true);
		}

	}
}
