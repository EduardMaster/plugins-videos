package net.eduard.live;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoMudarPlaca implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				Block bloco = p.getTargetBlock((Set<Material>)null, 100);
				
				if (bloco.getState() instanceof Sign) {
					Sign placa = (Sign) bloco.getState();
					
					// /setlinha linha texto
					if (args.length <= 1) {
						p.sendMessage("§a/mudarplaca [linha] [text]");
					}else {
						Integer linha = 0;
						
						try {
							 linha = Integer.valueOf(args[0]);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						if (linha<0) {
							linha = 0;
							
						}
						if (linha>3) {
							linha = 3;
						}
						String texto = args[1];
						
						placa.setLine(linha, ChatColor.translateAlternateColorCodes('&', texto));
						placa.update(true, true);
						p.sendMessage("§aA placa foi alterada");
						
						
					}
					
				}
				
			}
		return false;
	}

}
