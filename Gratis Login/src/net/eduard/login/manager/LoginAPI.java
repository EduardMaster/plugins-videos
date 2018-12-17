package net.eduard.login.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.modules.FakePlayer;
import net.eduard.login.LoginPlugin;

public final class LoginAPI {

	public static PlayerAccountManager getManager() {
		return LoginPlugin.getInstance().getManager();
	}

	public static boolean canRegister(Player p, String pass) {
		boolean ok = false;
		if (pass.length() < LoginPlugin.getInstance().getConfigs().getInt("min-password-size")) {
			p.sendMessage(LoginPlugin.getInstance().message("password-short"));
		} else if (pass.length() > LoginPlugin.getInstance().getConfigs().getInt("max-password-size")) {
			p.sendMessage(LoginPlugin.getInstance().message("password-long"));

		} else {
//		if (LoginPlugin.getInstance().getBoolean("use-letters-on-password")&!LoginPlugin.getInstance().getBoolean("use-numbers-on-password")) {
//			if (pass.matches("[a-zA-Z]+")) {
//				p.sendMessage(LoginPlugin.getInstance().message("only-letters"));
//			}
//		} else if (canUseNumbersOnPassword&!canUseLettersOnPassword) {
//			if (pass.matches("[0-9]+")) {
//				Mine.chat(p, messageOnlyLetters);
//				Mine.OPT_SOUND_ERROR.create(p);
//				return true;
//			}
//		}else {
//			ok = true;
//		}
		}
		if (!ok) {
			Mine.OPT_SOUND_ERROR.create(p);
		}
		return ok;
	}

//	return false;
//}

	private static ArrayList<Player> playersLogged = new ArrayList<>();
	private static Map<Player, Integer> logginAttempts = new HashMap<>();

	public static void addLoginAttempt(Player player) {
		logginAttempts.put(player, getLoginAttempts(player) + 1);
	}

	public static boolean isLogged(Player player) {
		return playersLogged.contains(player);
	}

	public static int getLoginAttempts(Player player) {
		return logginAttempts.getOrDefault(player, 0);
	}

	public static void login(Player player) {
		playersLogged.add(player);
	}

	public static void logout(Player player) {
		playersLogged.remove(player);
	}

	public static void save() {
		LoginPlugin.getInstance().save();
	}

	public static void reload() {
		LoginPlugin.getInstance().reload();
	}

	public static ArrayList<Player> getPlayersLogged() {
		return playersLogged;
	}

	public static void setPlayersLogged(ArrayList<Player> playersLogged) {
		LoginAPI.playersLogged = playersLogged;
	}

	public static Map<Player, Integer> getLogginAttempts() {
		return logginAttempts;
	}

	public static void setLogginAttempts(Map<Player, Integer> logginAttempts) {
		LoginAPI.logginAttempts = logginAttempts;
	}

	public static boolean isRegistered(Player player) {
		return getManager().getAccount(new FakePlayer(player)).isRegistred();
	}

	public static PlayerAccount getAccount(Player player) {
		return getManager().getAccount(new FakePlayer(player));
	}

}
