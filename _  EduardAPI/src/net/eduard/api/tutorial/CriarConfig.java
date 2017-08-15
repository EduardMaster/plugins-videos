package net.eduard.api.tutorial;

import org.bukkit.plugin.Plugin;

import net.eduard.api.config.Configs;

public class CriarConfig {
	private static Configs config;
	public static void criarConfig(Plugin plugin) {
		config = new Configs("teste.yml",plugin);
	}
	
	public static Configs pegarConfig() {
		return config;
	}
	public static void salvarConfig() {
		config.saveConfig();
	}
	public static void recarregarConfig() {
		config.reloadConfig();
	}
}
