package zLuck.zComandos;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Centro;
import zLuck.zUteis.Uteis;

public class Gm implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("gm")) {
			if (Main.isStaff(p)) {
				if (args.length == 0) {
					if (p.getGameMode() == GameMode.SURVIVAL) {
						p.setGameMode(GameMode.CREATIVE);
						Centro.sendCenteredMessage(p, "§7Modo de jogo alterado para §aCriativo");
					} else {
						p.setGameMode(GameMode.SURVIVAL);
						Centro.sendCenteredMessage(p, "§7Modo de jogo alterado para §6Survival");
					}
				} else {
					p.sendMessage("§c§oUse somente /gm");
				}
			} else{
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
