package net.eduard.curso.login;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.curso.Main;

public class ComandoLogin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (Main.registrados.containsKey(p.getUniqueId())) {

				// login senha

				if (args.length == 0) {
					// login
					sender.sendMessage("§c/login SENHA");

				} else {
					String digitou = args[0];
					String senha = Main.registrados.get(p.getUniqueId());
					if (digitou.equals(senha)) {

						Main.logados.put(p, System.currentTimeMillis());
						p.sendMessage("§aAutenticação feita com sucesso!");

					} else {
						p.kickPlayer(
								"§cVoce deve acertar a senha de primeira por motivos de segurança!");
					}

				}

			} else {
				p.sendMessage("§cVoce não esta registrado ainda por "
						+ "favor digite /register senha confirmar");
			}

		}

		return true;
	}

}
