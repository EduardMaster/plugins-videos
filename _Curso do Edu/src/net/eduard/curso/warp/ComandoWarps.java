package net.eduard.curso.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ComandoWarps implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		
		
		
		
		
		
//		if (Main.config.contains("Warps")) {
//
//			// teste, loja, vip , vip2
//
//			Set<String> warps = Main.config.getSection("Warps").getKeys(false);
//			sender.sendMessage("§aWarps disponiveis: " + warps);
//
//		} else {
//			
//
//		        
//		        
//			
//			sender.sendMessage("§cNão tem warps setadas");
//
//		}

		return true;
	}

	//
	// @Override
	// public boolean onCommand(CommandSender sender, Command command,
	// String label, String[] args) {
	// if (Main.config.contains("Warps")) {
	// Set<String> warps = Main.config.getSection("Warps").getKeys(false);
	// sender.sendMessage("§aWarps disponiveis: "+warps);
	//
	// }else {
	//
	// sender.sendMessage("§cNão existe nenhuma Warp!");
	// }
	// return true;
	// }

}
