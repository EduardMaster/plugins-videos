package net.eduard.live.almas;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoAlmas implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage("§aSuas almas: " + AlmasAPI.getAlmas(p));

			} else {
				sender.sendMessage("§cApenas jogadores podem ver seu cash.");
			}
		} else {
			if (!sender.hasPermission("almas.editar")) {
				sender.sendMessage("§cVoce não possui permissão para editar almas dos jogadores.");
				return true;
			}
			String sub = args[0];
			// /almas set|remove|add [jogador] [quantidade]
			if (sub.equalsIgnoreCase("set")) {
				if (args.length <= 2) {
					sender.sendMessage("§c/almas set [jogador] [quantidade]");
				} else {
					try {
						Integer quatidade = Integer.valueOf(args[2]);
						Player alvo = Bukkit.getPlayer(args[1]);
						if (alvo == null) {

							sender.sendMessage("§cEste jogador não esta online.");
						} else {

							AlmasAPI.setAlmas(alvo, quatidade);
							alvo.sendMessage("§aSuas almas foram alteradas para " + AlmasAPI.getAlmas(alvo));
							sender.sendMessage("§aVoce alterou a almas do jogador " + alvo.getName() + " para "
									+ AlmasAPI.getAlmas(alvo));
						}

					} catch (Exception e) {
						sender.sendMessage("§cVoce nao digitou um numero!");
					}

				}
			}else if (sub.equalsIgnoreCase("add")) {
				if (args.length <= 2) {
					sender.sendMessage("§c/almas add [jogador] [quantidade]");
				} else {
					try {
						Integer quatidade = Integer.valueOf(args[2]);
						Player alvo = Bukkit.getPlayer(args[1]);
						if (alvo == null) {

							sender.sendMessage("§cEste jogador não esta online.");
						} else {

							AlmasAPI.addAlmas(alvo, quatidade);
							alvo.sendMessage("§aSuas almas foram alteradas para " + AlmasAPI.getAlmas(alvo));
							sender.sendMessage("§aVoce alterou a almas do jogador " + alvo.getName() + " para "
									+ AlmasAPI.getAlmas(alvo));
						}

					} catch (Exception e) {
						sender.sendMessage("§cVoce nao digitou um numero!");
					}

				}
			}if (sub.equalsIgnoreCase("remove")) {
				if (args.length <= 2) {
					sender.sendMessage("§c/almas remove [jogador] [quantidade]");
				} else {
					try {
						Integer quatidade = Integer.valueOf(args[2]);
						Player alvo = Bukkit.getPlayer(args[1]);
						if (alvo == null) {

							sender.sendMessage("§cEste jogador não esta online.");
						} else {

							AlmasAPI.removeAlmas(alvo, quatidade);
							alvo.sendMessage("§aSuas almas foram alteradas para " + AlmasAPI.getAlmas(alvo));
							sender.sendMessage("§aVoce alterou a almas do jogador " + alvo.getName() + " para "
									+ AlmasAPI.getAlmas(alvo));
						}

					} catch (Exception e) {
						sender.sendMessage("§cVoce nao digitou um numero!");
					}

				}
			}

		}

		return false;
	}

}
