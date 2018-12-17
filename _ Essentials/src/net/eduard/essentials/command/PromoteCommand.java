package net.eduard.essentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//import me.smashapi.apis.MessageAPI;
//import me.smashapi.apis.SimbolosAPI;

public class PromoteCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("promover")) {
			if (p.hasPermission("smash.promover")) {

				if (args.length == 0) {
					p.sendMessage("§cPor favor, use /promover <jogador> <cargo>");
					return true;
				}

				if (args.length == 1) {

					p.sendMessage("§cPor favor, use /promover <jogador> <cargo>");

				} else {

					Player target = Bukkit.getPlayer(args[0]);
					String cargo = args[1];

					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
							"pex user " + target.getName() + " group set " + cargo);

//					p.sendMessage(
//							SimbolosAPI.seta + "Você promoveu o jogador " + target.getName() + " para " + cargo + ".");
//					target.sendMessage(SimbolosAPI.seta + "Você foi promovido para " + cargo + ".");

					target.getWorld().strikeLightningEffect(target.getLocation());
					target.getWorld().strikeLightningEffect(target.getLocation());
					target.getWorld().strikeLightningEffect(target.getLocation());

					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("§e * §e§lNOVIDADE");
					Bukkit.broadcastMessage("§e * Parabêns: §7" + target.getName() + " §efoi promovido para §7" + cargo);
					Bukkit.broadcastMessage("");

				}

			} else {

//				MessageAPI.semPermissao(p, "Administrador");

			}
		}

		return false;
	}
}
