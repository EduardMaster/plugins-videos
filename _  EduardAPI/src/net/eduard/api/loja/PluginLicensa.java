package net.eduard.api.loja;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.setup.ConfigAPI;

public class PluginLicensa {
	public static void proteger(JavaPlugin plugin) {

		ConfigAPI config = new ConfigAPI("licensa.yml", plugin);

		config.add("key", "coloque_a_key_aqui");
		config.saveDefault();
		PluginLicensa compra = new PluginLicensa();
		compra.setPlugin(plugin.getName());
		compra.setKey(config.getString("key"));
		List<PluginLicensa> vendas = getVendas("site");
		for (PluginLicensa venda : vendas) {
			if (venda.equals(compra)) {
				
				break;
			}
		}
		// String key = config.getString("key");

	}

	public static List<PluginLicensa> getVendas(String url) {
		List<PluginLicensa> vendas = new ArrayList<>();
		try {

			URLConnection connect = new URL(url).openConnection();
			connect.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			Scanner scan = new Scanner(connect.getInputStream());
			while (scan.hasNext()) {
				String line = scan.nextLine();
				if (line.isEmpty() | line.startsWith("|")) {
					continue;
				}
				vendas.add(new PluginLicensa(line));
			}
			scan.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendas;
	}

	private String plugin;
	private String key;

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public PluginLicensa(String plugin, String key) {
		this.plugin = plugin;
		this.key = key;
	}

	public PluginLicensa(String line) {
		String[] split = line.split(" ");
		this.plugin = split[0];
		this.key = split[1];
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((plugin == null) ? 0 : plugin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PluginLicensa other = (PluginLicensa) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (plugin == null) {
			if (other.plugin != null)
				return false;
		} else if (!plugin.equals(other.plugin))
			return false;
		return true;
	}

	public PluginLicensa() {
		// TODO Auto-generated constructor stub
	}

}
