package net.eduard.curso.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoFly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§aApenas para players!");
		} else {

			Player p = (Player) sender;
			// /fly NOME
			if (args.length == 0) {

				if (p.getAllowFlight()) {

					p.setAllowFlight(false);
					p.sendMessage("§aVoce desativou o modo voo!");

				} else {

					p.setAllowFlight(true);

					p.sendMessage("§aVoce ativou o modo voo!");

				}

			} else {
				Player alvo = Bukkit.getPlayerExact(args[0]);
				if (alvo == null) {
					p.sendMessage("§aJogador não encontrado!");
				}else {
					if (alvo.getAllowFlight()) {
						alvo.setAllowFlight(false);
						
					}else {
						
					}
				}
			}

		}

		return true;
	}

}
