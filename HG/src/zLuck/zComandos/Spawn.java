package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Spawn implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("spawn")) {
			if (zLuck.estado == Estado.Iniciando) {
		  	    double x = zLuck.mensagens.getDouble("Spawn.X");
		  	    double y = zLuck.mensagens.getDouble("Spawn.Y");
		  	    double z = zLuck.mensagens.getDouble("Spawn.Z");
		  	    
		  	    p.teleport(new Location(p.getWorld(), x, y, z));
		  	    p.setHealth(20.0);
				p.closeInventory();
		  	    Uteis.Hotbar(p);
				for (Player all : Bukkit.getOnlinePlayers()) {
					 p.showPlayer(all);
				}
				if (Arrays.treinopvp.contains(p)) {
					Arrays.treinopvp.remove(p);
				}
			} else {
				p.sendMessage(Uteis.prefix + " §c§oEstado de jogo nao compativel");
			}
		}
		return false;
	}
}
