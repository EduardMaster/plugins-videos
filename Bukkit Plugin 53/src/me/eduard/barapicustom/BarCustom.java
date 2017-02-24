
package me.eduard.barapicustom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.confuser.barapi.BarAPI;

public class BarCustom implements Listener {

	@EventHandler
	public void BarCustomEvent(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		BarAPI.setMessage(p, "Essa é uma mensagem Custom no BARAPI", 5);

		// Cuidado a Versão que baixar do BARAPI tem uma que vem com bug no
		// /reload (erros no console mas nada que atrapalhe o funciomento do
		// plugin)

	}

	public void MensagemEterna(Player p) {

		BarAPI.setMessage(p, "Essa é uma mensagem Eterna");
	}

	public void MensagemMetadeDaVida(Player p) {

		BarAPI.setHealth(p, (float) 0.5);
	}

	public void MensagemNormal(Player p) {

		// Mensagem por 5 Segundo
		BarAPI.setMessage(p, "Normal Mensagem", 5);
		// Mensagem Eterna com metade da Barra Cheia (50%)
		BarAPI.setMessage(p, "Normal Mensagem", (float) 0.5);
	}
}
