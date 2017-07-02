package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Centro;
import zLuck.zUteis.Uteis;

public class Tp implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("tp")) {
			if (Main.isStaff(p)) {
				if (args.length == 0) {
					p.sendMessage("§cUse /tp [jogador]");
					return true;
				}
				if (args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if (t != null) {
						p.teleport(t);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Teleportado com §asucesso §7para §6" + t.getDisplayName());
					} else {
						Centro.sendCenteredMessage(p, "§7[§4✖§7] §cJogador offline ou inexistente.");
					}
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		if (label.equalsIgnoreCase("tpall")) {
			if (Main.isStaff(p)) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.teleport(p);
					Centro.sendCenteredMessage(all, "§7[§a§l✔§7] Todos foram teleportados por §a" + p.getDisplayName());
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		if (label.equalsIgnoreCase("puxar")) {
			if (Main.isStaff(p)) {
				if (args.length == 0) {
					Centro.sendCenteredMessage(p, "§7[§4✖§7] §cDigite /Puxar <Jogador>");
					return true;
				}
				if (args.length == 1) {
					Player t = Bukkit.getPlayer(args[0]);
					if (t != null) {
						t.teleport(p);
						Centro.sendCenteredMessage(p, "§7[§a§l✔§7] Você puxou §6" + t.getDisplayName());
						Centro.sendCenteredMessage(t, "§7[§b§l✔§7] Você foi puxado por §6" + p.getDisplayName());
					} else {
						Centro.sendCenteredMessage(p, "§7[§4✖§7] §cJogador offline ou inexistente.");
					}
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
