package net.eduard.api.tutorial.sistemas;

import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import net.eduard.api.manager.Manager;

public class AlterarMensagemMOTD extends Manager{
	
	@EventHandler
	public void quandoAlguemVerAMOTD(ServerListPingEvent e) {
		e.setMotd("§6Parabens Por Jogar Primeira linha\n§bAQUI Segunda Linha");
	}
}
