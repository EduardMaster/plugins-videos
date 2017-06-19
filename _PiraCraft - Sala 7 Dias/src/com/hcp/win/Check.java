package com.hcp.win;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Eventos;
import com.hcp.Main;
import com.hcp.Texts;
import com.hcp.daays.Days;
import com.hcp.utils.Utils;

import br.com.piracraft.api.PiraCraftAPI;
import br.com.piracraft.api.util.MySQL;

public class Check {
	
	public static int time = 0;

	public static void startCheck() {
		new BukkitRunnable() {
			public void run() {
				time=+1;
				
				if (Days.days >= Days.ciclo) {
					try {
						Calendar d = Calendar.getInstance();
						d.setTime(new Date());
						
						Calendar dd = Calendar.getInstance();
						dd.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(com.hcp.Main.finalDate));
						
						int x = dd.get(Calendar.AM_PM) - d.get(Calendar.AM_PM);

						if (x == 1) {
							Bukkit.getConsoleSender().sendMessage("§4§lAlerta§f§l» §bMensagem de 1 hora.");
							
							for(Player p : Bukkit.getOnlinePlayers()){
								Texts.sendTitle(p, "§4§lHardcore", "§aVitoria a quem sobreviver!", 10, 40, 20);
							}
							
							MySQL.execute("UPDATE MINIGAMES_SALAS_E_SERVIDORES SET 7_DIAS_REVIVER_ATIVO = 0, 7_DIAS_COMPRADISPONIVEL = 0 WHERE ID_MINIGAMESSALAS = " + PiraCraftAPI.getIdNetwork()[0]);
							sendMobsEvery5Min();
							
							cancel();
						}else{
							if(new SimpleDateFormat("mm:ss").format(d.getTime()).equals("30:00")){
								for(Player p : Bukkit.getOnlinePlayers()){
									Texts.sendTitle(p, "§4§lHardcore", "§aEstamos a um passo da vitoria!", 10, 40, 20);
								}
								
								Bukkit.getConsoleSender().sendMessage("§4§lAlerta§f§l» §bMensagem de 30 minutos.");
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}.runTaskTimer(Main.plugin, 0, 5);
	}
	
	public static void sendMobsEvery5Min(){
		new BukkitRunnable() {
			public void run() {
				Utils.spawnMobOnRandomLoc();
			}
		}.runTaskTimer(Main.plugin, 0, 6000);
	}

}
