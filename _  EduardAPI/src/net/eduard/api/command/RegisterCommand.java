package net.eduard.api.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.game.Sounds;
import net.eduard.api.manager.CMD;

public class RegisterCommand extends CMD {
	public String messageError = "§cVoce ja esta registrado!";
	public String messageConfirm = "§cConfirme a senha!";
	public String messageRegister = "§aPor favor se registre no servidor /register";
	public Sounds soundSucess = Sounds.create(Sound.SUCCESSFUL_HIT);
	public Sounds soundFail = Sounds.create(Sound.NOTE_BASS_DRUM);
	public String message = "§aVoce foi registrado com sucesso!";
	public Config config = new Config("auth.yml");
	
	
	public boolean onRegisterAutoLogin = true;

	public boolean needCofirmPassword = true;

	public boolean isRegistered(Player p) {
		return config.contains(p.getUniqueId().toString());
	}
	public void register(Player p, String password) {
		config.set(p.getUniqueId().toString(), password);

	}

	public RegisterCommand() {
		super("register");
		setUsage("§c/register <password> <confirm>");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				sendUsage(sender);
			} else {
				if (isRegistered(p)) {
					ConfigSection.chat(p, messageError);
				} else {
					String pass = args[0];
					if (needCofirmPassword) {
						if (args.length >= 2) {
							if (!pass.equals(args[1])) {
								ConfigSection.chat(p, messageConfirm);
								return true;
							}
						} else {
							ConfigSection.chat(sender,messageConfirm);
							return true;
						}
					}
					ConfigSection.chat(p, message);
					register(p, pass);
					if (onRegisterAutoLogin) {
						p.chat("/login " + pass);
					}
				}
			}
		}

		return true;
	}
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!isRegistered()) {
			ConfigSection.chat(p, messageRegister);
		}
		
	}

}
