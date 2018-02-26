package net.eduard.live.mobstuckers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DestelarComando implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§a/telar <jogador> ");
		}else {
			Player jogador = Bukkit.getPlayer(args[0]);
			if (jogador == null) {
				sender.sendMessage("§cJogador offline");
			}else {
				Main.jogadoresSendoTelados.remove(jogador);
				
				jogador.sendMessage("§aVoce removeu o jogador da lista de sendo telados "+jogador.getName());
				
			}
		}
		
		return false;
	}
	
	

}
