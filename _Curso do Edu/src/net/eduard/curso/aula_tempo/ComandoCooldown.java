package net.eduard.curso.aula_tempo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.curso.CursoMain;

public class ComandoCooldown implements CommandExecutor {

	public static ArrayList<Player> jogadoresEmCooldown = new ArrayList<>();


	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (jogadoresEmCooldown.contains(p)) {
				sender.sendMessage("§cVocê esta em cooldown");
			} else {

				jogadoresEmCooldown.add(p);
				sender.sendMessage("§aVocê executou o comando e agora esta em cooldown.");
				new BukkitRunnable() {
					
					public void run() {

						sender.sendMessage("§cVocê pode usar o comando novamente.");
						jogadoresEmCooldown.remove(p);
					}
				}.runTaskLater(CursoMain.getInstance(), 20 * 10);

			}

		}

		return true;
	}

}
