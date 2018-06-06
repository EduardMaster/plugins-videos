package net.eduard.curso.scoreboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.game.DisplayBoard;

public class Scoreboards extends BukkitRunnable implements Listener {

	private HashMap<Player, DisplayBoard> scoreboards = new HashMap<>();

	@EventHandler
	public void aoEntrar(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		DisplayBoard scoreboard = new DisplayBoard("§6§lMEU SERVER", "§aLINHA1",
				"§aLinha2", "", "§aLinha4");
		scoreboard.apply(p);
		scoreboards.put(p, scoreboard);
		
//		String mensagem = Main.config.message("morte mensagem");
//		p.sendMessage(mensagem);
		
		
	}
	@EventHandler
	public void aoSair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		scoreboards.remove(p);

	}

	public void run() {
		update();
	}
	public void update() {

		for (Entry<Player, DisplayBoard> entry : scoreboards.entrySet()) {
			Player p = entry.getKey();
			DisplayBoard scoreboard = entry.getValue();
			scoreboard.setDisplay("§a§l" + p.getName());
			scoreboard.setLines(Arrays.asList("§aNivel; ", "§b" + p.getLevel(),
					"§aXp: ", "§f" + p.getTotalExperience(), ""));
			scoreboard.update();

		}

	}

}
