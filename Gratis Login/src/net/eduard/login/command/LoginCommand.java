package net.eduard.login.command;

import java.sql.Timestamp;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.login.LoginPlugin;
import net.eduard.login.manager.LoginAPI;
import net.eduard.login.manager.PlayerAccount;

public class LoginCommand extends CommandManager {

	public LoginCommand() {
		super("login");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				sendUsage(sender);
			} else {
				String pass = args[0];
				if (!LoginAPI.isRegistered(p)) {
					sender.sendMessage(LoginPlugin.getInstance().message("not-registred"));

					Mine.OPT_SOUND_ERROR.create(p);
				} else {
					if (LoginAPI.isLogged(p)) {
						sender.sendMessage(LoginPlugin.getInstance().message("already-logged"));
						Mine.OPT_SOUND_ERROR.create(p);
					} else {
						PlayerAccount account = LoginAPI.getAccount(p);
						String password = account.getPassword();
						if (pass.equals(password)) {
							sender.sendMessage(LoginPlugin.getInstance().message("login-success"));
							if (p.getGameMode() != GameMode.CREATIVE) {

								p.setFlying(false);
								p.setAllowFlight(false);
							}
							LoginAPI.login(p);
//							titleSuccess.create(p);
							Mine.OPT_SOUND_SUCCESS.create(p);
							for (String message : LoginPlugin.getInstance().getMessages("messages-when-login")) {
								p.sendMessage(message);
							}
							account.setLastLogin(new Timestamp(System.currentTimeMillis()));
//							config.set(p.getUniqueId().toString() + ".last-ip", Mine.getIp(p));
						} else {
							int max = LoginPlugin.getInstance().getConfigs().getInt("max-login-attemps");
							LoginAPI.addLoginAttempt(p);
							int fails = LoginAPI.getLoginAttempts(p);

							if (fails == max) {
								if (LoginPlugin.getInstance().getBoolean("kick-on-max-fails")) {
									p.kickPlayer(LoginPlugin.getInstance().message("kick-for-many-fails"));
								}
//								if (banOnMaxFails) {
//									p.setBanned(true);
//								}
								LoginAPI.getLogginAttempts().remove(p);
							}
							Mine.chat(p, LoginPlugin.getInstance().message("wrong-password"));
							Mine.OPT_SOUND_ERROR.create(p);
						}
					}
				}

			}
		}
		return true;
	}

}
