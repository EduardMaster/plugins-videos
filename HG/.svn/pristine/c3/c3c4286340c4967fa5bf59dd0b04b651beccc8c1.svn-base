package zLuck.zComandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Schematic;
import zLuck.zUteis.Uteis;

public class Force implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("forcefeast")) {
			if (Main.isStaff(p)) {
				if (zLuck.estado == Estado.Jogo) {
					Schematic.SchematicFeast();
				    p.sendMessage(Uteis.prefix + " §aVoce spawnou o feast!");				
				}
				else {
					p.sendMessage(Uteis.prefix + " §c§oEstado do jogo nao compativel");
				}
			}
		}
		if (label.equalsIgnoreCase("forceminifeast")) {
			if (Main.isStaff(p)) {
				if (zLuck.estado == Estado.Jogo) {
					Schematic.SchematicMiniFeast();
					p.sendMessage(Uteis.prefix + " §aVoce spawnou o minifeast");
				} else {
					p.sendMessage(Uteis.prefix + " §c§oEstado do jogo nao compativel");
				}
			}
		}
		return false;
	}
}
