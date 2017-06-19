
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

public class UnBanCommand extends CMD {

	public String message = "§6O jogador §e$target §6foi desabanido!";
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		String name = args[0];
		OfflinePlayer target = Bukkit.getOfflinePlayer(name);
		target.setBanned(false);
		Cs.chat(sender,message.replace("$target", target.getName()));
		

		return true;
	}
}
