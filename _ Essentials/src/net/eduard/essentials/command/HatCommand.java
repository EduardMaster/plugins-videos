package net.eduard.essentials.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;

public class HatCommand extends CommandManager {
	public String messageNotBlock = "Este item na mão não é um bloco";
	public String messageNotItem = "§cVocê precisa de um item em sua mão!";
	public String message = "§aAproveite o seu novo chapéu!";

	public HatCommand() {
		super("hat", "chapeu");
		// TODO Auto-generated constructor stub
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;
			if (p.getItemInHand() == null) {

				p.sendMessage(messageNotItem);
			} else {
				if (p.getItemInHand().getType().isBlock()) {

					p.getInventory().setHelmet(p.getItemInHand());
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					sender.sendMessage(message);
				} else {
					sender.sendMessage(messageNotBlock);
				}
			}

		}
		return false;
	}
}
