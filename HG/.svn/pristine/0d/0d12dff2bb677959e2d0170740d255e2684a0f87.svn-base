package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Schematic;
import zLuck.zUteis.Uteis;

public class Arena implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {	
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (label.equalsIgnoreCase("arena")) {
				if (Main.isStaff(p)) {
					if (zLuck.estado == Estado.Jogo) {
						Schematic.SchematicArena();
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.teleport(new Location(all.getWorld(), 21.5D, 109.0D, 23.5D));
						}
						Bukkit.broadcastMessage(Uteis.prefix + " §aTodos os jogadores foram teleportados para a arena!");
					} else {
						sender.sendMessage(Uteis.prefix + " §cEstado de jogo nao compativel");
					}
				}
			}
		} else {
			if (label.equalsIgnoreCase("arena")) {
				if (zLuck.estado == Estado.Jogo) {
					Schematic.SchematicArena();
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.teleport(new Location(all.getWorld(), 21.5D, 109.0D, 23.5D));
					}
					Bukkit.broadcastMessage(Uteis.prefix + " §aTodos os jogadores foram teleportados para a arena!");
				} else {
					sender.sendMessage(Uteis.prefix + " §cEstado de jogo nao compativel");
				}
			}
		}
		return false;
	}

}
