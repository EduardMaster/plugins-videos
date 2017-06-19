package com.hcp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Eventos;
import com.hcp.Main;
import com.hcp.SSB;
import com.hcp.daays.Days;
import com.hcp.daays.Difficulty;
import com.hcp.mercado.Entradas;

import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.util.MySQL;

public class Start {
	
	@SuppressWarnings("deprecation")
	public static void loadAll(){
		try {
			ResultSet rs = MySQL.getQueryResult("SELECT * FROM V_LOJAMINECRAFT_BAUS ORDER BY `ID`");
			while (rs.next()) {
				Main.boxItems.add(Material.getMaterial(rs.getInt("ID_ITEM")));
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet rs = MySQL.getQueryResult(
					"SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE PORTA = '" + Bukkit.getPort() + "'");
			if (rs.next()) {
				Days.ciclo = rs.getInt("7_DIAS_CICLO_DA_SALA");
				Days.minimo = rs.getInt("7_DIAS_TEMPO_MINIMO");
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		new BukkitRunnable() {
			public void run() {
				Entradas.whitelist = new ArrayList<Entradas>();
				Bukkit.getConsoleSender().sendMessage("§6Jogadores que compraram reviver nos ultimos 5 minutos:");

				try {
					ResultSet rs = MySQL.getQueryResult(
							"SELECT * FROM V_7_DIAS_USUARIOS_LIBERADOS WHERE ACESSO_BLOQUEADO = 0 ORDER BY `ID_SALA`");
					while (rs.next()) {
						Entradas e = new Entradas();

						e.setUuid(rs.getString("UUID"));

						Entradas.whitelist.add(e);
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					ResultSet rs = MySQL.getQueryResult("SELECT * FROM USUARIO ORDER BY `ID`");
					while (rs.next()) {
						for (int x = 0; x < Entradas.whitelist.size(); x++) {
							if (Entradas.whitelist.get(x).getUuid().equalsIgnoreCase(rs.getString("UUID"))) {
								OfflinePlayer op = Bukkit.getOfflinePlayer(rs.getString("NICK_NAME"));
								if (op.isBanned()) {
									op.setBanned(false);
									Bukkit.getConsoleSender().sendMessage("§a" + op.getName());
								}
							}
						}
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(Main.plugin, 0, 20 * 300);

		new BukkitRunnable() {
			public void run() {
				try {
					ResultSet rs = MySQL.getQueryResult(
							"SELECT SALA_REFRESH FROM MINIGAMES_SALAS_E_SERVIDORES WHERE SALA_REFRESH = 2");
					if (rs.next()) {
						MySQL.execute(
								"UPDATE MINIGAMES_SALAS_E_SERVIDORES SET SALA_REFRESH = 1");

						Entradas.whitelist = new ArrayList<Entradas>();
						Bukkit.getConsoleSender()
								.sendMessage("§6Jogadores que compraram reviver nos ultimos 5 minutos:");

						try {
							ResultSet r = MySQL.getQueryResult(
											"SELECT * FROM V_7_DIAS_USUARIOS_LIBERADOS WHERE ACESSO_BLOQUEADO = 0 ORDER BY `ID_SALA`");
							while (r.next()) {
								Entradas e = new Entradas();

								e.setUuid(r.getString("UUID"));

								Entradas.whitelist.add(e);
							}
							r.getStatement().getConnection().close();
						} catch (SQLException e) {
							e.printStackTrace();
						}

						try {
							ResultSet r = MySQL.getQueryResult("SELECT * FROM USUARIO ORDER BY `ID`");
							while (r.next()) {
								for (int x = 0; x < Entradas.whitelist.size(); x++) {
									if (Entradas.whitelist.get(x).getUuid().equalsIgnoreCase(r.getString("UUID"))) {
										OfflinePlayer op = Bukkit.getOfflinePlayer(r.getString("NICK_NAME"));
										if (op.isBanned()) {
											op.setBanned(false);
											Bukkit.getConsoleSender().sendMessage("§a" + op.getName());
										}
									}
								}
							}
							r.getStatement().getConnection().close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					rs.getStatement().getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(Main.plugin, 0, 200);
	}
	
	public static void doIt(){
		String s = new SimpleDateFormat("HH").format(new Date());
		String s1 = new SimpleDateFormat("mm").format(new Date());
		System.out.println("Hora " + s + ":" + s1);

		try {
			ResultSet a = MySQL.getQueryResult(
					"SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE PORTA = '" + Bukkit.getPort() + "';");
			if (a.next()) {
				Main.inicialDate = a.getString("DT_INICIAL");
				Main.finalDate = a.getString("DT_FINAL");
			}
			a.getStatement().getConnection().close();
		} catch (SQLException e) {
			System.out.println("ERRO - Main: " + e.getCause());
		}

		Date d = new Date();
		Date dd = null;
		try {
			dd = new SimpleDateFormat("yyyy-MM-dd").parse(Main.inicialDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		Calendar hoje = Calendar.getInstance();
		hoje.setTime(d);

		Calendar banco = Calendar.getInstance();
		banco.setTime(dd);

		System.out.println("Inicial - " + banco.get(Calendar.DAY_OF_MONTH));
		System.out.println("Hoje - " + hoje.get(Calendar.DAY_OF_MONTH));
		
		System.out.println("Data do sql: " + Main.inicialDate);
		System.out.println("Data cortada do sql: " + com.hcp.Main.inicialDate.substring(11, 19));

		long subtracao = (long) (hoje.getTime().getTime() - banco.getTime().getTime());
		
		Calendar resultado = Calendar.getInstance();
		resultado.setTime(new Date(subtracao));
		resultado.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
		
		Days.days = resultado.get(Calendar.DAY_OF_MONTH)+2;
		
		new BukkitRunnable() {
			public void run() {
				if (Main.iniciado == false) {
					if (new SimpleDateFormat("yyyy-MM-ss HH:mm:ss").format(new Date()).equals(com.hcp.Main.inicialDate)) {
						cancel();
						Main.iniciado=true;
						Days.startTheGame();
						Days.startCheck();

						System.out.println("------------------------");
						System.out.println("Dia - " + Days.days);
						System.out.println("Ciclo - " + Days.ciclo);
						System.out.println("Hora do checkWin - " + Main.finalDate);
						System.out.println(" ");

						try {
							for (int x = 0; x < br.com.piracraft.api.Main.hard.size(); x++) {
								if (br.com.piracraft.api.Main.hard.get(x).getIdDias() == Days.days) {
									Difficulty.damage = br.com.piracraft.api.Main.hard.get(x).getIntensidade();
									Difficulty.creeperRange = br.com.piracraft.api.Main.hard.get(x).getRange();
								}
							}

							System.out.println("Dano - " + Difficulty.damage);
							System.out.println("CreeperRange - " + Difficulty.creeperRange);
							System.out.println("------------------------");
						} catch (NullPointerException e) {
							System.out.println("ERRO - " + e.getCause());
						}

						Bukkit.getConsoleSender().sendMessage("§aRelogio iniciado!");
						Bukkit.getConsoleSender()
								.sendMessage("§aHora - §6" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
						
						if(Bukkit.getOnlinePlayers().size() >= 1){
							for(Player p : Bukkit.getOnlinePlayers()){

								Eventos.coins.put(p, 0);
								Eventos.cash.put(p, 0);

								Eventos.tempoEntrada.put(br.com.piracraft.api.Main.uuid.get(p), new Date());

								try {
									ResultSet rs = MySQL.getQueryResult("SELECT * FROM 7_DIAS_INSCRICAO_SALA WHERE UUID = '" + br.com.piracraft.api.Main.uuid.get(p) + "'");
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

								p.teleport(com.hcp.utils.Utils.getRandomLocation().add(0, 20, 0));

								p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 15));
								p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 400, 15));

								p.sendMessage("§4§lHardcore§f§l» §eVoce esta invencivel por 20 segundos.");
								p.setFireTicks(0);

								String as = new SimpleDateFormat("HH:mm").format(new Date());
								for (Player s : Bukkit.getOnlinePlayers()) {
									if (br.com.piracraft.api.Main.network.get(s) == 1) {
										Score score = new Score(br.com.piracraft.api.Main.uuid.get(s), "§6§lPira§f§lCraft", 
												Arrays.asList("§0 ",
												"Coins: §b" + Eventos.coins.get(s),
												"Cash: §b" + Eventos.cash.get(s),
												"§3 ",
												"Jogadores: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
												"Relogio: §b" + as,
												"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(br.com.piracraft.api.Main.uuid.get(s))),
												"Dia: §b" + Days.days,
												"§4 ",
												"§apiracraft.com.br"));
										
										score.create();
										score.set(s);
									} else {
										Score score = new Score(br.com.piracraft.api.Main.uuid.get(s), "§4§lU§6§lPlay§9§lCraft", 
												Arrays.asList("§0 ",
												"Coins: §b" + Eventos.coins.get(s),
												"Cash: §b" + Eventos.cash.get(s),
												"§3 ",
												"Players: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
												"Clock: §b" + as,
												"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(br.com.piracraft.api.Main.uuid.get(s))),
												"Day: §b" + Days.days,
												"§4 ",
												"§auplaycraft.com"));
										
										score.create();
										score.set(s);
									}
								}

								p.getWorld().setDifficulty(org.bukkit.Difficulty.HARD);
								p.getInventory().setItem(8, ItemAPI.Criar(Material.ENDER_CHEST, 1, 0, "§cMeus itens", true));
								p.setGameMode(GameMode.SURVIVAL);
							}
						}
					}
				} else {
					cancel();
				}
			}
		}.runTaskTimer(Main.plugin, 0, 5);
	}

}
