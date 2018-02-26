package net.eduard.live.mobstuckers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CarneComando implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage("§a/carne <jogador> ");
		}else {
			Player jogador = Bukkit.getPlayer(args[0]);
			if (jogador == null) {
				sender.sendMessage("§cJogador offline");
			}else {
				jogador.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
				jogador.sendMessage("§aVoce recebeu algumas carnes");
			}
		}
		return false;
	}

}
