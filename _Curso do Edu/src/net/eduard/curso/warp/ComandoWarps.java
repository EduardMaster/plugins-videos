package net.eduard.curso.warp;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.curso.Main;

public class ComandoWarps implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (Main.config.contains("Warps")) {



			Set<String> warps = Main.config.getSection("Warps").getKeys(false);
			sender.sendMessage("§aWarps disponiveis: " + warps);

		} else {

			sender.sendMessage("§cNão tem warps setadas");

		}

		return true;
	}

	//
	// @Override
	// public boolean onCommand(CommandSender sender, Command command,
	// String label, String[] args) {
	// if (Main.config.contains("Warps")) {
	// Set<String> warps = Main.config.getSection("Warps").getKeys(false);
	// sender.sendMessage("§aWarps disponiveis: "+warps);
	//
	// }else {
	//
	// sender.sendMessage("§cNão existe nenhuma Warp!");
	// }
	// return true;
	// }

}
