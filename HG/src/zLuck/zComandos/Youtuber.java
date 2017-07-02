package zLuck.zComandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import zLuck.zUteis.Centro;

public class Youtuber implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("youtuber") || label.equalsIgnoreCase("yt")) {	
			for (int i = 0; i < 30; i++) {		
				p.sendMessage(" ");
			}
			Centro.sendCenteredMessage(p, "§b§lTAG YOUTUBER");
			Centro.sendCenteredMessage(p, "§7É necessario §c§l2 §7MIL inscritos + §b§l100 §7Likes por vídeo.");
			Centro.sendCenteredMessage(p, "§7Também é necessario §b§lMIL §7visualização no vídeo gravado.");
	        p.sendMessage("  ");
	        Centro.sendCenteredMessage(p, "§6§lTAG PRO");
	        Centro.sendCenteredMessage(p, "§7É necessario §6§lMIL §7inscritos + §6§l50 §7Likes por vídeo.");
	        Centro.sendCenteredMessage(p, "§7Também é necessario §6§l500 §7visualização no vídeo gravado.");
	        p.sendMessage("  ");
	        Centro.sendCenteredMessage(p, "§e§lTAG S-PRO");
	        Centro.sendCenteredMessage(p, "§7É necessario §e§l500 §7inscritos + §6§l25 §7Likes por vídeo.");
	        Centro.sendCenteredMessage(p, "§7Também é necessario §6§l250 §7visualização no vídeo gravado.");
	        p.sendMessage("  ");
	        Centro.sendCenteredMessage(p, "§7Após ter gravado no servidor, envie-nos em nosso §btwitter");
	        p.sendMessage("  ");
	        Centro.sendCenteredMessage(p, "§c§lTwitter: @FuckyKits");
	        Centro.sendCenteredMessage(p, "§c§lFacebook: https://www.facebook.com/FuckyKits/");
		}
		return false;
	}

}
