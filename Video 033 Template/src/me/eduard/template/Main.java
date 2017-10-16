
package me.eduard.template;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private static JavaPlugin plugin;
	
	public static JavaPlugin getPlugin() {
		return plugin;
	}
	public static FileConfiguration getConfigs() {
		return getPlugin().getConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para players!");
			return true;
		}
		// Player p = (Player) sender;

		return true;
	}
	@EventHandler
	public void QuandoJogadorMover(PlayerMoveEvent evento) {
		//Player jogador = evento.getPlayer();
	}


	@Override
	public void onLoad() {
		registrarConfigs();
	}

	@Override
	public void onEnable() {
		
		registrarComandos();
		registrarEventos();
		

	}
	@Override
	public void onDisable() {

//		HandlerList.unregisterAll();
//	    Não precisa por isso mais
	}
	
	public void registrarConfigs() {
		getConfig().addDefault("texto", "Hoje voce vai se sentir otimo");
		salvarDefaultDaConfig();
	}
	
	public void salvarConfig() {
		saveConfig();
	}
	public void salvarDefaultDaConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void registrarComandos() {
		registrarComando("teste", new SimplesComando());
		
	}
	public void registrarEventos() {
		registrarListener(new SimplesEvento());
		
	}
	public void registrarListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	public void registrarComando(String nome,CommandExecutor comando) {
		getCommand(nome).setExecutor(comando);
	}

	

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}
}
