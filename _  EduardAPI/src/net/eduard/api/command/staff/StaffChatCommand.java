
package net.eduard.api.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.chat.Chats;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class StaffChatCommand extends CMD {
	public StaffChatCommand() {
		super("staffchat");
	}
	public String staffTag = "[STAFF] ";
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;

			StringBuilder builder = new StringBuilder();
			int id = 0;
			for (String arg : args) {
				if (id != 0)
					builder.append(" ");
				else
					id++;

				builder.append(ConfigSection.toChatMessage(arg));
			}
			ConfigSection.broadcast(staffTag + p.getDisplayName() + Chats.getArrowRight()
					+ " " + builder.toString(), getCommand().getPermission());
		}
		return true;
	}

}
