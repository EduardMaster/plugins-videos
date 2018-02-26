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

public class TelarComando implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§a/telar <jogador> ");
		}else {
			Player jogador = Bukkit.getPlayer(args[0]);
			if (jogador == null) {
				sender.sendMessage("§cJogador offline");
			}else {
				jogador.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*2000, 5));
				Main.jogadoresSendoTelados.add(jogador);
				jogador.sendMessage("§aVoce esta sendo telado se sair tomara ban aumaticamente");
				
			}
		}
		
		return false;
	}
	
	

}
