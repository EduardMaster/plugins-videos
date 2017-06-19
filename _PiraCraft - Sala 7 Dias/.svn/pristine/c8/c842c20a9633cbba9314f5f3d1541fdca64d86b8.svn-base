package com.hcp.cmd;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Eventos;
import com.hcp.Main;
import com.hcp.SSB;
import com.hcp.daays.BoxContent;
import com.hcp.daays.Days;
import com.hcp.daays.Difficulty;
import com.hcp.daays.Schemas;
import com.hcp.gift.GiftUtils;
import com.hcp.utils.Score;
import com.hcp.utils.Start;
import com.hcp.win.Check;

import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.games.util.Hardcore;
import br.com.piracraft.api.util.MySQL;

public class Teste implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
		if(s instanceof Player){
			if(br.com.piracraft.api.Main.isStaff.get((Player)s)){
				if (cmd.getName().equalsIgnoreCase("test")) {
					try {
						br.com.piracraft.api.Main.hard = new ArrayList<Hardcore>();
						ResultSet rs = MySQL.getQueryResult("SELECT * FROM `V_SALA_7_DIAS_MOBS_INTENSIDADE` ORDER BY `IDDIAS`");
						while (rs.next()) {
							Hardcore h = new Hardcore();
							h.setId(rs.getInt("ID"));
							h.setIdDias(rs.getInt("IDDIAS"));
							h.setIdMob(rs.getInt("ID_MOB"));
							h.setIdNetwork(rs.getInt("ID_NETWORK"));
							h.setNomeBukkit(rs.getString("MOB_BUKKIT"));
							h.setIntensidade(rs.getInt("INTENSIDADE"));
							h.setRange(rs.getInt("MOB_RANGE"));
							h.setIdSala(rs.getInt("ID_SALA"));

							br.com.piracraft.api.Main.hard.add(h);
						}
						rs.getStatement().getConnection().close();
						com.hcp.utils.Utils.spawnMobOnRandomLoc();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (cmd.getName().equalsIgnoreCase("chuva")) {
					Main.rain = true;
					Bukkit.getWorld("world").setStorm(true);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "toggledownfall");

					new BukkitRunnable() {
						public void run() {
							Main.rain = false;
						}
					}.runTaskLater(Main.plugin, 20 * 300);
				}
				if (cmd.getName().equalsIgnoreCase("caixa")) {
					if(br.com.piracraft.api.Main.isStaff.get((Player)s)){
						GiftUtils.spawnBoxes();
					}
				}
				if (cmd.getName().equalsIgnoreCase("start")) {
					if(!Main.iniciado){
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
								for (Player sss : Bukkit.getOnlinePlayers()) {
									if (br.com.piracraft.api.Main.network.get(s) == 1) {
										Score score = new Score(br.com.piracraft.api.Main.uuid.get(sss), "§6§lPira§f§lCraft", 
												Arrays.asList("§0 ",
												"Coins: §b" + Eventos.coins.get(sss),
												"Cash: §b" + Eventos.cash.get(sss),
												"§3 ",
												"Jogadores: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
												"Relogio: §b" + as,
												"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(br.com.piracraft.api.Main.uuid.get(sss))),
												"Dia: §b" + Days.days,
												"§4 ",
												"§apiracraft.com.br"));
										
										score.create();
										score.set(sss);
									} else {
										Score score = new Score(br.com.piracraft.api.Main.uuid.get(sss), "§4§lU§6§lPlay§9§lCraft", 
												Arrays.asList("§0 ",
												"Coins: §b" + Eventos.coins.get(sss),
												"Cash: §b" + Eventos.cash.get(sss),
												"§3 ",
												"Players: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
												"Clock: §b" + as,
												"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(br.com.piracraft.api.Main.uuid.get(sss))),
												"Day: §b" + Days.days,
												"§4 ",
												"§auplaycraft.com"));
										
										score.create();
										score.set(sss);
									}
								}

								p.getWorld().setDifficulty(org.bukkit.Difficulty.HARD);
								p.getInventory().setItem(8, ItemAPI.Criar(Material.ENDER_CHEST, 1, 0, "§cMeus itens", true));
								p.setGameMode(GameMode.SURVIVAL);
							}
						}
						
						Main.iniciado = true;
					}else{
						s.sendMessage("§4§lHardcore§f§l» §cA partida ja foi iniciada.");
					}
				}
			}
		}else{
			if (cmd.getName().equalsIgnoreCase("test")) {
				try {
					br.com.piracraft.api.Main.hard = new ArrayList<Hardcore>();
					ResultSet rs = MySQL.getQueryResult("SELECT * FROM `V_SALA_7_DIAS_MOBS_INTENSIDADE` ORDER BY `IDDIAS`");
					while (rs.next()) {
						Hardcore h = new Hardcore();
						h.setId(rs.getInt("ID"));
						h.setIdDias(rs.getInt("IDDIAS"));
						h.setIdMob(rs.getInt("ID_MOB"));
						h.setIdNetwork(rs.getInt("ID_NETWORK"));
						h.setNomeBukkit(rs.getString("MOB_BUKKIT"));
						h.setIntensidade(rs.getInt("INTENSIDADE"));
						h.setRange(rs.getInt("MOB_RANGE"));
						h.setIdSala(rs.getInt("ID_SALA"));

						br.com.piracraft.api.Main.hard.add(h);
					}
					rs.getStatement().getConnection().close();
					com.hcp.utils.Utils.spawnMobOnRandomLoc();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (cmd.getName().equalsIgnoreCase("chuva")) {
				Main.rain = true;
				Bukkit.getWorld("world").setStorm(true);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "toggledownfall");

				new BukkitRunnable() {
					public void run() {
						Main.rain = false;
					}
				}.runTaskLater(Main.plugin, 20 * 300);
			}
			if (cmd.getName().equalsIgnoreCase("caixa")) {
				GiftUtils.spawnBoxes();
			}
			if (cmd.getName().equalsIgnoreCase("start")) {
				if(!Main.iniciado){
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
							for (Player sss : Bukkit.getOnlinePlayers()) {
								if (br.com.piracraft.api.Main.network.get(s) == 1) {
									Score score = new Score(br.com.piracraft.api.Main.uuid.get(sss), "§6§lPira§f§lCraft", 
											Arrays.asList("§0 ",
											"Coins: §b" + Eventos.coins.get(sss),
											"Cash: §b" + Eventos.cash.get(sss),
											"§3 ",
											"Jogadores: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
											"Relogio: §b" + as,
											"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(br.com.piracraft.api.Main.uuid.get(sss))),
											"Dia: §b" + Days.days,
											"§4 ",
											"§apiracraft.com.br"));
									
									score.create();
									score.set(sss);
								} else {
									Score score = new Score(br.com.piracraft.api.Main.uuid.get(sss), "§4§lU§6§lPlay§9§lCraft", 
											Arrays.asList("§0 ",
											"Coins: §b" + Eventos.coins.get(sss),
											"Cash: §b" + Eventos.cash.get(sss),
											"§3 ",
											"Players: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
											"Clock: §b" + as,
											"TimePlay §b" + new SimpleDateFormat("HH:mm:ss").format(Eventos.tempo.get(br.com.piracraft.api.Main.uuid.get(sss))),
											"Day: §b" + Days.days,
											"§4 ",
											"§auplaycraft.com"));
									
									score.create();
									score.set(sss);
								}
							}

							p.getWorld().setDifficulty(org.bukkit.Difficulty.HARD);
							p.getInventory().setItem(8, ItemAPI.Criar(Material.ENDER_CHEST, 1, 0, "§cMeus itens", true));
							p.setGameMode(GameMode.SURVIVAL);
						}
					}
					
					Main.iniciado = true;
				}else{
					s.sendMessage("§4§lHardcore§f§l» §cA partida ja foi iniciada.");
				}
			}
		}
		return false;
	}

}
