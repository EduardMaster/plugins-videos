package net.eduard.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class WarpCommand extends CMD {
	public List<String> messages = new ArrayList<>();
	public WarpCommand() {
		messages.add(API.SERVER_TAG+" - Requisitos para ser Youtuber - ");

	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		for (String line : messages) {
			sender.sendMessage(line);
		}
		return true;
	}

}
