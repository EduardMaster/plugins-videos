package zLuck.zTempos;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import zLuck.zMain.zLuck;
import zLuck.zScoreboard.Scoreboards;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Partida;
import zLuck.zUteis.Uteis;

public class Iniciando {
	
	public Iniciando() {
    	Arrays.tempo1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(zLuck.getPlugin(), new Runnable() {    
			public void run() {
				Arrays.tempoA1--;
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!Scoreboards.score.contains(p)) {
						Scoreboards.setarInicio(p);
					}
				}
				if (Arrays.tempoA1 == 60) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Torneio iniciando em §c§l1 §7minuto");
				}
				if (Arrays.tempoA1 == 5) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Torneio iniciando em §c§l5 §7segundos");
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
					}
				}
				if (Arrays.tempoA1 == 4) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Torneio iniciando em §c§l4 §7segundos");
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
					}
				}
				if (Arrays.tempoA1 == 3) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Torneio iniciando em §c§l3 §7segundos");
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
					}
				}
				if (Arrays.tempoA1 == 2) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Torneio iniciando em §c§l2 §7segundos");
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
					}
				}
				if (Arrays.tempoA1 == 1) {
					Bukkit.broadcastMessage(Uteis.prefix + " §7Torneio iniciando em §c§l1 §7segundo");
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);
					}
				}
				if (Arrays.tempoA1 == 0) {
					if (Arrays.jogador.size() < 3) {
						Arrays.tempoA1 = 60;
						Bukkit.broadcastMessage(Uteis.prefix + " §cJogadores insuficientes para iniciar o torneio");
					} else {
						Partida.IniciarProteção();
					}
				}
			}			
		}, 0, 20);
	}
	
}
