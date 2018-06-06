package net.eduard.curso.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.curso.Main;


public class ComandoDeleteWarp implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if (args.length == 0) {
			
			sender.sendMessage("§c/deletewarp <nome>");
			//
			
		}else {
			
			String nome = args [0];
			
			
			
			
			Main.config.remove("Warps."+ nome );
			Main.config.saveConfig();
			
			sender.sendMessage("§aA warp " + nome  + " foi deletada!");
				
			
			
		}
	
	
		
		
		
		
		
		
//		if (args.length == 0) {
//			sender.sendMessage("§cDigite /delwarp <nome>");
//			
//		} else {
//			
//			String nome = args[0];
//			Main.config.remove("Warps."  + nome);
//			Main.config.saveConfig();
//			sender.sendMessage("§AWarp deletada com sucesso!");
//		}

		return true;
	}
	
	// @Override
		// public boolean onCommand(CommandSender sender, Command command,
		// String label, String[] args) {
		// if (args.length == 0) {
		// sender.sendMessage("§cDigite /delwarp <nome>");
		// } else {
		// String nome = args[0];
		// if (Main.config.contains("Warps." + nome)) {
		// Main.config.remove("Warps."+nome);
		// sender.sendMessage("§AWarp deletada com sucesso!");
		// }else {
		// sender.sendMessage("§cEsta warp não existe!");
		// }
		// }
		// return true;
		// }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
