
package net.eduard.antibot;


import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.antibot.setup.BotDetector;
import net.eduard.api.config.CS;
import net.eduard.api.config.Config;
import net.eduard.api.manager.Manager;

public class Main extends JavaPlugin {

	public static List<String> ips_proxy;
	public static Config config;
	public static Manager time;
	public void onEnable() {
		config = new Config(this);
		time = new Manager(this);
		config.add("Bot",
			"&cSuspeita de Bot! $enter §aPor favor relogue em alguns segundos!");
		config.saveConfig();
		new BotDetector();
		try {
			File file = new File(getDataFolder(),"proxy.yml");
			CS.console("§3[§bAntBot§3] §7Salvando a config §eproxy.yml");
			saveResource("proxy.yml", false);
			CS.console("§3[§bAntBot§3] §7Pegando os §fIps-Proxys§7 da config §eproxy.yml");
			ips_proxy = (List<String>) Files.readAllLines(file.toPath());
			CS.console("§3[§bAntBot§3] §aAntivando Bot Detector!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
