package net.eduard.api.util.vendas;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.setup.ConfigAPI;
import net.eduard.api.setup.Extra;
import net.eduard.api.setup.Extra.KeyType;
import net.eduard.api.setup.Mine;

public class SistemaDeKey {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Compra venda = new Compra();
		Scanner scan = new Scanner(System.in);

		System.out.println("Digite Nome do Dono do Plugin");
		venda.setNomeComprador(scan.nextLine());

		System.out.println("Digite Nome do Plugin");
		venda.setNomePlugin(scan.nextLine());

		System.out.println("Digite Preço do Plugin");
		venda.setPreco(Double.parseDouble(scan.nextLine()));

		System.out.println("Digite Ip do Servidor");
		venda.setIpHost(scan.nextLine());

		venda.setChave(Extra.newKey(KeyType.ALPHANUMERIC, 10));

		venda.setDataCompra(new Date(System.currentTimeMillis()));
		System.out.println(venda.gerarKey());

	}

	private static String site = "https://eduarddev.000webhostapp.com/plugins/protecao.txt";
	private static String extra = "";
	private static boolean verificarIp = true;
	private static boolean verificarPlugin = true;
	private static boolean verificarComprador = true;
	private static String vendedor = "Eduard";

	public static Compra getCompra(String texto) {
		Compra compra = new Compra();
		String[] split = texto.split(";");
		compra.setNomePlugin(split[0]);
		compra.setNomeComprador(split[1]);
		compra.setDataCompra(new Date(Mine.toLong(split[2])));
		compra.setPreco(Mine.toDouble(split[3]));
		compra.setChave(split[4]);
		compra.setIpHost(split[5]);
		return compra;
	}

	public static void verificarCompra(JavaPlugin plugin) {
		Bukkit.getConsoleSender().sendMessage("§aVerificando autenticacao do plugin.");
		String ip = getServerIp();
		ConfigAPI config = new ConfigAPI("licença.yml", plugin);
		config.add("comprador", "Coloque um nome.");
		config.add("key", "Coloque uma key.");
		config.saveDefault();
		String comprador = config.getString("comprador");
		String key = config.getString("key");
		String nomePlugin = plugin.getName();
		for (Compra compra : getCompras()) {
			if (verificarComprador) {
				if (!compra.getNomeComprador().equals(comprador)) {
					continue;
				}
			}
			if (verificarPlugin) {
				if (!compra.getNomePlugin().equals(nomePlugin)) {
					continue;
				}
			}
			if (verificarIp) {
				if (!compra.getIpHost().equals(ip)) {
					Bukkit.getConsoleSender().sendMessage("§cEste servidor nao possui o Ip correto da Chave do plugin");
					continue;
				}
			}
			if (compra.getChave().equals(key)) {
				Bukkit.getConsoleSender().sendMessage("§aPlugin ativado com sucesso.");
				return;
			}

		}

		Bukkit.getConsoleSender()
				.sendMessage("§cNao foi encontrado a key no nosso sistema, entre em contato com " + vendedor);
		Bukkit.getPluginManager().disablePlugin(plugin);
		throw new Error("Plugin nao liberado");

	}

	public static List<Compra> getCompras() {
		List<Compra> lista = new ArrayList<>();
		for (String linha : lerUmaLista()) {
			lista.add(getCompra(linha));
		}
		return lista;
	}

	private static List<String> lerUmaLista() {
		List<String> lista = new ArrayList<>();
		try {
			URLConnection connect = new URL(site + extra).openConnection();
			// connect.addRequestProperty("User-Agent",
			// "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			Scanner scan = new Scanner(connect.getInputStream());
			while (scan.hasNext()) {
				String text = scan.next();
				lista.add(text);
			}
			scan.close();

		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return lista;
	}

	@SuppressWarnings("unused")
	private static StringBuilder lerUmTexto() {
		StringBuilder sb = new StringBuilder();
		try {
			URLConnection connect = new URL(site + extra).openConnection();
			Scanner scan = new Scanner(connect.getInputStream());
			while (scan.hasNext()) {
				String text = scan.next();
				sb.append(text);
			}
			scan.close();

		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return sb;
	}

	public static class Compra {

		public String gerarKey() {
			return "" + this.nomePlugin + ";" + this.nomeComprador + ";" + this.dataCompra.getTime() + ";" + this.preco
					+ ";" + this.chave + ";" + this.ipHost;
		}

		private String nomePlugin;
		private String nomeComprador;
		private Date dataCompra;
		private double preco;
		private String chave;
		private String ipHost;

		public String getNomePlugin() {
			return nomePlugin;
		}

		public void setNomePlugin(String nomePlugin) {
			this.nomePlugin = nomePlugin;
		}

		public String getNomeComprador() {
			return nomeComprador;
		}

		public void setNomeComprador(String nomeComprador) {
			this.nomeComprador = nomeComprador;
		}

		public Date getDataCompra() {
			return dataCompra;
		}

		public void setDataCompra(Date dataCompra) {
			this.dataCompra = dataCompra;
		}

		public double getPreco() {
			return preco;
		}

		public void setPreco(double preco) {
			this.preco = preco;
		}

		public String getChave() {
			return chave;
		}

		public void setChave(String chave) {
			this.chave = chave;
		}

		public String getIpHost() {
			return ipHost;
		}

		public void setIpHost(String ipHost) {
			this.ipHost = ipHost;
		}

	}

	public static String getServerIp() {
		try {
			URLConnection connect = new URL("http://checkip.amazonaws.com/").openConnection();
			connect.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			Scanner scan = new Scanner(connect.getInputStream());
			StringBuilder sb = new StringBuilder();
			while (scan.hasNext()) {
				sb.append(scan.next());
			}
			scan.close();
			return sb.toString();

		} catch (Exception ex) {

			String ip = null;
			return ip;
		}
	}

}
