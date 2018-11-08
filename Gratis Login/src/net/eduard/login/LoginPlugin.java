package net.eduard.login;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import net.eduard.api.server.EduardPlugin;

public class LoginPlugin extends EduardPlugin{
	public static final Map<Player, String> PLAYERS_LOGGED = new HashMap<>();
	public static final Map<Player, Integer> FAILS_LOGINS = new HashMap<>();
	@Override
	public void onEnable() {
		
		
//		public Title title = new Title(20, 20 * 60, 20, "§cAutenticação",
//				"§c/register <senha>");
//		public Title titleSuccess = new Title(20, 20 * 3, 20, "§a§lRegistrado!",
//				"§cNao use senhas faceis!");
		
//		public Title title = new Title(20, 20 * 60, 20, "§cAutenticação", "§c/login <senha>");
//		public Title titleSuccess = new Title(20, 20 * 3, 20, "§a§lAutenticado!", "§6§lSeja bem vindo novamente!");

		
	}
}
