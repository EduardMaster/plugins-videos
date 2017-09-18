package net.eduard.template;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements  Listener{	
	@Override
	public void onEnable() {Bukkit.getPluginManager().registerEvents(this, this);
	
	MysqlTest.criarTabela();
	}
	@EventHandler
	public void event(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (!MysqlTest.hasJogador(p)){
			MysqlTest.addJogador(p);
		}
		MysqlTest.setJogador(p, 50000);
	}

}
