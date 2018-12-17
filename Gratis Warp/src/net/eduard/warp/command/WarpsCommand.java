
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.warp.WarpPlugin;

public class WarpsCommand implements CommandExecutor {
	public String message = "";
	public String messageEmpty = "§cNenhum Warp disponivel!";
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (WarpPlugin.getWarps().size() == 0) {
			sender.sendMessage("");

		} else {
			
			/// Não tenho certeza se funciona
			sender.sendMessage("§aWarps criados: " + WarpPlugin.getWarps().keySet().toString().replace("[", "").replace("]", ""));
		}
		return true;
	}

}
