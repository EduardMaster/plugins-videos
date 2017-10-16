package net.eduard.api.tutorial.sistemas;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;


public class AlterarMensagemMOTD implements Listener{
	
	@EventHandler
	public void quandoAlguemVerAMOTD(ServerListPingEvent e) {
		e.setMotd("§6Parabens Por Jogar Primeira linha\n§bAQUI Segunda Linha");
	}
}
