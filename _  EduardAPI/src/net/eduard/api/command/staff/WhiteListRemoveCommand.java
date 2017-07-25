
package net.eduard.api.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class WhiteListRemoveCommand extends CMD {
	public WhiteListRemoveCommand() {
		super("remove", "remover");
	}
	public String message = "§6Voce removeu o §e$player§6 da Lista §fBranca";
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length <= 1) {
			sender.sendMessage("§c/" + label + " " + args[0]+" <player>");
			return true;
		}

		String name = args[1];
		OfflinePlayer target = Bukkit.getOfflinePlayer(name);
		target.setWhitelisted(false);
		ConfigSection.chat(sender,message.replace("$player", target.getName()));
	
		return true;
	}

}
