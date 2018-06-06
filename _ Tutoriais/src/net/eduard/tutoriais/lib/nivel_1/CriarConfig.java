package net.eduard.tutoriais.lib.nivel_1;

import org.bukkit.plugin.Plugin;

import net.eduard.api.lib.core.ConfigAPI;

public class CriarConfig {
	private static ConfigAPI config;
	public static void criarConfig(Plugin plugin) {
		config = new ConfigAPI("teste.yml",plugin);
	}
	
	public static ConfigAPI pegarConfig() {
		return config;
	}
	public static void salvarConfig() {
		config.saveConfig();
	}
	public static void recarregarConfig() {
		config.reloadConfig();
	}
}
