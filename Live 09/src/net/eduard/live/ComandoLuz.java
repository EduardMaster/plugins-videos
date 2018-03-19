package net.eduard.live;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ComandoLuz implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
				p.sendMessage("§cLuz desativada!");
			} else {
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000000, 1));
				p.sendMessage("§aLuz ativada!");
			}
		}

		return false;
	}

}
