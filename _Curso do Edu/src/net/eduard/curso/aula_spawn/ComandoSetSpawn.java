package net.eduard.curso.aula_spawn;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.curso.CursoMain;

public class ComandoSetSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;

			CursoMain.getConfigs().set("spawn", p.getLocation());
			CursoMain.getConfigs().saveConfig();
			p.sendMessage(ChatColor.GREEN + "O spawn foi setado");

		}
		return false;
	}

}
