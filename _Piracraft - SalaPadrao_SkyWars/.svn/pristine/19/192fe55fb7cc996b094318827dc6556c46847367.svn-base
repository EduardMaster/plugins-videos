package me.leo.skywars;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.PiraCraftAPI;

public class Jogador {

	public static HashMap<Player, Integer> coins = new HashMap<>();
	public static HashMap<Player, Integer> cash = new HashMap<>();
	
	public static ArrayList<String> gambi = new ArrayList<String>();

	public void entrar(Player p) {
		PiraCraftAPI u = new PiraCraftAPI(p);
		coins.put(p, Integer.valueOf(u.getCoins()));
		cash.put(p, Integer.valueOf(u.getCash()));
	}

	public void sair(Player p) {
		if (coins.containsKey(p)) {
			coins.remove(p);
		}
		if (cash.containsKey(p)) {
			cash.remove(p);
		}
	}

	public static void espectador(Player p) {
		me.leo.skywars.Main.jogadores.remove(p.getName());
		me.leo.skywars.Main.spec.add(p.getName());
		new BukkitRunnable() {
			@Override
			public void run() {
				p.setGameMode(GameMode.ADVENTURE);
				p.setAllowFlight(true);
				p.setFlying(true);

				Texts.sendTitle(p, "§c§lVOCE MORREU!", "§7Voce agora e um espectador.", 10, 40, 20);

				for (Player p2 : Bukkit.getOnlinePlayers()) {
					p2.hidePlayer(p);
				}
			}
		}.runTask(me.leo.skywars.Main.getInstance());
	}

	public static void bungeeSend(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		p.sendPluginMessage(me.leo.skywars.Main.getInstance(), "BungeeCord", out.toByteArray());
	}

	public static void removeBlocoBellow() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Location blockBelow = p.getLocation().subtract(0, 1, 0);
			blockBelow.getBlock().setType(Material.AIR);
		}
	}

	public static void setarDBKill(Player p, Player morreu) {
		if (br.com.piracraft.api.Main.isVip.get(p)) {
			Main.getMysql()
					.execute("INSERT INTO MINIGAMES_XP (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`COINS`,`DATAHORA`) VALUES ('"
							+ PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "'," + "'"
							+ Main.uuid.get(p) + "','40','"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')");
			
			Bukkit.broadcastMessage("§cSkyWars §e» §aO jogador §2" + p.getName() + " §aMatou o jogador §2"
					+ morreu.getName() + "§a e ganhou 40 coins!");
		} else {
			Main.getMysql()
					.execute("INSERT INTO MINIGAMES_XP (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`COINS`,`DATAHORA`) VALUES ('"
							+ PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "'," + "'"
							+ Main.uuid.get(p) + "','20','"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')");
			
			Bukkit.broadcastMessage("§cSkyWars §e» §aO jogador §2" + p.getName() + " §aMatou o jogador §2"
					+ morreu.getName() + "§a e ganhou 20 coins!");
		}
	}

	public static void setarXPWinner(Player p) {
		Main.getMysql()
		.execute("INSERT INTO MINIGAMES_XP (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`XP`,`DATAHORA`) VALUES ('"
				+ PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "'," + "'" + Main.uuid.get(p)
				+ "','500','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')");
	}

	public static void morrer(Player p) {
		Main.getMysql()
		.execute("INSERT INTO MINIGAMES_XP (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`XP`,`COINS`,`DATAHORA`) VALUES ('"
				+ PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "'," + "'" + Main.uuid.get(p)
				+ "','-10','-10','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')");
		
		Bukkit.broadcastMessage(
				"§cSkyWars §e» §aO jogador §2" + p.getName() + " §amorreu e perdeu 10 coins!");

	}

}
