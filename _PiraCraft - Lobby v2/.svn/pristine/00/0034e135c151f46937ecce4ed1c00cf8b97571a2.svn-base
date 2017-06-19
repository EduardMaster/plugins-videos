package br.com.piracraft.lobby2;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.util.MySQL;
import br.com.piracraft.lobby2.event.AntiAfkSystem;
import br.com.piracraft.lobby2.event.BlockManager;
import br.com.piracraft.lobby2.event.GameManager;
import br.com.piracraft.lobby2.event.PlayerManager;
import br.com.piracraft.lobby2.menu.Menu;
import br.com.piracraft.lobby2.menu.MenuFaction;
import br.com.piracraft.lobby2.menu.MenuHg;
import br.com.piracraft.lobby2.menu.MenuKitPvp;
import br.com.piracraft.lobby2.menu.MenuLobby;
import br.com.piracraft.lobby2.menu.MenuSevenDays;
import br.com.piracraft.lobby2.menu.MenuSkywars;
import br.com.piracraft.lobby2.menu.MenuSkywars2;
import br.com.piracraft.lobby2.utils.Cor;
import br.com.piracraft.lobby2.utils.LobbyAPI;
import br.com.piracraft.lobby2.utils.SevenDaysQuerys;
import br.com.piracraft.lobby2.utils.Shop;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class Main extends JavaPlugin {

	private static Plugin plugin;

	@Override
	public void onEnable() {
		setPlugin(this);
		getServer().getMessenger().registerOutgoingPluginChannel(this,
				"BungeeCord");
		PluginManager pm = getServer().getPluginManager();
		AntiAfkSystem afk = new AntiAfkSystem();
		afk.runTaskTimer(this, 20, 20);
		pm.registerEvents(afk, this);
		pm.registerEvents(new PlayerManager(), this);
		pm.registerEvents(new GameManager(), this);
		pm.registerEvents(new BlockManager(), this);
		pm.registerEvents(new MenuSevenDays(), this);
		pm.registerEvents(new Shop(), this);
		new MenuHg();
		new MenuLobby();
		new MenuKitPvp();
		new MenuSkywars();
		new MenuSkywars2();
		new MenuFaction();
		new MenuSevenDays();
		new BukkitRunnable() {

			@Override
			public void run() {

				Menu.getMenu("Hg").update();
				Menu.getMenu("Faction").update();
				Menu.getMenu("KitPvp").update();
			}
		}.runTaskTimer(this, 10, 5 * 60 * 20);
		new BukkitRunnable() {

			@Override
			public void run() {
				Menu.getMenu("Skywars2").update();
			}
		}.runTaskTimer(this, 10, 3 * 20);
		updateLobby();
		spawnNpcs();
		
		//AQUI É A CHECAGEM DE TODAS AS LIST DO 7 DIAS!
		SevenDaysQuerys.loadAll();
		//Não mexer até que eu diga.
	}
	private void updateLobby(){
		try {
			ResultSet s = MySQL.conectar().createStatement().executeQuery(
					"SELECT * FROM `MINIGAMES_SALAS_E_SERVIDORES` WHERE PORTA = '"
							+ Bukkit.getPort() + "'");
			
			if (s.next()) {
				LobbyAPI.setLobby(new Location(Bukkit.getWorld("world"),
						Double.parseDouble(s.getString("LOBBY").split(" ")[0]),
						Double.parseDouble(s.getString("LOBBY").split(" ")[1]),
						Double.parseDouble(s.getString("LOBBY").split(" ")[2])));
				
			}
			s.getStatement().getConnection().close();
		} catch (SQLException e) {
			LobbyAPI.enviarAlertaConsole("Mysql ERRO", e.getMessage() + ".",
					Cor.Vermelho);
		}
	}
	private void spawnNpcs(){
		try {
			ResultSet rs = MySQL.getQueryResult(
					"SELECT * FROM V_NPC_LOCALIZACAO where ID_NETWORK = '1' and ID_SALA = '2';");
			int id = 1;
			while (rs.next()) {
				String[] locs = rs.getString("COORDENADA").split(",");
				Location loc = new Location(Bukkit.getWorld("world"),
						Double.valueOf(locs[0]),
						Double.valueOf(locs[1]),
						Double.valueOf(locs[2]),
						Float.valueOf(locs[3]),
						Float.valueOf(locs[4]));
				createNPC(rs.getString("NOME"), rs.getString("SKIN"), loc);
				Bukkit.getConsoleSender().sendMessage("§aNPC Spawnados!");
				Bukkit.getConsoleSender().sendMessage("Sommos nozzes "+id);
				id++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void createNPC(String nick, String skin, Location loc) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		NPC npc = registry.createNPC(EntityType.PLAYER, nick);
		npc.setName(nick);
		npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, skin);
		npc.spawn(loc);
	}


	@Override
	public void onDisable() {
		CitizensAPI.getNPCRegistry().deregisterAll();
		LobbyAPI.enviarAlertaConsole("Aviso", "Removendo todas as entidades.",
				Cor.Verde);
		
		for (Entity e:Bukkit.getWorlds().get(0).getEntities()){
			if (e instanceof Player)continue;
			e.remove();
		}
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(Plugin plugin) {
		Main.plugin = plugin;
	}


}
