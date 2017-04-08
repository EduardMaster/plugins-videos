package net.eduard.api.tutorial;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class AlterarMensagemMOTD {
	
	@EventHandler
	public void quandoAlguemVerAMOTD(ServerListPingEvent e) {
		e.setMotd("§6Parabens Por Jogar Primeira linha\n§bAQUI Segunda Linha");
	}
}
