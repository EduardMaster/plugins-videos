package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Centro;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Fly implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("fly")) {
			if (Main.isStaff(p) || Main.isVip(p)) {
				if (zLuck.estado == Estado.Iniciando) {
					if (args.length == 0) {
						if (p.getAllowFlight()) {
							p.setAllowFlight(false);
							Centro.sendCenteredMessage(p, "§7seu modo de voo foi §c§lDESATIVADO");
						} else {
							p.setAllowFlight(true);
							Centro.sendCenteredMessage(p, "§7Seu modo de voo foi §b§lATIVADO");
						}
					}
					if (args.length == 1) {
						Player t = Bukkit.getPlayer(args[0]);
						if (t == null) {
							p.sendMessage("§cJogador offline ou inexistente");
							return true;
						}
						if (t.getAllowFlight()) {
							t.setAllowFlight(false);
							Centro.sendCenteredMessage(t, "§7Seu modo de voo foi §c§LDESATIVADO");
							Centro.sendCenteredMessage(p, "§7Voce §c§lDESATIVOU §7o modo de voo de " + t.getDisplayName());
						} else {
							t.setAllowFlight(true);
							Centro.sendCenteredMessage(t, "§7Seu modo de voo foi §a§LATIVADO");
							Centro.sendCenteredMessage(p, "§7Voce §a§lATIVOU §7o modo de voo de " + t.getDisplayName());
						}
					}
				} else {
					p.sendMessage(Uteis.prefix + " §cEstado de jogo nao compativel");
				}
			} else{
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
