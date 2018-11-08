package net.eduard.essentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nightessentials.utilidades.MessageAPI;
import nightclan.config.ExtraAPI;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EquipCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("s")) {
				if (p.hasPermission("nightmc.s")) {

					if (args.length == 0) {

						p.sendMessage("§cPor favor, use /s {Messagem}");

					} else {


						String prefix = PermissionsEx.getUser(p).getPrefix().replace("&", "§");
						String messagem = ExtraAPI.getText(0, args);
						String formato = "§d[§d§lS§d] " + prefix + "§7" + p.getName() + "§d:§f" + messagem.replace("&", "§");
						
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < args.length; i++) {
							sb.append(args[i]);
							if (i < args.length) {
								sb.append(" ");
							}
						}
						
						for (Player staff : Bukkit.getOnlinePlayers()) {
							if (staff.hasPermission("nightmc.s")) {
								staff.sendMessage(formato);
							}
						}
					}

				} else {

					MessageAPI.semPermissaoEssentials(p, "Ajudante");

				}
			}
		}
		return false;
	}
}
