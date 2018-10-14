
package net.eduard.chat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.chat.Main;

public class ToggleTellCommand extends CommandManager {


	
	 
	public ToggleTellCommand() {
		super("toggletell");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Main.getInstance().getChat().getTellDisabled().contains(p)) {
				Main.getInstance().getChat().getTellDisabled().remove(p);
				p.sendMessage("§cVocê desativou mensagens privadas");
			}else {
				Main.getInstance().getChat().getTellDisabled().add(p);
				p.sendMessage("§aVocê ativou mensagens privadas");
			}
			
		}
		return true;
	}


}
