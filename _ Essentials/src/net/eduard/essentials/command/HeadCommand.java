package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class HeadCommand extends CommandManager {
	
	public String message = "§aCabeça de $player adquirida com sucesso.";
	

	public HeadCommand() {
		super("head");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if (Mine.onlyPlayer(sender)) {
			if (args.length == 0) {
				sendUsage(sender);
			} else {

				String nickname = args[0];
				p.getInventory().addItem(Mine.newSkull("§aCabeça do " + nickname, nickname));
				p.sendMessage(message.replace("$player", nickname));

			}

		}

		return true;
	}

}
