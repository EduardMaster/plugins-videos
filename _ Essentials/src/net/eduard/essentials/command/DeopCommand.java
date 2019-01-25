
package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class DeopCommand extends CommandManager {
	public DeopCommand() {
		super("deop");
	}
	public String messageTarget = "§6Voce removeu Op do jogador §e$target";
	public String message = "§6Voce agora não é mais Op!";
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (Mine.existsPlayer(sender, args[0])) {
			Player target = Mine.getPlayer(args[0]);
			target.setOp(false);
			target.sendMessage(message);
			sender.sendMessage(
					messageTarget.replace("$target", target.getDisplayName()));
		}

		return true;
	}
}
