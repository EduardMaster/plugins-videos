package zLuck.zComandos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import zLuck.zUteis.Uteis;

public class Tell implements CommandExecutor{
	
	private ArrayList<Player> tell = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("tell")) {
			if (args.length == 0) {
				p.sendMessage("§cUse /tell [jogador] [mensagem]");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (tell.contains(p)) {
						p.sendMessage(Uteis.prefix + " §a§lTell ativado");
						tell.remove(p);
					} else {
						p.sendMessage(Uteis.prefix + " §7Seu tell ja esta ativado");
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!tell.contains(p)) {
						p.sendMessage(Uteis.prefix + " §c§lTell desativado");
						tell.add(p);
					} else {
						p.sendMessage(Uteis.prefix + " §7Seu tell ja esta desativado");
					}
				} else {
					p.sendMessage("§cUse /tell " + args[0] + " [mensagem]");
					return true;
				}
			}
			if (args.length >= 2) {
				Player t = Bukkit.getPlayer(args[0]);
	            String msg = "";
	            for (int x = 1; x < args.length; x++) {
		            msg = msg + args[x] + " ";
	            }
				if (t == null) {
					p.sendMessage("§cJogador offline ou inexistente");
					return true;
				}
				if (!tell.contains(t)) {
					p.sendMessage("§bVoce para §a[" + t.getDisplayName() + "§a] §c» §7" + msg);		
					t.sendMessage("§a["+p.getDisplayName()+"§a] §c» §7" + msg);		
				} else {
					p.sendMessage(Uteis.prefix + " §cO tell do player esta desativado");
				}
			}
		}
		return false;
	}

}
