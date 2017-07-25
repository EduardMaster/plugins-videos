
package net.eduard.api.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.chat.Chats;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class TellCommand extends CMD {

	public String message = "§aPara: §f$target$> §7$message";
	public String messageTarget = "§aDe: §f$player$> §7$message";
	public String messageDisabled = "§cDe: §f$player desativou as mensagens";
	public static Map<Player, Boolean> toggle = new HashMap<>();
	public static void toggle(Player player) {
		toggle.put(player, !isToggle(player));

	}
	public static Boolean isToggle(Player player) {
		if (!toggle.containsKey(player)) {
			toggle.put(player, false);
		}
		return toggle.get(player);
	}
	public TellCommand() {
		super("tell", "privado");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length <= 1) {
			ConfigSection.chat(sender, getUsage());
		} else {
			if (API.existsPlayer(sender, args[0])) {
				Player target = API.getPlayer(args[0]);
				String message = ConfigSection.getText(1, args);
				if (!isToggle(target)) {
					ConfigSection.chat(sender, messageDisabled.replace("$player",
							target.getName()));
				} else {
					ConfigSection.chat(sender,
							this.message.replace("$target", target.getName())
									.replace("$>", Chats.getArrowRight())
									.replace("$message", message));
					ConfigSection.chat(target,
							messageTarget.replace("$player", sender.getName())
									.replace("$>", Chats.getArrowRight())
									.replace("$message", message));
				}
			}
		}
		return true;
	}
}
