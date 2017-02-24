
package me.eduard.tutorial;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
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
					p.sendMessage(ChatColor.RED + "Voce foi healado!");
				} else {
					p.sendMessage(ChatColor.RED + "Voce nao pode ser healado!");
				}
			}
			if (command.getName().equalsIgnoreCase("feed")) {
				Player p = (Player) sender;
				if (p.getFoodLevel() < 20) {
					p.setFoodLevel(20);
					p.sendMessage(ChatColor.RED + "Sua fome foi saciada!");
				} else {
					p.sendMessage(ChatColor.RED + "Sua fome ja esta no maximo!");
				}
			}
		}
		return false;
	}

	public void onDisable() {

		getLogger().info("Esse plugin foi desabilitado!");
		// Não falei no video mais não precisa por isso
		// HandlerList.unregisterAll();
		HandlerList.unregisterAll();
	}

	public void onEnable() {

		getLogger().info("Esse plugin foi habilitado!");
		getServer().getPluginManager().registerEvents(new Eventos(), this);
	}
}
