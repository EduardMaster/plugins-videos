package me.eduard.template;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SimplesEvento implements Listener{

	@EventHandler
	public void QuandoJogadorMover(PlayerMoveEvent evento) {
		Player jogador = evento.getPlayer();
		jogador.setLevel(1);
	}
}
