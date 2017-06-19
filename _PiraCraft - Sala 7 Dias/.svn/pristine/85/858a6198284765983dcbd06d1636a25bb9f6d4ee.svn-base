package com.hcp.win;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Eventos;
import com.hcp.Texts;
import com.hcp.daays.Days;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.PiraCraftAPI;
import br.com.piracraft.api.util.MySQL;

public class Utils {

	public static boolean isInTime(Player p) {
		boolean a = false;
		Calendar horaSaidaEntrada = Calendar.getInstance();
		horaSaidaEntrada.setTimeInMillis(new Date().getTime() - Eventos.tempoEntrada.get(Main.uuid.get(p)).getTime()
				+ Eventos.tempo.get(Main.uuid.get(p)).getTime());
		String horaString = new SimpleDateFormat("HH:mm:ss").format(horaSaidaEntrada.getTime());
		int hora = Integer.parseInt(horaString.split(":")[0]);

		if (hora >= Days.minimo) {
			a = true;
		}
		return a;
	}

	public static int counter = 11;

	public static void makeWin() {
		if (Days.days >= Days.ciclo && isHourToWin()) {
			if (Bukkit.getOnlinePlayers().size() == 1) {
				Player p = (Player) Bukkit.getOnlinePlayers().toArray()[0];
				if (isInTime(p)) {
					flyDragon(p);
					p.setNoDamageTicks(Integer.MAX_VALUE);
					p.setAllowFlight(true);

					MySQL.execute(
							"INSERT INTO MINIGAMES_XP (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`COINS`,`CASH`,`XP`,`DATAHORA`) VALUES ('"
									+ PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "'," + "'"
									+ Main.uuid.get(p) + "','5000','500','700','"
									+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')");

					MySQL.execute(
							"INSERT INTO 7_DIAS_ENTRADAS_SAIDAS (`UUID`,`ID_SALA`,`TIPO`,`VENCEDOR`) VALUES ('" + Main.uuid.get(p) + "','"+PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0]+"','2','1');");
					
					MySQL.execute("DELETE * FROM 7_DIAS_INSCRICAO_SALA WHERE ID_SALA = '"+PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0]+"'");
					
					Calendar c = Calendar.getInstance();
					c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+Days.ciclo);
					
					MySQL.execute("UPDATE MINIGAMES_SALAS_E_SERVIDORES SET 7_DIAS_COMPRADISPONIVEL = 1, 7_DIAS_ATIVO = 1, 7_DIAS_REVIVER_ATIVO = 1, "
							+ "7_DIAS_TOTAL_INGRESSOS_DISPONIVEIS = 150, DT_INICIAL = '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"', "
									+ "DT_FINAL = '"+new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())+" 18:00:00' WHERE ID_MINIGAMESSALAS = '"+PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0]+"'");

						Texts.sendTitle(p, "§4§lHardcore", "§7Voce venceu!", 10, 200, 20);
						new BukkitRunnable() {
							public void run() {
								counter -= 1;

								if (counter > 0) {
									p.sendMessage("§4§lHardcore§f§l» §eVoce venceu!");
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
									p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 1.0F, 1.0F);
								}
								
								if(counter == 1){
									for (Player p : Bukkit.getOnlinePlayers()) {
										com.hcp.utils.Utils.enviar(p, "lobbyhardcore");
									}
								}

								if (counter == 0) {
									cancel();
									counter = 11;

									restartServer(false);
								}
							}
						}.runTaskTimer(com.hcp.Main.plugin, 0, 20);
				} else {
					com.hcp.utils.Utils.enviar(p, "lobby");
					p.sendMessage("§4§lHardcore§f§l» §bPara ganhar uma partida no §e7 Dias §bvoce necessita ter §a"
							+ Days.minimo + " hora(s) §bde tempo jogado, portanto o jogo será finalizado.");
					new BukkitRunnable() {

						@Override
						public void run() {
							Bukkit.shutdown();
						}
					}.runTaskLater(com.hcp.Main.plugin, 40);
				}
			}
		} else {
			Bukkit.broadcastMessage("§4§lHardcore§f§l» §cNao pode haver um vencedor antes do ciclo de §d" + Days.ciclo
					+ " dias §cacabar.");
		}
	}

	public static void flyDragon(Player p) {
		EnderDragon dragao = Bukkit.getWorld("world").spawn(p.getLocation().add(0, 80, 0), EnderDragon.class);
		dragao.setPassenger(p);
	}

	public static void restartServer(boolean is18) {
		new BukkitRunnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					com.hcp.utils.Utils.enviar(p, "lobby");
				}
			}
		}.runTaskLater(com.hcp.Main.plugin, 200);

		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.shutdown();
			}
		}.runTaskLater(com.hcp.Main.plugin, 220);
	}

	public static boolean isHourToWin() {
		boolean a = false;

		try {
			Calendar d = Calendar.getInstance();
			d.setTime(new Date());
			
			Calendar dd = Calendar.getInstance();
			dd.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(com.hcp.Main.finalDate));

			if (d.getTime().getTime() >= dd.getTime().getTime()) {
				a = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return a;
	}

}
