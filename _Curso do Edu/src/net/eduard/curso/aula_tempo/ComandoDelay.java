package net.eduard.curso.aula_tempo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.curso.CursoMain;

public class ComandoDelay implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {

		sender.sendMessage("§aDelay iniciado");

		BukkitRunnable objeto = new BukkitRunnable() {

			public void run() {
				sender.sendMessage("§aDelay finalizado");
			}
		};
		
		objeto.runTaskLater(CursoMain.getInstance(), 20 * 5);
		
		return true;
	}

}
