
package net.eduard.curso;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.BukkitConfig;
import net.eduard.curso.aula_caixas.CaixaAPI;
import net.eduard.curso.aula_eventos.Eventos;
import net.eduard.curso.aula_login.ComandoLogin;
import net.eduard.curso.aula_login.ComandoRegister;
import net.eduard.curso.aula_rankup.ComandoRankup;
import net.eduard.curso.aula_rankup.RankAPI;
import net.eduard.curso.aula_scoreboard.SimplesScore;
import net.eduard.curso.aula_spawn.ComandoSetSpawn;
import net.eduard.curso.aula_spawn.ComandoSpawn;
import net.eduard.curso.aula_tempo.ComandoCooldown;
import net.eduard.curso.aula_tempo.ComandoDelay;
import net.eduard.curso.aula_tempo.ComandoTimer;
import net.eduard.curso.aula_warps.ComandoDeleteWarp;
import net.eduard.curso.aula_warps.ComandoSetWarp;
import net.eduard.curso.aula_warps.ComandoWarps;

public class CursoMain extends JavaPlugin {
	private static CursoMain instance;
	private static BukkitConfig config;

	public static BukkitConfig getConfigs() {
		return config;
	}

	public void onEnable() {
		instance = this;
		config = new BukkitConfig("config.yml", this);
		SimplesScore.ligar(this);
		CaixaAPI.reload();
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		getCommand("setspawn").setExecutor(new ComandoSetSpawn());
		getCommand("spawn").setExecutor(new ComandoSpawn());

		getCommand("delwarp").setExecutor(new ComandoDeleteWarp());
		getCommand("setwarp").setExecutor(new ComandoSetWarp());
		getCommand("warp").setExecutor(new ComandoDeleteWarp());
		getCommand("warps").setExecutor(new ComandoWarps());

		getCommand("login").setExecutor(new ComandoLogin());
		getCommand("register").setExecutor(new ComandoRegister());

		getCommand("rank").setExecutor(new ComandoRankup());
		
		getCommand("delay").setExecutor(new ComandoDelay());
		getCommand("cooldown").setExecutor(new ComandoCooldown());
		getCommand("timer").setExecutor(new ComandoTimer());
		
		RankAPI.reload();
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin ativado do curso");
	}

	public void onDisable() {
		CaixaAPI.save();
		RankAPI.save();
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Plugin desativado do curso");
	}

	public static CursoMain getInstance() {
		return instance;
	}

}
