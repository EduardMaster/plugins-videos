
package net.eduard.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CMD;

public class SlimeCommand extends CMD {
	public List<String> messages = new ArrayList<>();
	public SlimeCommand() {
		messages.add(API.SERVER_TAG+" - Requisitos para ser Youtuber - ");

	}
	
	public Config config = new Config("spawn.yml");
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if (args.length == 0) {
			for (String line : messages) {
				sender.sendMessage(line);
			}	
		}else {
			
		}
		
		
		return true;
	}

}
