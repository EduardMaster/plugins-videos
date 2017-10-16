
package net.eduard.api.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;
import net.eduard.api.setup.Mine;

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
				API.chat(p,message.replace("$ip", Mine.getIp(p)));
			} else
				return false;

		} else {
			if (API.existsPlayer(sender, args[0])) {
				Player target = Mine.getPlayer(args[0]);
				API.chat(sender,messageTarget
						.replace("$player", target.getDisplayName())
						.replace("$ip", Mine.getIp(target)));
			}
		}
		return true;
	}

}
