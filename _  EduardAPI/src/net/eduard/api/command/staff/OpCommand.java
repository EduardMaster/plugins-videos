
package net.eduard.api.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class OpCommand extends CMD {
	public String messageTarget = "§6Voce deu Op para o jogador §e$target";
	public String message = "§6Voce agora é Op!";
	public OpCommand() {
		super("op");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (API.existsPlayer(sender, args[0])) {
			Player target = API.getPlayer(args[0]);
			target.setOp(true);
			ConfigSection.chat(target,message);
			ConfigSection.chat(sender,
					messageTarget.replace("$target", target.getDisplayName()));
		}

		return true;
	}
}
