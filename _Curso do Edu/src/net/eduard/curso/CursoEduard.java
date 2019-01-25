
package net.eduard.curso;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.BukkitConfig;
import net.eduard.curso.basico.ComandoDeleteWarp;
import net.eduard.curso.basico.ComandoSetSpawn;
import net.eduard.curso.basico.ComandoSetWarp;
import net.eduard.curso.basico.ComandoSpawn;
import net.eduard.curso.basico.ComandoWarps;
import net.eduard.curso.basico.SimplesScore;
import net.eduard.curso.comandos.ComandoCooldown;
import net.eduard.curso.comandos.ComandoDelay;
import net.eduard.curso.comandos.ComandoTimer;
import net.eduard.curso.eventos.Eventos;
import net.eduard.curso.sistemas.caixas.CaixaAPI;
import net.eduard.curso.sistemas.login.ComandoLogin;
import net.eduard.curso.sistemas.login.ComandoRegister;
import net.eduard.curso.sistemas.rankup.ComandoRankup;
import net.eduard.curso.sistemas.rankup.RankAPI;

public class CursoEduard extends JavaPlugin {
	private static CursoEduard instance;
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

	public static CursoEduard getInstance() {
		return instance;
	}

}
