package net.eduard.login.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.login.LoginPlugin;
import net.eduard.login.manager.LoginAPI;
import net.eduard.login.manager.PlayerAccount;

public class ChangePasswordCommand extends CommandManager {

	public ChangePasswordCommand() {
		super("changepassword");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;

			if (args.length <= 1) {
				sendUsage(sender);
			} else {
				if (LoginAPI.isLogged(p)) {
					PlayerAccount account = LoginAPI.getAccount(p);
					String pass = args[0];
					String newPass = args[1];
					if (account.getPassword().equals(pass)) {
						if (LoginAPI.canRegister(p, newPass)) {
							account.setPassword(pass);
							LoginPlugin.getInstance().message("password-change-success");
						}

					} else {
						LoginPlugin.getInstance().message("old-password-wrong");
					}
				} else {
					LoginPlugin.getInstance().message("not-logged");
				}
			}

		}
		return true;
	}

}
