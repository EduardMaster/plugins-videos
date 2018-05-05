
package net.eduard.antibot;


import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.setup.Mine;
import net.eduard.api.setup.manager.TimeManager;

public class Main extends JavaPlugin {

	public static List<String> ips_proxy;
	public static Config config;
	public static TimeManager time;
	@Override
	public void onEnable() {
		config = new Config(this);
		time = new TimeManager(this);
		config.add("Bot",
			"&cSuspeita de Bot! $enter §aPor favor relogue em alguns segundos!");
		config.saveConfig();
		new BotDetector();
		try {
			File file = new File(getDataFolder(),"proxy.yml");
			Mine.console("§3[§bAntBot§3] §7Salvando a config §eproxy.yml");
			saveResource("proxy.yml", false);
			Mine.console("§3[§bAntBot§3] §7Pegando os §fIps-Proxys§7 da config §eproxy.yml");
			ips_proxy = Files.readAllLines(file.toPath());
			Mine.console("§3[§bAntBot§3] §aAntivando Bot Detector!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
