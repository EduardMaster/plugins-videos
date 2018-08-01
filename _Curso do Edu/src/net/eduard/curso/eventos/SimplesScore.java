package net.eduard.curso.eventos;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.game.DisplayBoard;

public class SimplesScore extends BukkitRunnable implements Listener {

	private static HashMap<Player, DisplayBoard> scoreboards = new HashMap<>();

	
	
	
	
	
	@EventHandler
	public void aoEntrar(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		setScore(p);

//		String mensagem = Main.config.message("morte mensagem");
//		p.sendMessage(mensagem);

	}
	public static void ligar(JavaPlugin plugin) {
		SimplesScore simpleScore = new SimplesScore();
		simpleScore.runTaskTimerAsynchronously(plugin, 20, 20);
		Bukkit.getPluginManager().registerEvents(simpleScore, plugin);;
	}
	
	public static void setScore(Player p) {
		DisplayBoard scoreboard = new DisplayBoard("§6§lMEU SERVER", "§aLINHA1", "§aLinha2", "", "§aLinha4");
		scoreboard.apply(p);
		scoreboards.put(p, scoreboard);
	}

	@EventHandler
	public void aoSair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		scoreboards.remove(p);

	}

	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			atualizar(p);
		}
	}

	

	public static  void atualizar(Player p) {

		DisplayBoard scoreboard = scoreboards.get(p);
		if (scoreboard == null)
			return;
		scoreboard.setDisplay("§a§l" + p.getName());
		scoreboard.add("linha 1");
//		scoreboard
//				.setLines(Arrays.asList("§aNivel; ", "§b" + p.getLevel(), "§aXp: ", "§f" + p.getTotalExperience(), ""));
		scoreboard.update();

	}

}
