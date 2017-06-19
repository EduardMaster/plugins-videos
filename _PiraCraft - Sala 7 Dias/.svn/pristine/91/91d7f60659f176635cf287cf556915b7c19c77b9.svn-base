package com.hcp.daays;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Main;
import com.hcp.gift.GiftUtils;

import br.com.piracraft.api.games.util.Hardcore;
import br.com.piracraft.api.util.MySQL;

public class Days {

	public static boolean day = true;
	public static int days = 0;
	public static int ciclo = 0;
	public static int minimo = 0;
	public static int clock = 0;

	public static int time = 0;
	public static long hours = 6000;

	public static int caixa = 0;
	public static boolean spawned = false;

	public static void startCheck() {
		Date d = new Date();
		Calendar r = Calendar.getInstance();
		String ss = new SimpleDateFormat("mm:ss").format(d);
		r.setTime(d);
		clock = r.get(Calendar.HOUR_OF_DAY);

		if (clock >= 06 && clock < 18) {
			Bukkit.getWorld("world").setTime(6000);
			hours = Bukkit.getWorld("world").getTime();
			day = true;
		}

		if (clock >= 18) {
			Bukkit.getWorld("world").setTime(18000);
			hours = Bukkit.getWorld("world").getTime();
			day = false;
		}

		if (ss.equals("00:00")) {
			com.hcp.utils.Utils.spawnMobOnRandomLoc();
		}
	}

	public static void startTheGame() {
		new BukkitRunnable() {
			public void run() {
				Date d = new Date();
				String s = new SimpleDateFormat("HH:mm:ss").format(d);
				String ss = new SimpleDateFormat("mm:ss").format(d);
				Calendar r = Calendar.getInstance();
				r.setTime(d);
				clock = r.get(Calendar.HOUR_OF_DAY);

				if (clock == 06) {
					Bukkit.getWorld("world").setTime(6000);
					hours = Bukkit.getWorld("world").getTime();
					day = true;
				}

				if (clock >= 00 && clock <= 05) {
					Bukkit.getWorld("world").setTime(18000);
					hours = Bukkit.getWorld("world").getTime();
					day = false;
				}

				if (clock == 18) {
					Bukkit.getWorld("world").setTime(18000);
					hours = Bukkit.getWorld("world").getTime();
					day = false;
				}

				if (s.equals("00:00:01")) {
					days += 1;

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
				}

				if (ss.equals("00:00")) {
					com.hcp.utils.Utils.spawnMobOnRandomLoc();

					try {
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
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(ss.equals("15:00")){
					Main.rain = true;
					Bukkit.getConsoleSender().sendMessage("§aChuva§f§l» §eChuva iniciada.");
					Bukkit.getWorld("world").setStorm(true);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "toggledownfall");
					
					new BukkitRunnable() {
						public void run() {
							Main.rain = false;
							Bukkit.getConsoleSender().sendMessage("§aChuva§f§l» §eChuva parou.");
						}
					}.runTaskLater(Main.plugin, 20 * 300);
				}

				if (ss.equals("45:00")) {
					GiftUtils.spawnBoxes();
				}

				Bukkit.getWorld("world").setTime(Days.hours);
			}
		}.runTaskTimer(Main.plugin, 0, 20);
	}

	public static String formatIntoHHMMSS(int secs) {
		int remainder = secs % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;

		return new StringBuilder().append(minutes).append(":").append(seconds < 10 ? "0" : "").append(seconds)
				.toString();
	}
}
