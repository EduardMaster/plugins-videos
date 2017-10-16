
package net.eduard.clickcounter.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;
import net.eduard.clickcounter.event.CPSCounter;

public class TestClickCommand extends CommandManager {

	public TestClickCommand() {
		super("clicktest");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player)sender;
			if (CPSCounter.getClicking().contains(p)) {
				p.sendMessage("§cVoce desativou o Contador de Cliques!");
				CPSCounter.getClicking().remove(p);
				CPSCounter.getClicks().remove(p);
			}else {
				CPSCounter.getClicking().add(p);
				p.sendMessage("§aVoce ativou o Contador de Cliques!");
			}
				
		}
		
		return true;
	}

}
