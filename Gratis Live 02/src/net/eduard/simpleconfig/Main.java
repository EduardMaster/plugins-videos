package net.eduard.simpleconfig;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static SimpleConfig config3;
	public void onEnable() {
		config3 = new SimpleConfig("contas.yml", this);
		SimpleConfig config = new SimpleConfig("configuracao.yml", this);
		
		
		
		config.saveConfig();
		
		
		
		
		config.reloadConfig();
		
		SimpleConfig config2 = new SimpleConfig("configexemplo.yml",this);
		config2.saveDefaultConfig();
		
		saveDinehiroJogador("Eduard", 100);
		
		
		double dinheiroSalvado = config3.getDouble("contas.Eduard");
		System.out.println("§cTeste"+dinheiroSalvado);
		Bukkit.broadcastMessage("teste");
		Bukkit.getConsoleSender().sendMessage("§adfasdfasdf");
		
		config3.add("exemplo", "&cMeu exemplo");
		config3.saveDefault();
		
		String msg = config3.message("exemplo");
		
		Bukkit.getConsoleSender().sendMessage("MENSAGEM DA CONFIG: "+msg);
		

	}
	
	public void saveDinehiroJogador(String nomePlayer,double dinheiro) {
		config3.set("contas."+nomePlayer, dinheiro);
		config3.saveConfig();
	}
	
	
}
