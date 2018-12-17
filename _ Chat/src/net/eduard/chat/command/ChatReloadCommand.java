
package net.eduard.chat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.chat.ChatPlugin;

public class ChatReloadCommand extends CommandManager {

	
	public ChatReloadCommand() {
		super("chatreload", "educhatreload");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		ChatPlugin.getInstance().reload();
		sender.sendMessage("§aSistema de chat recarregado");
		return true;
	}
}
