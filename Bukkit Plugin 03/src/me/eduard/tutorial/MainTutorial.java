
package me.eduard.tutorial;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainTutorial extends JavaPlugin {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// se quem digitou o comando foi um jogador faz isso
		if (sender instanceof Player) {
			if (command.getName().equalsIgnoreCase("send")) {
				sender.sendMessage(ChatColor.GREEN + "Voce enviou uma mensagem!");
			}
			// se nao faz isso
		} else {
			// Nao ponha acentos na mensagens para o Console do Servidor porque
			// fica buggado
			sender.sendMessage(ChatColor.RED + "Comando so para jogadores!");
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
