package net.eduard.login.command;

import java.sql.Timestamp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.login.LoginPlugin;
import net.eduard.login.manager.LoginAPI;
import net.eduard.login.manager.PlayerAccount;

public class RegisterCommand extends CommandManager {

	public RegisterCommand() {
		super("register", "registrar");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length < 3) {
				sendUsage(sender);
			} else {
				if (LoginAPI.isRegistered(p)) {
//					Mine.chat(p, messageError);
					Mine.OPT_SOUND_ERROR.create(p);
				} else {
					PlayerAccount account = LoginAPI.getAccount(p);
					String pass = args[0];
					if (LoginAPI.canRegister(p, pass)) {

						if (LoginPlugin.getInstance().getBoolean("need-confirm-password")) {

							if (!pass.equals(args[1])) {
								p.sendMessage(LoginPlugin.getInstance().message("confirm-error"));
								return true;
							}

						}

						p.sendMessage(LoginPlugin.getInstance().message("register-success"));
						account.setRegisterTime(new Timestamp(System.currentTimeMillis()));

						if (LoginPlugin.getInstance().getBoolean("when-register-auto-login")) {
							p.chat("/login " + pass);
						} else {
//							titleSuccess.create(p);
//							Mine.OPT_SOUND_SUCCESS.create(p);
						}
					}

				}
			}
		}

		return true;
	}

}
