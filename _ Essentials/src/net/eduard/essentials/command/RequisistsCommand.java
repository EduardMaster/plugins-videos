package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class RequisistsCommand extends CommandManager {

	public List<String> messages = new ArrayList<>();

	public RequisistsCommand() {
		super("requisits", "requisitos");
		messages.add("§aYoutuber 10 mil Inscritos");
		// TODO Auto-generated constructor stub
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		for (String msg : messages) {
			sender.sendMessage(msg);
		}
		return true;
	}

}
