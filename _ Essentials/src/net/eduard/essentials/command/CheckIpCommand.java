
package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class CheckIpCommand extends CommandManager {
	public String message = "§6Seu IP é: §a$ip";
	public String messageTarget = "§6O IP do jogador $player é: §e$ip";
	
	public CheckIpCommand() {
		super("checkip");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(message.replace("$ip", Mine.getIp(p)));
			} else
				return false;

		} else {
			if (Mine.existsPlayer(sender, args[0])) {
				Player target = Mine.getPlayer(args[0]);
				sender.sendMessage(messageTarget
						.replace("$player", target.getDisplayName())
						.replace("$ip", Mine.getIp(target)));
			}
		}
		return true;
	}

}
