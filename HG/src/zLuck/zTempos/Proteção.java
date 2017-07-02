package zLuck.zTempos;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import zLuck.zMain.zLuck;
import zLuck.zScoreboard.Scoreboards;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Partida;
import zLuck.zUteis.Uteis;

public class Proteção {
	
	public Proteção() {
		Arrays.tempo2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(zLuck.getPlugin(), new Runnable() {
			public void run() {
				Arrays.tempoA2--;
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (!Scoreboards.score.contains(all)) {
					    Scoreboards.setarProteção(all);
					}
				}
				if (Arrays.tempoA2 == 60) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Protecao acaba em §c1 §7minuto");
				}
				if (Arrays.tempoA2 == 30) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Protecao acaba em §c30 §7segundos");
				}
				if (Arrays.tempoA2 == 10) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Protecao acaba em §c10 §7segundos");
				}
				if (Arrays.tempoA2 == 0) {
					if (Arrays.jogador.size() == 0) {
						Bukkit.shutdown();
					} else {
						Partida.IniciarJogo();
					}
				}
			}			
		}, 0, 20);
	}
	
}
