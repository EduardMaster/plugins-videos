package net.eduard.curso.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.curso.Main;

public class ComandoWarp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {

			Player p = (Player) sender;
		

			 if (args.length == 0) {
			 // /warp
			 p.sendMessage("§cdigite /warp <nome>");
			 } else {
			 // /warp <algumacoisa>
			 // /warp loja
			 String nome = args[0];
			 if (!p.hasPermission("warp." + nome)) {
			 p.sendMessage(
			 "§cVoce não tem permissão para ir a este Warp");
			 return true;
			 }
			
			 if (Main.config.contains("Warps." + nome)) {
			
			 // warp.loja
			
			
			
			 p.teleport(Main.config.getLocation("Warps." + nome));
			 p.sendMessage("§aVoce foi ate o warp " + nome);
			
			 } else {
			 p.sendMessage("§cEste warp não foi setado!");
			 }
			
			 }

		}

		return true;
	}

}
