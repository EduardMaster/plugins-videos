package zLuck.zComandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Game implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    Player p = (Player)sender;
	    
	    if (label.equalsIgnoreCase("game")) {
	        if (zLuck.estado == Estado.Iniciando) {	        	
                p.sendMessage(Uteis.prefix + " §7O torneio ainda nao iniciou.");
	        }
		    if (zLuck.estado == Estado.Proteção) {
		        p.sendMessage("§c§lJogadores: §7" + Arrays.jogador.size());
		    	p.sendMessage("§c§lKit: §7" + KitAPI.kit.get(p));
		    	p.sendMessage("§c§lInvencibilidade §7" + Uteis.numeroComPontos(Arrays.tempoA2));
		    	p.sendMessage("§c§lKills: §7" + Arrays.kills.get(p.getName()));
		    	p.sendMessage("§c§lIP: §7" + zLuck.mensagens.getString("IP"));
		    }
		    if (zLuck.estado == Estado.Jogo) {
		    	p.sendMessage("§c§lJogadores: §7" + Arrays.jogador.size());
		    	p.sendMessage("§c§lKit: §7" + KitAPI.kit.get(p));
		    	p.sendMessage("§c§lTempo de jogo: §7" + Uteis.numeroComPontos(Arrays.tempoA3));
		    	p.sendMessage("§c§lKills: §7" + Arrays.kills.get(p.getName()));
		    	p.sendMessage("§c§lIP: §7" + zLuck.mensagens.getString("IP"));
		    }
	    }
		return false;
	}
}
