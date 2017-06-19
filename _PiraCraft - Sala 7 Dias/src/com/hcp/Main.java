package com.hcp;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.api.Admin;
import com.hcp.cmd.Teste;
import com.hcp.cmd.Utils;
import com.hcp.daays.Days;
import com.hcp.daays.Difficulty;
import com.hcp.daays.Schemas;
import com.hcp.mercado.Bau;
import com.hcp.mercado.Entradas;
import com.hcp.utils.Score;
import com.hcp.utils.Start;
import com.hcp.win.Check;

import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.util.MySQL;

public class Main extends JavaPlugin {

	public static Plugin plugin;

	public static List<Schemas> schematic = new ArrayList<Schemas>();

	public static boolean b = true;
	public static String inicialDate = null;
	public static String finalDate = null;

	public static boolean iniciado = false;
	public static boolean rain = false;

	public static Map<String, Location> locations = new HashMap<String, Location>();

	@Override
	public void onLoad() {
	}

	public void removerArquivos(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}
	
	public void onEnable() {
		plugin = this;

		Bukkit.getWorld("world").setAutoSave(false);

		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		Bukkit.getPluginManager().registerEvents(new Admin(), this);
		Bukkit.getPluginManager().registerEvents(new Bau(), this);

		Bukkit.getWorld("world").setDifficulty(org.bukkit.Difficulty.HARD);
		
		Admin.isUsingAdmin();

		getCommand("start").setExecutor(new Teste());
		getCommand("test").setExecutor(new Teste());
		getCommand("caixa").setExecutor(new Teste());
		getCommand("chuva").setExecutor(new Teste());

		getCommand("cc").setExecutor(new Utils());
		getCommand("hardcore").setExecutor(new Utils());

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		new BukkitRunnable() {
			public void run() {
				if (!rain) {
					Bukkit.getWorld("world").setStorm(false);
				}
			}
		}.runTaskTimer(this, 0, 20);

		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player s : Bukkit.getOnlinePlayers()) {

					if (locations.containsKey(s.getName())) {
						if (locations.get(s.getName()).equals(s.getLocation())) {
							if (br.com.piracraft.api.Main.network.get(s) == 1) {
								s.sendMessage("§4§lHardcore§f§l» §eVoce passou muito tempo sem se mexer.");
								com.hcp.utils.Utils.enviar(s, "lobby");
							} else {
								s.sendMessage("§4§lHardcore§f§l» §eYou didn't moved for a long time.");
								com.hcp.utils.Utils.enviar(s, "lobby");
							}
						} else {
							locations.put(s.getName(), s.getLocation());
						}
					} else {
						locations.put(s.getName(), s.getLocation());
					}
				}
			}
		}.runTaskTimer(this, 0, 20 * 180);

		new BukkitRunnable() {
			public void run() {
				if(iniciado){
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
				}
			}
		}.runTaskTimer(this, 0, 27 * 60);

		WorldBorder wb = Bukkit.getServer().getWorld("world").getWorldBorder();
		
		wb.setSize(1000);
		wb.setCenter(0, 0);
		wb.setDamageAmount(2.0);
		
		Start.doIt();
		Start.loadAll();
		Check.startCheck();
	}

	public static List<Material> boxItems = new ArrayList<Material>();
}
