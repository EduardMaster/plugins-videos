package net.eduard.login;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.eduard.api.config.Config;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.lib.core.Mine;
import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;

public class RegisterCommand extends CommandManager {


	public boolean isRegistered(Player p) {
		return config.contains(p.getUniqueId().toString() + ".password");
	}
	public void register(Player p, String password) {
		ConfigSection sec = config.getSection(p.getUniqueId().toString());
		sec.set("password", password);
		sec.set("name", p.getName());
		sec.set("registred-since", Mine.getNow());
	}
	public boolean canRegister(Player p, String pass) {
		if (pass.length() < minPasswordSize) {
			Mine.chat(p, messageMinPasswordSize);
			Mine.OPT_SOUND_ERROR.create(p);
		} else if (pass.length() > maxPasswordSize) {
			Mine.chat(p, messageMaxPasswordSize);
			Mine.OPT_SOUND_ERROR.create(p);
		} else {
			if (canUseLettersOnPassword&!canUseNumbersOnPassword) {
				if (pass.matches("[a-zA-Z]+")) {
					Mine.chat(p, messageOnlyLetters);
					Mine.OPT_SOUND_ERROR.create(p);
					return true;
				}
			} else if (canUseNumbersOnPassword&!canUseLettersOnPassword) {
				if (pass.matches("[0-9]+")) {
					Mine.chat(p, messageOnlyLetters);
					Mine.OPT_SOUND_ERROR.create(p);
					return true;
				}
			}else {
				return true;
			}


		}

		return false;
	}

	public RegisterCommand() {
		super("register");
		registerCommand = this;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				sendUsage(sender);
			} else {
				if (isRegistered(p)) {
					Mine.chat(p, messageError);
					Mine.OPT_SOUND_ERROR.create(p);
				} else {
					String pass = args[0];
					if (canRegister(p, pass)) {

						if (needCofirmPassword) {
							if (args.length >= 2) {
								if (!pass.equals(args[1])) {
									Mine.chat(p, messageConfirmError);
									return true;
								}
							} else {
								Mine.chat(sender, messageConfirm);
								return true;
							}
						}
						Mine.chat(p, message);
						register(p, pass);

						if (onRegisterAutoLogin) {
							p.chat("/login " + pass);
						} else {
							titleSuccess.create(p);
							Mine.OPT_SOUND_SUCCESS.create(p);
						}
					}

				}
			}
		}

		return true;
	}
	
}
