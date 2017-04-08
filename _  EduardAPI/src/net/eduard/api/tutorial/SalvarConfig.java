package net.eduard.api.tutorial;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class SalvarConfig implements Listener {

	public SalvarConfig(FileConfiguration config) {
		config.set("ALGO_NA_CONFIG", 1);
		config.set("algo_na_config", 1);
		config.set("AlgoNaConfig", 1);
		config.set("algoNaConfig", 1);
		config.set("algo-na-config", 1);
		config.set("algo na config", 1);
		config.set("Algo na Config", 1);
		config.set("Algo-na-Config", 1);
		config.set("$player", 1);
		config.set("$player$", 1);
		config.set("<player>", 1);
		config.set("%player%", 1);
		config.set("%player", 1);
		
	}
}
