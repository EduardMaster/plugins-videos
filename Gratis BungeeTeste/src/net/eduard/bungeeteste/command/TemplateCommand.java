
package net.eduard.bungeeteste.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.manager.CommandManager;

public class TemplateCommand extends CommandManager {

	public TemplateCommand() {
		super("comando");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
//		Player p = (Player)sender;
//		sender.sendMessage("§cEnviando mensagem para o Bungeecord");
//		String mensagem = Mine.getText(0, args);
//		 Main.getInstance().sendMessage(sender, "enviar-mensagem", mensagem);
//		 Main.getInstance().conectarMinigame(sender);
		if (sender instanceof Player) {
			Player p = (Player) sender;
//			BukkitControl.sendMessage(p, "connect-to-state", "1");	
		}
		
		
		return true;
	}

}
