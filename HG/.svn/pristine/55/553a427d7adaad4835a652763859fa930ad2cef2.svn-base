package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Desistir implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        String nome = "§7[§4§l卐§7] [§c§l"+p.getDisplayName()+"§7] §7(§c§l" + KitAPI.kit.get(p) + "§7)";
		
		if (label.equalsIgnoreCase("desistir")) {
			if (zLuck.estado != Estado.Iniciando) {
				if (Arrays.jogador.contains(p)) {
					if (Main.isStaff(p) || Main.isVip(p)) {
						Bukkit.broadcastMessage(nome + " §7desistiu do torneio!");
						Uteis.EntrarSpec(p);
					} else {
						Bukkit.broadcastMessage(nome + " §7desistiu do torneio!");
			    	    p.kickPlayer("§cVoce desistiu do torneio\nPara espectar compre §a§lVIP §cem nossa loja");
					}
				} else {
					p.sendMessage("§cVoce nao esta in-game");
				}
			} else{
				p.sendMessage(Uteis.prefix + " §c§oEstado de jogo nao compativel");
			}
		}
		return false;
	}
	
}
