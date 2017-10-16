package net.eduard.api.command.map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;
import net.eduard.api.setup.Mine;

public class MapDeleteWorldCommand extends CommandManager {

	public MapDeleteWorldCommand() {
		super("delete","deletar");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 1){
			return false;
		}
		
		String name = args[0];
		if (API.existsWorld(sender, name)) {
			Mine.deleteWorld(name);
			sender.sendMessage(
					"§bEduardAPI §aO Mundo §2" + name + "§a foi deletado com sucesso!");
		}
		return true;
	}
}
