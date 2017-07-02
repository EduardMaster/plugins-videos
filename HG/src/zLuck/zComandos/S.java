package zLuck.zComandos;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Centro;
import zLuck.zUteis.Uteis;

public class S implements CommandExecutor{
	
	public static ArrayList<Player> s = new ArrayList<Player>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("s")) {
			if (Main.isStaff(p)) {
				if (!s.contains(p)) {
					s.add(p);
					Centro.sendCenteredMessage(p, "§7Você §aentrou §7no §cStaff Chat");
				} else {
					s.remove(p);
					Centro.sendCenteredMessage(p, "§7Você §csaiu §7do §cStaff Chat");
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
