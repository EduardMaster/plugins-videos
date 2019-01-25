
package net.eduard.chat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.chat.ChatPlugin;

public class TellCommand extends CommandManager {

	public String message = "§aPara: §f$target$> §7$message";
	public String messageTarget = "§aDe: §f$player$> §7$message";
	public String messageDisabled = "§cDe: §f$player desativou as mensagens";

	public TellCommand() {
		super("tell", "privado", "pm", "pv", "t");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {

			Player p = (Player) sender;
			if (args.length <= 1) {
				sendUsage(sender);
			} else {
				if (Mine.existsPlayer(sender, args[0])) {
					Player target = Mine.getPlayer(args[0]);
					String message = Mine.getText(1, args);
					if (!ChatPlugin.getInstance().getChat().getTellDisabled().contains(target)) {
						sender.sendMessage(messageDisabled.replace("$player", target.getName()));
					} else {
						ChatPlugin.getInstance().getLastPrivateMessage().put(target, p);
						sender.sendMessage( this.message.replace("$target", target.getName())
								.replace("$>", "").replace("$message", message));
						target.sendMessage( messageTarget.replace("$player", sender.getName())
								.replace("$>", "").replace("$message", message));
					}
				}
			}
		}
		return true;
	}
}
