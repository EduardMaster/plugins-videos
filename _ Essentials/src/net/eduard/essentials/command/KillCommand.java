package net.eduard.essentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("kill")) {
				if (p.hasPermission("smash.kill")) {

					if (args.length == 0) {

//						p.sendMessage("§7" + SimbolosAPI.seta + "Você se matou.");
						p.setHealth(0.0D);

					} else {

						Player target = Bukkit.getPlayer(args[0]);

						if (target == null) {
							p.sendMessage("§cEste jogador está offline.");
							return true;
						}

						target.setHealth(0.0D);
//						target.sendMessage("§7" + SimbolosAPI.seta + "O jogador " + p.getName() + " te matou.");
//						p.sendMessage("§7" + SimbolosAPI.seta + "Você matou o jogador " + target.getName());

					}

				} else {

//					MessageAPI.semPermissao(p, "Administrador");

				}
			}
		}
		return false;
	}
}
