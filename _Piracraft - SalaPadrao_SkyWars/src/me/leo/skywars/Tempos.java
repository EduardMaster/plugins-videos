package me.leo.skywars;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.PiraCraftAPI;

public class Tempos {

	public static int TaskPre;
	public static int TempoPre = 120;

	@SuppressWarnings("deprecation")
	public static void PreJogo() {
		TaskPre = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreBoardBolada.scoreBoard(p);
				}
				if (Main.jogadores.size() < Main.MinJogador) {
					TempoPre = 120;
					return;
				}
				if (Main.jogadores.size() == Bukkit.getMaxPlayers()) {
					if (TempoPre > 20) {
						TempoPre = 20;
						Bukkit.broadcastMessage(
								"§eSkyWars §b» §aTempo da partida alterado para §220 Segundos §a, pois a sala esta lotada!");
					}
				}

				if (Main.jogadores.size() >= Main.MinJogador) {
					if (TempoPre > 45) {
						TempoPre = 45;
						Bukkit.broadcastMessage(
								"§eSkyWars §b» §aTempo da partida alterado para §245 Segundos §a, pois a sala esta com "
										+ Bukkit.getOnlinePlayers().size() + " jogadores!");
					}
				}
				if (TempoPre == 0) {
					iniciar();
				}
				TempoPre = TempoPre - 1;

			}
		}, 0, 20);
	}

	public static void iniciar() {
		Bukkit.getScheduler().cancelTask(TaskPre);

		PiraCraftAPI.setPlaying(Bukkit.getPort(), 2, false);

		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.playSound(pl.getLocation(), Sound.AMBIENCE_CAVE, 30.0F, 30.0F);
			ScoreBoardBolada.removeScore(pl);
			Texts.sendTitle(pl, "§c§lSkyWars", "§ePartida iniciada.", Integer.valueOf(10), Integer.valueOf(40),
					Integer.valueOf(20));
		}
		Main.atual = Main.status.Invencibilidade;
		Bukkit.broadcastMessage("§eSkyWars §b» §aPartida Iniciada!");
		// int i = 0;
		Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
		// Bukkit.getWorld("world").setTime(1000);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20D);
			p.teleport(Pos.tpLoc(Eventos.numero.get(p)));
			p.setBedSpawnLocation(p.getLocation());
		}
		// Resolve o bug de sair da jaula :D
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.setGameMode(GameMode.SURVIVAL);
					p.teleport(Pos.tpLoc(Eventos.numero.get(p)));
				}
			}
		}.runTaskLater(Main.getInstance(), 5);
		Inv();
	}

	public static int TaskInv;
	public static int TempoInv = 45;

	@SuppressWarnings("deprecation")
	public static void Inv() {
		TaskInv = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreBoardBolada.scoreBoard(p);
				}
				if (TempoInv == 30) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						Texts.sendTitle(p, "§c§lSkyWars", "§aBoa sorte a todos!", 10, 40, 20);
					}
					// Main.colarSchen("plugins/WorldEdit/schematics/ilhas2.schematic",
					// new Location(Bukkit.getWorld("world"), 300, 80, 300));
					Jogador.removeBlocoBellow();
				}
				if (TempoInv == 0) {
					iniciarJogo();
				}
				if (Main.jogadores.size() == 0) {
					Bukkit.shutdown();
				}
				TempoInv = TempoInv - 1;

			}
		}, 0, 20);
	}

	public static void iniciarJogo() {
		Bukkit.getScheduler().cancelTask(TaskInv);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 30.0F, 30.0F);
			ScoreBoardBolada.removeScore(pl);

			Texts.sendTitle(pl, "§c§lSkyWars", "§aPvP liberado!", 10, 40, 20);
		}
		Main.atual = Main.status.Jogo;
		Jogo();
	}

	public static int TaskJogo;
	public static int TempoJogo = 0;

	@SuppressWarnings("deprecation")
	public static void Jogo() {
		TaskJogo = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ScoreBoardBolada.scoreBoard(p);
				}
				if (Main.jogadores.size() == 0) {
					Bukkit.shutdown();
				}

				if (Main.jogadores.size() == 1) {
					acabar();
				}

				if (TempoJogo == 900) {
					if (Bukkit.getOnlinePlayers().size() > 0) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							Jogador.bungeeSend(p, "lobby");
						}
					}
					new BukkitRunnable() {
						public void run() {
							Bukkit.shutdown();
						}
					}.runTaskLater(Main.getInstance(), 60);
				}
				if (TempoJogo == 600) {
					Bukkit.broadcastMessage("§eSkyWars §b» §aEm 5 minutos o servidor sera reiniciado!");
				}
				TempoJogo = TempoJogo + 1;

			}
		}, 0, 20);
	}

	public static void acabar() {
		Bukkit.getScheduler().cancelTask(TaskJogo);
		Jogador.setarXPWinner(Bukkit.getPlayer(Main.jogadores.get(0)));

		if (Main.spec.size() > 0) {
			for (String n : Main.spec) {
				Player s = (Player) Bukkit.getPlayer(n);

				Texts.sendTitle(s, "§c§lJOGO FINALIZADO",
						"§7" + Bukkit.getPlayer(Main.jogadores.get(0)) + " ganhou a partida.", 10, 60, 20);
			}
		}

		if (Main.jogadores.size() == 1) {
			Texts.sendTitle(Bukkit.getPlayer(Main.jogadores.get(0)), "§e§lVITORIA", "§7Voce venceu!", 10, 200, 20);
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setGameMode(GameMode.CREATIVE);
			ScoreBoardBolada.removeScore(p);
		}

		new BukkitRunnable() {

			@Override
			public void run() {
				if (Main.jogadores.size() == 1) {
					// ItemAPI.criar(Bukkit.getPlayer(Main.jogadores.get(0)),
					// Type.BALL_LARGE);
					Bukkit.getPlayer(Main.jogadores.get(0)).getWorld()
							.spawn(Bukkit.getPlayer(Main.jogadores.get(0)).getLocation(), Firework.class);
				} else {
					cancel();
				}

			}
		}.runTaskTimer(Main.getInstance(), 0, 10);

		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Jogador.bungeeSend(p, "lobby");
				}
			}
		}.runTaskLater(Main.getInstance(), 8 * 20);

		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.shutdown();
			}
		}.runTaskLater(Main.getInstance(), 10 * 20);
	}

}
