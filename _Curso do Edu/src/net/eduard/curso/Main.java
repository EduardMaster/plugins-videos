
package net.eduard.curso;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.core.ConfigAPI;
import net.eduard.curso.caixa.CaixaSistema;
import net.eduard.curso.cash.CashSQLite;
import net.eduard.curso.eventos.Eventos;
import net.eduard.curso.eventos.SimplesScore;
import net.eduard.curso.gladiador.Gladiador;
import net.eduard.curso.login.ComandoLogin;
import net.eduard.curso.login.ComandoRegister;
import net.eduard.curso.warp.ComandoDeleteWarp;
import net.eduard.curso.warp.ComandoSetWarp;
import net.eduard.curso.warp.ComandoWarps;

public class Main extends JavaPlugin {
	public static Main instance;
	public static ConfigAPI config;
	public static Gladiador glad;
	public static HashMap<UUID, String> registrados = new HashMap<>();
	public static HashMap<Player, Long> logados = new HashMap<>();

	public void onEnable() {
		instance = this;
		CaixaSistema.ligar(this);
		CashSQLite.abrir();
		getCommand("test").setExecutor(new CashSQLite());
		glad = new Gladiador();
		config = new ConfigAPI("config.yml", this);
		config.saveDefaultConfig();

		config.add("morte mesagem", "mensagem");
		
		
		config.saveDefault();

		SimplesScore scoreboards = new SimplesScore();
		Bukkit.getPluginManager().registerEvents(scoreboards, this);
		scoreboards.runTaskTimer(this, 20, 20);

		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		recarregasRegistros();
		getCommand("login").setExecutor(new ComandoLogin());
		getCommand("register").setExecutor(new ComandoRegister());
		getCommand("delwarp").setExecutor(new ComandoDeleteWarp());
		getCommand("setwarp").setExecutor(new ComandoSetWarp());
		getCommand("warp").setExecutor(new ComandoDeleteWarp());
		getCommand("warps").setExecutor(new ComandoWarps());

	}

	public void recarregasRegistros() {
		registrados.clear();
		if (config.contains("Contas")) {
			for (Entry<String, Object> entry : config.getSection("Contas").getValues(false).entrySet()) {
				registrados.put(UUID.fromString(entry.getKey()), (String) entry.getValue());
			}
		}

	}

	public void salvarRegistros() {
		config.remove("Contas");
		for (Entry<UUID, String> entry : registrados.entrySet()) {
			config.set("Contas." + entry.getKey(), entry.getValue());
		}
		config.saveConfig();

		// for (Entry<UUID, String> map : registrados.entrySet()) {
		// config.set("Contas." + map.getKey(), map.getValue());
		// }
		// config.saveConfig();
	}

	public void onDisable() {
		CashSQLite.fechar();
		salvarRegistros();
	}

}
