package zLuck.zEventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;

public class Sair implements Listener{
	
	@EventHandler
	void aoSair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		
		if (zLuck.estado == Estado.Iniciando) {
			if (Arrays.treinopvp.contains(p)) {
				Arrays.treinopvp.remove(p);
			}
			if (Arrays.jogador.contains(p)) {
				Arrays.jogador.remove(p);
			}
			if (Arrays.spec.contains(p)) {
				Arrays.spec.remove(p);
			}
		}
		if (zLuck.estado == Estado.Proteção) {
			if (Arrays.treinopvp.contains(p)) {
				Arrays.treinopvp.remove(p);
			}
			if (Arrays.jogador.contains(p)) {
				Arrays.jogador.remove(p);
			}
			if (Arrays.spec.contains(p)) {
				Arrays.spec.remove(p);
			}
		}
		if (zLuck.estado == Estado.Jogo) {
			if (Arrays.treinopvp.contains(p)) {
				Arrays.treinopvp.remove(p);
			}
			if (Arrays.jogador.contains(p)) {
				Arrays.jogador.remove(p);
			}
			if (Arrays.spec.contains(p)) {
				Arrays.spec.remove(p);
			}
		}
	}

}
