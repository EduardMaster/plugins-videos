
package net.eduard.api.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class WhiteListOnCommand extends CMD {

	public String message = "§6Voce ativou a Lista §fBranca";

	public WhiteListOnCommand() {
		super("on", "enable");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Bukkit.setWhitelist(true);
		ConfigSection.chat(sender,message);
		return true;
	}

}
