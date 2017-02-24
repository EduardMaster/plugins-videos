
package net.eduard.hg.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HGCommand;

public class StaffChat extends HGCommand  {

	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			StringBuilder builder = new StringBuilder();
			for (String arg : args) {
				builder.append(" ");
				builder.append(arg);
				
			}
			String message = ChatColor.translateAlternateColorCodes('&', builder.toString());
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("command.staffchat")) {
					player.sendMessage("§8[§bSTAFF§8] §r" + p.getDisplayName() + "§f:§r" + message);
				}
			}

		}
	}

}
