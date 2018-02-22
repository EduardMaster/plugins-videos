package net.eduard.live.almas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	public static List<String> mensagens = new ArrayList<>();
	public static Main instance;

	public void onEnable() {
		instance = this;
		configExtra = YamlConfiguration
				.loadConfiguration(new File(Main.instance.getDataFolder(), "configextra.yml"));;
		mensagens.add("primeira mensagem");
		mensagens.add("segunda mensagem");
		mensagens.add("terceira mensagem");
		mensagens.add("quarta mensagem");
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		new BukkitRunnable() {

			@Override
			public void run() {
				int id = 1 + new Random().nextInt(mensagens.size());
				String mensagem = mensagens.get(id - 1);
				Bukkit.broadcastMessage(mensagem);
			}
		}.runTaskTimerAsynchronously(this, 20 * 60 * 2, 20 * 60 * 2);

		new BukkitRunnable() {
			int id = 0;

			@Override
			public void run() {
				String mensagem = mensagens.get(id);
				Bukkit.broadcastMessage(mensagem);
				id--;
				if (id == mensagens.size()) {
					id = 0;
				}
			}
		}.runTaskTimerAsynchronously(this, 20 * 60 * 2, 20 * 60 * 2);
//		recarregarAlmas();
		new BukkitRunnable() {

			@Override
			public void run() {
				salvarAlmas();
			}
		}.runTaskTimerAsynchronously(this, 20 * 60 * 4, 20 * 60 * 4);
	}

	public void onDisable() {
		salvarAlmas();
	}

//	public static void recarregarAlmas() {
//		configExtra = YamlConfiguration
//				.loadConfiguration(new File(Main.instance.getDataFolder(), "configextra.yml"));;
//		Set<String> secao = configExtra.getKeys(false);
//		for (String key : secao) {
//			
//		}
//				
//	}

	public static YamlConfiguration configExtra;

	public static void salvarAlmas() {
		for (Entry<Player, Double> entry : almas.entrySet()) {
			Player jogador = entry.getKey();
			Double pontos = entry.getValue();
			configExtra.set(jogador.getName().toLowerCase(), pontos);
		}
		try {
			configExtra.save(new File(Main.instance.getDataFolder(),"configextra.yml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap<Player, Double> almas = new HashMap<>();

	public static void adiconarAlmas(Player jogador, double almas) {
		double almasAtuais = getAlmas(jogador);
		Main.almas.put(jogador, almasAtuais + almas);
	}

	public static double getAlmas(Player jogador) {
		return Main.almas.getOrDefault(jogador, 0D);
	}

	public static void setarAlmas(Player jogador, double quantidade) {
		Main.almas.put(jogador, quantidade);
	}

	public static void removerAlmas(Player jogador, double almas) {
		double almasAtuais = getAlmas(jogador);
		setarAlmas(jogador, almasAtuais - almas);
	}

}
