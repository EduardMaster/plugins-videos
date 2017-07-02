package zLuck.zEventos;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import br.com.piracraft.api.Main;
import zLuck.zComandos.S;
import zLuck.zMySQL.SQLStats;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Uteis;

public class Chat implements Listener{
	
	private Map<String, Long> antiflood = new HashMap<String, Long>();
    
	@EventHandler(priority = EventPriority.MONITOR)
	void AntiFlood(AsyncPlayerChatEvent e) {
	    Player p = e.getPlayer();
	    
	    if (antiflood.containsKey(p.getName()) && antiflood.get(p.getName()).longValue() > System.currentTimeMillis() && !Main.isStaff(p)) {
	        p.sendMessage("§c§lNão§f §7flode no chat global.");
	        e.setCancelled(true);
	        return;
	    }
	    antiflood.put(p.getName(), System.currentTimeMillis() + 3000);
	}
	@EventHandler
	void aoSpecFalar(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (Arrays.spec.contains(p) && !Main.isStaff(p)) {
			p.sendMessage(Uteis.prefix + " §7Spectador nao pode falar");
			e.setCancelled(true);
		}
		if (Arrays.cm) {
			if (!Main.isStaff(p)) {
				e.setCancelled(true);
				p.sendMessage("§c§oChat desativado para players");
			}
		}
	}
	@EventHandler
	void aoFalar(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		String rank = null;
		if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Bronze")) {
			rank = "§e§o" + SQLStats.getRank(p.getName());
      	} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Aluminiun")) {
			rank = "§8§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Prata")) {
			rank = "§0§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Ouro")) {
			rank = "§6§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Diamante")) {
			rank = "§b§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Cobriun")) {
			rank = "§9§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Esmerald")) {
			rank = "§a§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Mercuriun")) {
			rank = "§6§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Titaniun")) {
			rank = "§5§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Platina")) {
			rank = "§9§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Mestre")) {
			rank = "§c§o" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Centuriun")) {
			rank = "§c§o§l" + SQLStats.getRank(p.getName());
		} else if (SQLStats.getRank(p.getName()).equalsIgnoreCase("Desafiante")) {
			rank = "§4§o" + SQLStats.getRank(p.getName());
		} else {
			rank = "§7§oUNRANKED";
		}
		
		if (Main.isStaff(p)) {
			e.setFormat("§8[§c"+rank+"§8] §7" + p.getDisplayName() + " §8» §f" + e.getMessage().replace("&", "§").replace("%", ""));
		} else {
			e.setFormat("§8[§c"+rank+"§8] §7" + p.getDisplayName() + " §8» §f" + e.getMessage().toLowerCase().replace("%", ""));
		}
	}
	@EventHandler
	void TirarBugs(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
        if (e.getMessage().toLowerCase().contains("hacker") || e.getMessage().toLowerCase().contains("hack")) {
        	if (!Main.isStaff(p)) {
              	p.sendMessage("§7[§4✖§7] §cDigite /Report <jogador> <motivo>");
          	    e.setCancelled(true);
          }
	   }
	}
	@EventHandler
	void StaffChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (S.s.contains(p)) {
			e.setCancelled(true);
			for (Player all : Bukkit.getOnlinePlayers()) {
            	if (Main.isStaff(p)) {
	                all.sendMessage("§7[§4§lSTAFF§7] " + p.getDisplayName() + ": §f" + e.getMessage());
            	}
			}
		}
	}

}
