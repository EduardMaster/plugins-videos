
package net.eduard.api.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.game.Sounds;
import net.eduard.api.manager.CMD;
import net.eduard.api.time.Delay;
import net.eduard.api.util.PlayerEffect;

public class SpawnCommand extends CMD {

	public SpawnCommand() {
		super("spawn");

	}
	public Delay delay = new Delay();
	public String message = "§cVoce foi teleportado para o Spawn!";
	public String messageNot = "§cO Spawn não foi setado!";
	public Sounds sound = Sounds.create(Sound.SUCCESSFUL_HIT);
	public Config config = new Config("spawn.yml");

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (!config.contains("spawn")) {
				ConfigSection.chat(p, messageNot);
				p.sendMessage(messageNot);
			} else {
				delay.effect(p, new PlayerEffect() {
					
					@Override
					public void effect(Player p) {
						ConfigSection.chat(p, message);
						p.teleport(config.getLocation("spawn"));
						sound.create(p);
					}
				});
				
			}

		}

		return true;
	}

}
