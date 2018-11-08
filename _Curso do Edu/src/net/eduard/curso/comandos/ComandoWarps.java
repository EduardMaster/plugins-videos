package net.eduard.curso.comandos;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.curso.apis.WarpAPI;

public class ComandoWarps implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		List<String> warps = WarpAPI.getWarps();
		if (warps.isEmpty()) {
			sender.sendMessage("§cNão tem warps setadas");
		} else {
			sender.sendMessage("§aWarps disponiveis: " + warps);
		}

		return true;
	}


}
