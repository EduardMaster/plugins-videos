package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Tempo implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("tempo")) {
			if (Main.isStaff((Player) sender)) {
				if (args.length == 0) {
					sender.sendMessage("§cUse /tempo [tempo]");
					return true;
				}
				if (args.length == 1) {
					int tempo = Integer.parseInt(args[0]);
					if (tempo >= 10001) {
						sender.sendMessage("§cNao use numeros maiores que 10000");
						return true;
					}
					if (zLuck.estado == Estado.Iniciando) {
						Arrays.tempoA1 = tempo;
						Bukkit.broadcastMessage(Uteis.prefix + " §7Tempo alterado para §c§l" + tempo + " §7segundos");
					}
					if (zLuck.estado == Estado.Proteção) {
						Arrays.tempoA2 = tempo;
						Bukkit.broadcastMessage(Uteis.prefix + " §7Tempo alterado para §c§l" + tempo + " §7segundos");
					}
					if (zLuck.estado == Estado.Jogo) {
						Arrays.tempoA3 = tempo;
						Bukkit.broadcastMessage(Uteis.prefix + " §7Tempo alterado para §c§l" + tempo + " §7segundos");
					}
				}
			} else{
				sender.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
