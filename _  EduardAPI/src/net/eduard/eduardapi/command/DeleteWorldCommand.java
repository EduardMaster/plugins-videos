package net.eduard.eduardapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class DeleteWorldCommand extends CMD {
	public DeleteWorldCommand() {
		getCommand().setPermission("eduardapi.commands.deleteworld");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0)
			return false;
		String name = args[0];
		if (API.existsWorld(sender, name)) {
			API.deleteWorld(name);
			sender.sendMessage("§aO Mundo §2" + name + "§a foi deletado com sucesso!");
		}
		return true;
	}
}
