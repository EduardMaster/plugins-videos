package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class EduardDeleteWorldCommand extends SubCommands {

	public EduardDeleteWorldCommand() {
		super("delete","deletar");
	}
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {

		if (args.length == 1){
			return;
		}
		
		String name = args[0];
		if (API.existsWorld(sender, name)) {
			API.deleteWorld(name);
			sender.sendMessage(
					"§aO Mundo §2" + name + "§a foi deletado com sucesso!");
		}

	}
}
