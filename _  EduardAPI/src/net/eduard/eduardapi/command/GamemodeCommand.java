package net.eduard.eduardapi.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.eduardapi.EduardAPI;

public class GamemodeCommand extends CMD {

	public GamemodeCommand() {
		getCommand().setPermission("eduardapi.commands.gamemode");
	}

	public String getGamemode(Player player) {
		return API.toTitle(player.getGameMode().name());
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.getGameMode() == GameMode.CREATIVE) {
					p.setGameMode(GameMode.SURVIVAL);
				} else {
					p.setGameMode(GameMode.CREATIVE);
				}
				sender.sendMessage(EduardAPI.message("gamemode").replace("$gamemode", getGamemode(p)));

			} else
				return false;

		} else {
			String arg = args[0];
			GameMode gm = null;
			try {
				gm = GameMode.getByValue(API.toInt(arg));
			} catch (Exception ex) {
				try {
					gm = GameMode.valueOf(arg.toUpperCase());
				} catch (Exception ex2) {
					return false;
				}
			}
			Player p = null;
			if (sender instanceof Player) {
				p = (Player) sender;
			}
			if (args.length >= 2) {
				if (API.existsPlayer(sender, args[1])) {
					p = API.getPlayer(args[1]);
					sender.sendMessage(EduardAPI.message("gamemodeTarget").replace("$gamemode", getGamemode(p))
							.replace("$player", p.getDisplayName()));
				}else return true;;

			}
			if (p == null) {
				return false;
			}
			p.setGameMode(gm);
			p.sendMessage(EduardAPI.message("gamemode").replace("$gamemode", getGamemode(p)));

		}
		return true;
	}
}
