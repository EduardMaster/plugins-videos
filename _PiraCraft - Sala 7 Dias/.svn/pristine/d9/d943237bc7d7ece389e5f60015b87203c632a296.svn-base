package com.hcp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.hcp.Eventos;
import com.hcp.SSB;
import com.hcp.daays.Days;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.PiraCraftAPI;
import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.util.MySQL;

public class Join {
	
	public static Map<String, Boolean> firstJoin = new HashMap<String, Boolean>();
	
	public static void giveItems(Player p){
		MySQL.execute(
				"INSERT INTO 7_DIAS_ENTRADAS_SAIDAS (`UUID`,`ID_SALA`,`TIPO`) VALUES ('" + Main.uuid.get(p) + "','"+PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0]+"','1');");

		Eventos.coins.put(p, 0);
		Eventos.cash.put(p, 0);

		Eventos.tempoEntrada.put(Main.uuid.get(p), new Date());

		try {
			ResultSet rs = MySQL.getQueryResult("SELECT * FROM 7_DIAS_INSCRICAO_SALA WHERE UUID = '" + Main.uuid.get(p) + "' AND ID_NETWORK = '"+Main.network.get(p)+"'");
			if (rs.next()) {
				try {
					Eventos.tempo.put(rs.getString("UUID"),
							new SimpleDateFormat("HH:mm:ss").parse(rs.getString("TEMPO_CORRIDO")));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		
		String as = new SimpleDateFormat("HH:mm").format(new Date());
		for (Player s : Bukkit.getOnlinePlayers()) {
			if (br.com.piracraft.api.Main.network.get(s) == 1) {
				Score score = new Score(Main.uuid.get(s), "§6§lPira§f§lCraft", 
						Arrays.asList("§0 ",
						"Coins: §b" + Eventos.coins.get(s),
						"Cash: §b" + Eventos.cash.get(s),
						"§3 ",
						"Jogadores: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
						"Relogio: §b" + as,
						"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(Main.uuid.get(s))),
						"Dia: §b" + Days.days,
						"§4 ",
						"§apiracraft.com.br"));
				
				score.create();
				score.set(s);
			} else {
				Score score = new Score(Main.uuid.get(s), "§4§lU§6§lPlay§9§lCraft", 
						Arrays.asList("§0 ",
						"Coins: §b" + Eventos.coins.get(s),
						"Cash: §b" + Eventos.cash.get(s),
						"§3 ",
						"Players: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
						"Clock: §b" + as,
						"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(Main.uuid.get(s))),
						"Day: §b" + Days.days,
						"§4 ",
						"§auplaycraft.com"));
				
				score.create();
				score.set(s);
			}
		}
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 15));
		p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 400, 15));

		p.sendMessage("§4§lHardcore§f§l» §eVoce esta invencivel por 20 segundos.");
		p.setFireTicks(0);

		p.getWorld().setDifficulty(org.bukkit.Difficulty.HARD);
		
		if(!firstJoin.containsKey(p.getName())){
			p.getInventory().clear();
			if(com.hcp.Main.iniciado){
				firstJoin.put(p.getName(), true);
				if(!Eventos.startLoc.containsKey(Main.uuid.get(p))){
					Eventos.startLoc.put(Main.uuid.get(p), com.hcp.utils.Utils.getRandomLocation().add(0, 20, 0));
				}
				p.teleport(Eventos.startLoc.get(Main.uuid.get(p)));
				p.getInventory().setItem(8, ItemAPI.Criar(Material.ENDER_CHEST, 1, 0, "§cMeus itens", true));
			}else{
				p.setGameMode(GameMode.ADVENTURE);
				p.getWorld().setDifficulty(org.bukkit.Difficulty.PEACEFUL);
				p.teleport(new Location(p.getWorld(), 0, 80, 0));
				
				if (br.com.piracraft.api.Main.network.get(p) == 1) {
					Score score = new Score(Main.uuid.get(p), "§6§lPira§f§lCraft", 
							Arrays.asList("§0 ",
							"§cAguardando...",
							"§eInicia em §b" + com.hcp.Main.inicialDate.substring(11, 19),
							"§3 "));
					
					score.create();
					score.set(p);
				} else {
					SSB ss = new SSB("§4§lU§6§lPlay§9§lCraft");

					ss.add("§0 ", 7);
					ss.add("§cWaiting...", 6);
					ss.add("§eStarting at §b" + com.hcp.Main.inicialDate.substring(11, 19), 5);
					ss.add("§1 ", 4);

					ss.build();
					ss.send(p);
				}
			}
		}else{
			p.teleport(Eventos.startLoc.get(Main.uuid.get(p)));
		}
	}

}
