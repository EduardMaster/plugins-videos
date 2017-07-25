package net.eduard.api.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CMD;

public class LoginCommand extends CMD {
	public String message = "§aVoce foi logado com sucesso!";
	public String messageError = "§cVoce ja esta logado!";
	public String messageRegister = "§cVoce não esta registrado!";
	public String messagePassword = "§cSenha incorreta";

	public Config config = new Config("auth.yml");
	public static Map<Player, String> logged = new HashMap<>();

	public LoginCommand() {
		super("login");

	}
	public boolean isRegistered(Player p) {
		return config.contains(p.getUniqueId().toString());
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;

			if (args.length == 0) {
				sendUsage(sender);
			} else {
				String pass = args[0];
				if (!isRegistered()) {
					ConfigSection.chat(sender, messageRegister);
				} else {
					if (logged.containsKey(p)) {
						ConfigSection.chat(sender, messageError);
					} else {
						String password = config
								.getString(p.getUniqueId().toString());
						if (pass.equals(password)) {
							ConfigSection.chat(p, message);
							logged.put(p, ""+System.currentTimeMillis());
						} else {
							ConfigSection.chat(p, messagePassword);
						}
					}
				}

			}
		}
		return true;
	}

}
