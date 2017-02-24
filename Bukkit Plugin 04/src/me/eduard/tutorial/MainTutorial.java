
package me.eduard.tutorial;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainTutorial extends JavaPlugin {

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("send")) {
				sender.sendMessage(ChatColor.GREEN + "Voce enviou uma mensagem!");
			}
			if (command.getName().equalsIgnoreCase("heal")) {
				Player p = (Player) sender;
				if (p.getHealth() < 20) {
					p.setHealth(20);
					p.setFireTicks(0);
					p.sendMessage(ChatColor.RED + "Voce foi healado!");
				} else {
					p.sendMessage(ChatColor.RED + "Voce nao pode ser healado!");
				}
			}
		}
		return false;
	}

	public void onDisable() {

		getLogger().info("Esse plugin foi desabilitado!");
	}

	public void onEnable() {

		getLogger().info("Esse plugin foi habilitado!");
	}
}
