
package net.eduard.curso;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.BukkitConfig;
import net.eduard.curso.apis.CaixaAPI;
import net.eduard.curso.apis.RankAPI;
import net.eduard.curso.comandos.ComandoDeleteWarp;
import net.eduard.curso.comandos.ComandoLogin;
import net.eduard.curso.comandos.ComandoRankup;
import net.eduard.curso.comandos.ComandoRegister;
import net.eduard.curso.comandos.ComandoSetSpawn;
import net.eduard.curso.comandos.ComandoSetWarp;
import net.eduard.curso.comandos.ComandoSpawn;
import net.eduard.curso.comandos.ComandoWarps;
import net.eduard.curso.eventos.Eventos;
import net.eduard.curso.manager.SimplesScore;

public class CursoMain extends JavaPlugin {
	private static CursoMain instance;
	private static BukkitConfig configs;

	public void onEnable() {
		setInstance(this);
		setConfigs(new BukkitConfig("config.yml", this));
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

	public static BukkitConfig getConfigs() {
		return configs;
	}

	public static void setConfigs(BukkitConfig configs) {
		CursoMain.configs = configs;
	}

}
