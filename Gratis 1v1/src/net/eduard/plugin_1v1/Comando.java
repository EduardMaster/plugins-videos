package net.eduard.plugin_1v1;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comando implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player	) {
			Player jogador = (Player) sender;
			
			if (args.length == 0) {
				jogador.sendMessage("§cErro: /"+label +" [jogador]");
			}else {
				@SuppressWarnings("deprecation")
				Player convidado = Bukkit.getPlayerExact(args[0]);
				if (convidado == null) {
					jogador.sendMessage("§cJogador offline!");
				}else {
					jogador.sendMessage("§aConvidando o jogador: §2"+convidado.getName());
					Main.getPlugin().convidarJogador(jogador, convidado);
					Main.getPlugin().aceitarPvP(convidado, jogador);
				}
			}
		}else
		{
			sender.sendMessage("§cComando para jogadores apenas!");
		}
		return true;
	}
}
