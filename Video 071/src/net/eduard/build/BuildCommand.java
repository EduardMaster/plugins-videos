
package net.eduard.build;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para Players!");
			return true;
		}
		Player p = (Player) sender;
		if (Main.build.contains(p)) {
			Main.build.remove(p);
			p.sendMessage("§aVoce desativou o Build!");
		} else {
			Main.build.add(p);
			p.sendMessage("§aVoce ativou o Build!");
		}

		return false;
	}

}
