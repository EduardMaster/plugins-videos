package net.eduard.api.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class GamemodeCommand extends Commands {

	public GamemodeCommand() {
		super("gamemode");
	}
	public String message = "§6Seu gamemode agora é: $gamemode";
	public String messageTarget = "§6O gamemode do $player agora é: $gamemode";

	public String getGamemode(Player player) {
		return API.toTitle(player.getGameMode().name());
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.getGameMode() == GameMode.CREATIVE) {
					p.setGameMode(GameMode.SURVIVAL);
				} else {
					p.setGameMode(GameMode.CREATIVE);
				}
				API.chat(sender,
						message.replace("$gamemode", getGamemode(p)));

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
					API.chat(sender,
							messageTarget.replace("$gamemode", getGamemode(p))
									.replace("$player", p.getDisplayName()));
				} else
					return true;;

			}
			if (p == null) {
				return false;
			}
			p.setGameMode(gm);
			API.chat(p,message.replace("$gamemode", getGamemode(p)));

		}
		return true;
	}
}
