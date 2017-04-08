
package net.eduard.warp.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.eduard.warp.Main;

public class WarpEvent implements Listener {

	@EventHandler
	private void aoUsarComando(PlayerCommandPreprocessEvent e) {
		String warp = e.getMessage().replace("/", "").toLowerCase();
		if (Main.warps.containsKey(warp)) {
			e.setMessage("/warp "+warp);
		}
		// affs so burro pakas
		// So funciona se eu digita /Loja
		// Mais se digita /Loja aew galera
		// não funciona
	}
}
