package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Uteis;

public class Cm implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("cm")) {
			if (Main.isStaff(p)) {
				if (Arrays.cm) {
					Arrays.cm = false;
					Bukkit.broadcastMessage(Uteis.prefix + " §aChat ativado");
				} else {
					Bukkit.broadcastMessage(Uteis.prefix + " §cChat desativado");
					Arrays.cm = true;
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
