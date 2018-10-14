
package net.eduard.chat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.modules.SpigotAPI.Chat;
import net.eduard.chat.Main;

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
				Mine.chat(sender, getUsage());
			} else {
				if (Mine.existsPlayer(sender, args[0])) {
					Player target = Mine.getPlayer(args[0]);
					String message = Mine.getText(1, args);
					if (!Main.getInstance().getChat().getTellDisabled().contains(target)) {
						Mine.chat(sender, messageDisabled.replace("$player", target.getName()));
					} else {
						Main.getInstance().getLastPrivateMessage().put(target, p);
						Mine.chat(sender, this.message.replace("$target", target.getName())
								.replace("$>", Chat.getArrowRight()).replace("$message", message));
						Mine.chat(target, messageTarget.replace("$player", sender.getName())
								.replace("$>", Chat.getArrowRight()).replace("$message", message));
					}
				}
			}
		}
		return true;
	}
}
