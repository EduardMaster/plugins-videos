
package net.eduard.curso;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.ConfigAPI;
import net.eduard.curso.caixa.CaixaAPI;
import net.eduard.curso.eventos.Eventos;
import net.eduard.curso.exemplos.SimplesScore;
import net.eduard.curso.login.ComandoLogin;
import net.eduard.curso.login.ComandoRegister;
import net.eduard.curso.rankup.ComandoRankup;
import net.eduard.curso.rankup.RankAPI;
import net.eduard.curso.spawn.ComandoSetSpawn;
import net.eduard.curso.spawn.ComandoSpawn;
import net.eduard.curso.warp.ComandoDeleteWarp;
import net.eduard.curso.warp.ComandoSetWarp;
import net.eduard.curso.warp.ComandoWarps;

public class CursoMain extends JavaPlugin {
	private static CursoMain instance;
	private static ConfigAPI configs;

	public void onEnable() {
		setInstance(this);
		setConfigs(new ConfigAPI("config.yml", this));
		SimplesScore.ligar(this);
		CaixaAPI.ligar(this);
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		getCommand("setspawn").setExecutor(new ComandoSetSpawn());
		getCommand("spawn").setExecutor(new ComandoSpawn());
		getCommand("login").setExecutor(new ComandoLogin());
		getCommand("register").setExecutor(new ComandoRegister());
		getCommand("delwarp").setExecutor(new ComandoDeleteWarp());
		getCommand("setwarp").setExecutor(new ComandoSetWarp());
		getCommand("warp").setExecutor(new ComandoDeleteWarp());
		getCommand("warps").setExecutor(new ComandoWarps());
		getCommand("rank").setExecutor(new ComandoRankup());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin ativado do curso");
		RankAPI.reload();
	}

	public void onDisable() {
		RankAPI.save();
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Plugin desativado do curso");
	}

	public static CursoMain getInstance() {
		return instance;
	}

	public static void setInstance(CursoMain instance) {
		CursoMain.instance = instance;
	}

	public static ConfigAPI getConfigs() {
		return configs;
	}

	public static void setConfigs(ConfigAPI configs) {
		CursoMain.configs = configs;
	}

}
