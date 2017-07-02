package zLuck.zMain;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import zLuck.zComandos.*;
import zLuck.zEventos.*;
import zLuck.zHabilidades.Habilidade;
import zLuck.zHabilidades.Habilidades;
import zLuck.zGuis.*;
import zLuck.zTempos.Iniciando;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Schematic;
import zLuck.zUteis.TreinoPvP;
import zLuck.zUteis.Uteis;

public class zLuck extends JavaPlugin{
	
	public static Plugin pl;
	public static FileConfiguration mensagens;
	public static Estado estado;
	
	public void onLoad() {
		pl = this;
		Uteis.DeletarPasta(new File("world"));
		Uteis.RegistrarConfig();
		Uteis.ReceitasSopa();
		Bukkit.getConsoleSender().sendMessage(Uteis.prefix + " §cDeletando mundo...");
	}
	public void onEnable() {
		Uteis.IniciarTempos();
		Uteis.gerarBorda();
		
		RegistrarComandos();
		RegistrarEventos();
		
		estado = Estado.Iniciando;
		new Iniciando();
		new Schematic();	
       
		Bukkit.getWorld("world").setMonsterSpawnLimit(20);
        Bukkit.getConsoleSender().sendMessage(Uteis.prefix + " §cPlugin iniciado com sucesso!");
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Uteis.prefix + " §cPlugin desligado com sucesso");
	}
	
	void RegistrarEventos() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new Chat(), this);
		pm.registerEvents(new Entrar(), this);
		pm.registerEvents(new Sair(), this);
		pm.registerEvents(new Morrer(), this);
		pm.registerEvents(new Eventos(), this);
		pm.registerEvents(new Admin(), this);
		pm.registerEvents(new TreinoPvP(), this);
		pm.registerEvents(new Habilidades(), this);
		pm.registerEvents(new Habilidade(), this);
		pm.registerEvents(new KitGui(), this);
		pm.registerEvents(new PlayerStatus(), this);
	}
	void RegistrarComandos() {
		getCommand("kit").setExecutor(new Kit());
		getCommand("tempo").setExecutor(new Tempo());
		getCommand("admin").setExecutor(new Admin());
		getCommand("invsee").setExecutor(new Admin());
		getCommand("arena").setExecutor(new Arena());
		getCommand("bc").setExecutor(new Bc());
		getCommand("cm").setExecutor(new Cm());
		getCommand("desistir").setExecutor(new Desistir());
		getCommand("fly").setExecutor(new Fly());
		getCommand("forcefeast").setExecutor(new Force());
		getCommand("forceminifeast").setExecutor(new Force());
		getCommand("gm").setExecutor(new Gm());
		getCommand("head").setExecutor(new Head());
		getCommand("game").setExecutor(new Game());
		getCommand("ping").setExecutor(new Ping());
		getCommand("score").setExecutor(new Score());
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("s").setExecutor(new S());
		getCommand("tp").setExecutor(new Tp());
		getCommand("tpall").setExecutor(new Tp());
		getCommand("puxar").setExecutor(new Tp());
		getCommand("tell").setExecutor(new Tell());
		getCommand("yt").setExecutor(new Youtuber());
		getCommand("youtuber").setExecutor(new Youtuber());
		getCommand("skit").setExecutor(new Skit());
	}
/*	public static void MensagensAutomatica() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			public void run() {
				String[] Mensagens = { 	
						"§6§lFucky§7§lKits §7» Confira nossa loja e site: §6§lwww.FuckyKits.com.br",
						"§6§lFucky§7§lKits §7» Entre em nosso TeamSpeak 3 - §a§lts.fuckykits.com.br",
						"§6§lFucky§7§lKits §7» Compre §6§lVIP §7e tenha muitas vantagens, além de ajudar a manter o servidor sempre aberto",
						"§6§lFucky§7§lKits §7» Aplique-se para a staff usando o comando /§aformulario",
						"§6§lFucky§7§lKits §7» §eAdicione nossos servidores em sua lista!",
						"§6§lFucky§7§lKits §7» §fEstá ruim no PvP? treine no nosso servidor de §a§lKitPvP §fIP: §a§lpvp.fuckykits.com.br",
						"§6§lFucky§7§lKits §7» §fEntre em nossos servidores de KitPvP! IP: §a§lpvp.fuckykits.com.br",
						"§6§lFucky§7§lKits §7» §fTemos 3 servidores de HG abertos! IP: §fIP: §a§lA1.hg.fuckykits.com.br §faté §a§lA3"};
					
				Bukkit.broadcastMessage(Mensagens[new Random().nextInt(Mensagens.length)]);
			}
		}, 20, 60 * 20);
	} */
	public static Plugin getPlugin() {
		return pl;
	}

}
