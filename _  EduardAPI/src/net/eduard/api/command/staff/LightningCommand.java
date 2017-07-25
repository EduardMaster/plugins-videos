
package net.eduard.api.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class LightningCommand extends CMD {
	public String messageOn = "§6Luz ativada!";
	public String messageOff = "§6Luz desativada!";
	
	public LightningCommand() {
		super("lightning");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
					p.removePotionEffect(PotionEffectType.NIGHT_VISION);
					ConfigSection.chat(p, messageOff);
				}else{
					ConfigSection.chat(p, messageOn);
				}
				
			} else
				return false;

		} else {
		
		}
		return true;
	}

}
