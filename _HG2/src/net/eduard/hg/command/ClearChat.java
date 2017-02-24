
package net.eduard.hg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class ClearChat extends HGCommand  {

	public void command(CommandSender sender, String cmd, String... args) {
		String name = "§aConsole";
		if (sender instanceof Player) {
			Player p = (Player) sender;
			name = p.getDisplayName();
		}
		for (int id = 1; id <= HG.clearSize; id++) {
			Bukkit.broadcastMessage("");
		}
		Bukkit.broadcastMessage("§6O Chat foi limpo pelo " + name);
	}

}
