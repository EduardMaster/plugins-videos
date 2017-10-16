
package net.eduard.api.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;
import net.eduard.api.setup.Mine;

public class OpCommand extends CommandManager {
	public String messageTarget = "§6Voce deu Op para o jogador §e$target";
	public String message = "§6Voce agora é Op!";
	public OpCommand() {
		super("op");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (API.existsPlayer(sender, args[0])) {
			Player target = Mine.getPlayer(args[0]);
			target.setOp(true);
			API.chat(target,message);
			API.chat(sender,
					messageTarget.replace("$target", target.getDisplayName()));
		}
		
		return true;
	}
}
