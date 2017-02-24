package net.eduard.hg.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.gui.KitManager;
import net.eduard.api.util.SimpleEffect;

public class HG  {

	
	public HG() {
		new Game(1).timer(new SimpleEffect() {
			
			public void effect() {
				event();
			}
		});
		time = 60*2;
		kit = new KitManager();
		kit.reloadDefaults();
	}
	public static int time;
	public static List<Player> players = new ArrayList<>();
	public static List<Player> admins = new ArrayList<>();
	public static List<Player> specs = new ArrayList<>();
	public static HashMap<Player, ItemStack[]> items = new HashMap<>();
	public static KitManager kit;
	public static HGState state = HGState.STARTING;
	public static String noPerm = "§cSem permissão!";
	public static int minPlayers = 2;
	public static int maxPlayers = 2;
	public static int invunerabilityTime = 2 * 60;
	public static int soupValue = 7;
	public static int clearSize;
	public static boolean pvpEnabled;
	public static boolean kitsEnabled = true;
	public static boolean chatEnabled;
	public static boolean test = true;
	public static boolean buildEnabled = true;
	public static String specGuiTitle = "§8Teleportar para:";
	public static int kitAmount = 0;
	public static void send(Player p,String message) {
		p.sendMessage(getMessage(message));
	}
	
	public static void sendGlobal(String message) {
		
	}
	public static void sendPlayers(String message) {
		for (Player p:players) {
			send(p,message);
		}
	}
	public static void sendSpecs(String message) {
		for (Player p:specs) {
			send(p,message);
		}
	}
	public static void sendAdmins(String message) {
		for (Player p:admins) {
			send(p,message);
		}
	}
	private static String getMessage(String message) {
		return message;
	}
	public void event() {
		if (state == HGState.STARTING) {

			if (API.getPlayers().size() < HG.minPlayers) {
				return;
			}
			time--;
		
			if (time == 0) {
				HG.startHG();
			} else {
				boolean broad = false;
				if (time <= 10) {
					broad = true;
				} else if (time % 10 == 0) {
					broad = true;
				}
				if (broad) {
					sendGlobal("§6O torneio vai começar em $time");
				}

			}
		} else if (state == HGState.GAMING) {
			time++;
			if (time <= HG.invunerabilityTime) {
				int result = HG.invunerabilityTime - time;
				boolean broad = false;
				if (result == 0) {
					Bukkit.broadcastMessage("§6A invunerabilidade acabou!");
				} else if (result <= 10) {
					broad = true;
				} else if (result % 30 == 0) {
					broad = true;
				}
				if (broad) {
					sendGlobal("§6A invunerabilidade vai acabar em " + result + " segudos");
				}
			} else if (time == (5 * 60)) {
				sendGlobal("§6Já se passaram 5 minutos!");
			} else if (time % (10 * 60) == 0) {
				sendGlobal("§6Já se passaram 10 minutos!");
			}
		}
	}
	public static void startHG() {
		sendGlobal("§6O torneio começou, Boa Sorte a todos");
		sendPlayers("§6Voce esta invuneravel por 2 minutos se prepare!");
		sendGlobal("§6O torneio se encerra em 20 minutos Bom Jogo!");;
		state =HGState.GAMING;
		time = 60*20;
		for (Player p:players) {
			API.teleportToSpawn(p);
			kit.gainKit(p);
		}
	}
	public static void joinToAdminState(Player p) {
		items.put(p, p.getInventory().getContents());
		PlayerInventory inv = p.getInventory();
		API.clearInventory(p);
		API.hide(p);
		inv.setItem(0,
				API.add(API.newItem(Material.STICK, "§6Testar KnockBack"), Enchantment.KNOCKBACK, 10));
		inv.setItem(4, API.newItem(Material.MAGMA_CREAM, "§6Troca rapida"));
		inv.setItem(3, API.newItem(Material.BEDROCK, "§6Prender"));
		inv.setItem(5, API.newItem(Material.PAPER, "§6Trolar"));
		inv.setItem(8, API.newItem(Material.BOWL, "§6Testar AutoSoup"));
		admins.add(p);
		p.sendMessage("§6Voce entrou no modo Admin!");
	}
	public static void removeFromAdminState(Player p) {
		p.getInventory().setContents(items.get(p));
		admins.remove(p);
		send(p, "§6Voce saiu do modo Admin!");
		API.show(p);
		if (specs.contains(p)) {
			toSpectate(p);
		}
	}
	public static void toSpectate(Player p) {
		for (Player target:players) {
			target.hidePlayer(p);
		}
		
		
	}

}
