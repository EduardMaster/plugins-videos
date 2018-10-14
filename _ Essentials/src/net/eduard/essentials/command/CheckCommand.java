package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class CheckCommand extends CommandManager {

	private List<String> messages = new ArrayList<>();

	public CheckCommand() {
		super("check", "info");
		setUsage("§c/check <player>");
		messages.add("");
		messages.add("§7Informações - $player_name");
		messages.add("");
		messages.add("§7 » Modo de jogo: §b§l$player_mode");
		messages.add("§7 » Fome: §b$player_food");
		messages.add("§7 » Vida: §b$player_health");
		messages.add("");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			sendUsage(sender);
		} else {
			if (Mine.existsPlayer(sender, args[0])) {
				Player player = Mine.getPlayer(args[0]);
				for (String msg : messages) {
					sender.sendMessage(Mine.getReplacers(msg, player));
				}
			}
		}

		return false;
	}
}
