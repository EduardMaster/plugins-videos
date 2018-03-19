package net.eduard.live;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoTeste implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Eventos.verificarItens(p, 50, Material.DIAMOND)) {
				p.sendMessage("§aVoce tem diamantes");
			}else {
				p.sendMessage("§cVoce nao tem diamantes");
			}

		}
		return false;
	}

}
