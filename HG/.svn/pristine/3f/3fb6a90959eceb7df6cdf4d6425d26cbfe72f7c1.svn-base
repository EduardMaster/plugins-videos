package zLuck.zComandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import zLuck.zUteis.Uteis;

public class Ping implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    Player p = (Player)sender;
	    
	    if (label.equalsIgnoreCase("ping")) {
	    	int ping = ((CraftPlayer) p).getHandle().ping;
	    	
	    	p.sendMessage(Uteis.prefix + " §7o ping do servidor é de §b§l" + ping + "MS");
	    }
		return false;
	}
}
