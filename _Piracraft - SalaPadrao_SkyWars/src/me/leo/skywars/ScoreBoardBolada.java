package me.leo.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import me.leo.skywars.Main.status;

public class ScoreBoardBolada {

	public static void scoreBoard(Player p) {
		SSB s = new SSB("§7PiraCraft - SkyWars");
		if (Main.atual == status.PreJogo) {
			s.add("§0     ", 9);
			s.add("Jogador: §e" + p.getName(), 8);
			s.add("§1  ", 7);
			s.add("§e§lEstatisticas:", 6);
			s.add("§2              ", 5);
			s.add("Inicia: §e" + Main.tempo(Tempos.TempoPre), 4);
			s.add("Kit: §eNenhum", 3);
			s.add("Jogadores: §e" + Main.jogadores.size() + "/" + Bukkit.getMaxPlayers(), 2);
			s.add("Estagio: §e" + Main.tipo(), 1);
			s.add("§3 ", 0);
		}
		if (Main.atual == status.Invencibilidade) {
			s.add("§0     ", 9);
			s.add("Jogador: §e" + p.getName(), 8);
			s.add("§1  ", 7);
			s.add("§e§lEstatisticas:", 6);
			s.add("§2              ", 5);
			s.add("Invencivel: §e" + Main.tempo(Tempos.TempoInv), 4);
			s.add("Kit: §eNenhum", 3);
			s.add("Jogadores: §e" + Main.jogadores.size() + "/" + Bukkit.getMaxPlayers(), 2);
			s.add("Estagio: §e" + Main.tipo(), 1);
			s.add("§3 ", 0);
		}
		if (Main.atual == status.Jogo) {
			s.add("§0     ", 9);
			s.add("Jogador: §e" + p.getName(), 8);
			s.add("§1  ", 7);
			s.add("§e§lEstatisticas:", 6);
			s.add("§2              ", 5);
			s.add("Jogo: §e" + Main.tempo(Tempos.TempoJogo), 4);
			s.add("Kit: §eNenhum", 3);
			s.add("Jogadores: §e" + Main.jogadores.size() + "/" + Bukkit.getMaxPlayers(), 2);
			s.add("Estagio: §e" + Main.tipo(), 1);
			s.add("§3 ", 0);
		}

		s.build();
		s.send(p);
	}

	public static void removeScore(Player p) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		board.clearSlot(DisplaySlot.SIDEBAR);
		p.setScoreboard(board);
	}

}
