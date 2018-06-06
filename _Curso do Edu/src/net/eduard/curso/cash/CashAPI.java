package net.eduard.curso.cash;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import net.eduard.api.lib.core.ConfigAPI;

public class CashAPI {

	public static HashMap<String, Double> contas = new HashMap<>();

	public static ConfigAPI config = new ConfigAPI("contas.yml");

	public static void addCash(Player player, double quantidade) {
		setCash(player, getCash(player) + quantidade);

	}

	public static void removeCash(Player player, double quantidade) {
		setCash(player, getCash(player) - quantidade);

	}

	public static void setCash(Player player, double quantidade) {
		contas.put(player.getName().toLowerCase(), quantidade);
		config.set(player.getName().toLowerCase(), quantidade);

	}

	public static List<Entry<String, Double>> gerarMoneyTop() {
		Stream<Entry<String, Double>> streamOrdenada = contas.entrySet().stream()
				.sorted(Comparator.comparing((Entry::getValue)));
		Stream<Entry<String, Double>> streamLimitada = streamOrdenada.limit(10);
//		streamLimitada.forEach(entrada -> {
//			String chave = entrada.getKey();
//			Double valor = entrada.getValue();
//			
//			
//			
//		});
//		streamLimitada.sorted(Collections.reverseOrder());
		return streamLimitada.collect(Collectors.toList());
	}
	public static void mostrarTopSQL(Player player) {
		String query = "SELECT * FROM banco odery by quantia desc;";
		
	}

	public static void mostrarTop(Player player) {
		DecimalFormat formatador = new DecimalFormat("#.###,");
		List<Entry<String, Double>> top = gerarMoneyTop();
		int posicao = 1;
		for (Entry<String, Double> entrada : top) {
			String chave = entrada.getKey();
			Double valor = entrada.getValue();
			player.sendMessage("§a" + posicao + "º " + chave + " R$ §2" + formatador.format(valor));
			posicao++;
			if (posicao>=10) {
				break;
			}
		}
	}

	public static double getCash(Player player) {
		return contas.getOrDefault(player.getName().toLowerCase(), 0D);
	}

	public static double getCashConfig(Player player) {
		return config.getConfig().getDouble(player.getName().toLowerCase(), 0D);
	}

}
