
package net.eduard.api.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class WhiteListOffCommand extends CMD {

	public String message = "§6Voce desativou a Lista §fBranca";

	public WhiteListOffCommand() {
		super("off", "disable");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Bukkit.setWhitelist(false);
		ConfigSection.chat(sender,message);
		
		return true;
	}

}
