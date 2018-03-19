package net.eduard.live;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ComandoReiniciar implements CommandExecutor {
	public static boolean reiniciando = false;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// if (sender instanceof Player) {
		// Player player = (Player) sender;
		//
		// }

		if (reiniciando == false) {
			sender.sendMessage("§aReinicimento ativado");
			reiniciando = true;
			new BukkitRunnable() {
				int chamadas = 2;

				@Override
				public void run() {
					chamadas--;
					Bukkit.broadcastMessage("§aO Servidor irá reiniciar em " + chamadas + " minutos.");
					if (chamadas == 0) {
						cancel();
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.kickPlayer("§cServidor fulano de tal reinciado");
						}
						new BukkitRunnable() {
							
							@Override
							public void run() {
								Bukkit.shutdown();
							}
						}.runTaskLater(Main.getPlugin(Main.class), 20);
						

					}
					// TODO Auto-generated method stub

				}
			}.runTaskTimer(Main.getPlugin(Main.class), 20, 20 * 5);
		} else {
			sender.sendMessage("§cJa esta reiniciando");
		}
		return false;
	}

}
