package net.eduard.chat.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.chat.Main;

public class ColorCommand extends CommandManager {

	public ColorCommand() {
		super("color", "cor");
		setUsage("§c/cor <cor>");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (args.length == 0) {
				sendUsage(sender);
			} else {

				String cor = args[0];

				if (cor.equalsIgnoreCase("reset")) {
					Main.getInstance().getChat().getColors().put(p, "");
					p.sendMessage("§aCor removida com sucesso!");
					return true;
				}

				Main.getInstance().getChat().getColors().put(p, cor);
				p.sendMessage("§aCor alterada com sucesso.");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

			}

		}
		return true;
	}

}
