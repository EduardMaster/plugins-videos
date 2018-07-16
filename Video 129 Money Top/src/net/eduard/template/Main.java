
package net.eduard.template;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.server.EduardPlugin;
import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.TemplateEvents;

public class Main extends EduardPlugin {
	private static Main plugin;

	public static Main getInstance() {
		return plugin;
	}

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}

	public static HashMap<String, Double> contas = new HashMap<>();

	public static List<Entry<String, Double>> valores;

	@Override
	public void onEnable() {
		plugin = this;
		new TemplateEvents().register(this);
		new TemplateCommand().register();
		new BukkitRunnable() {

			@Override
			public void run() {
				atualizador();
			}
		}.runTaskTimer(this, 20, 60*2);
		
		//errado 
		//atualizar a cada segundo é locura
		
		// mudei pra 2 min

	}

	@SuppressWarnings("deprecation")
	public static void atualizador() {
		// REQUISITO USAR VAULTAPI
		for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
			double dinheiro = VaultAPI.getEconomy().getBalance(offline);
			contas.put(offline.getName(), dinheiro);
		}

		// AGORA VEM A PARTE LEGAL
		// JAVA 8

		Stream<Entry<String, Double>> streamOrdenada = contas.entrySet().stream()
				.sorted((x, y) -> y.getValue().compareTo(x.getValue()));

		valores = streamOrdenada.collect(Collectors.toList());
		
		// TUDO PRONTO
		
		// CODIGO PRA ORDENAR DO MAIOR PRO MENOR

	}
	
	public static void mostrarTop(Player p) {
		DecimalFormat formatador = new DecimalFormat("#,##0.00");
		int id = 1;
		for (Entry<String, Double> entrada : valores) {
			String jogador = entrada.getKey();
			Double valor = entrada.getValue();
			
			p.sendMessage("§a  "+id+"º §2"+jogador +" §f: §7"+formatador.format(valor));
			id++;
			if (id >10) {
				break;
			}
			
			
		}
		// agora falta o comando fake
		
		
	}
	
	

	@Override
	public void onDisable() {
	}

}
