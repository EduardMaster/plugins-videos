package zLuck.zEventos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.PiraCraftAPI;
import br.com.piracraft.api.games.util.GamesInserts;
import zLuck.zAPIs.Status;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Estado;

public class PlayerStatus implements Listener {
	
	public static List<Status> status = new ArrayList<Status>();

	@EventHandler
	public void Death(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer();
		
		if (zLuck.estado != Estado.Jogo) return;
		
		if (p.getKiller() != null && p.getKiller() instanceof Player) {
			Player k = p.getKiller();
			
			addStatus(k, p);
		} else {
			addStatus(null, p);
		}
	}

	@EventHandler
	public void logou(PlayerJoinEvent e) {
		Status s = new Status();
		
		s.setCash(new PiraCraftAPI(e.getPlayer()).getCash());
		s.setCoins(0);
		s.setDeaths(0);
		s.setKills(0);
		s.setStreak(0);
		s.setXp(0);
		s.setVencedor(0);
		s.setNetworkAtual(Integer.parseInt(new PiraCraftAPI(e.getPlayer()).getNetworkAtualUsuario()[14]));
		s.setUuid(new PiraCraftAPI(e.getPlayer()).getUUID());
		
		status.add(s);
	}

	@EventHandler
	public void deslogou(PlayerQuitEvent e) {

		for(int x = 0; x < status.size(); x++){
			if(status.get(x).getUuid().equals(Main.uuid.get(e.getPlayer()))) {
				new GamesInserts().insertMiniGamesXP(
						Integer.parseInt(PiraCraftAPI.getIdNomeSala(Bukkit.getPort())[2]), 
						Integer.parseInt(PiraCraftAPI.getIdNomeSala(Bukkit.getPort())[0]), 
						status.get(x).getUuid(), 
						status.get(x).getXp(), 
						status.get(x).getKills(), 
						status.get(x).getDeaths(), 
						status.get(x).getCoins(), 
						status.get(x).getNetworkAtual(), 
						status.get(x).getVencedor());
				
				status.remove(status.get(x));
			}
		}
	}
	
	public static void addStatus(Player k, Player d){
		if(k != null){
			for(int x = 0; x < status.size(); x++){
				if(status.get(x).getUuid().equals(Main.uuid.get(k))){
					status.get(x).setKills(status.get(x).getKills()+1);
					status.get(x).setStreak(status.get(x).getStreak()+1);
					
					if(Main.isVip.get(k)){
						status.get(x).setCoins(status.get(x).getCoins()+20);
						status.get(x).setXp(status.get(x).getXp()+20);
					}else{
						status.get(x).setCoins(status.get(x).getCoins()+10);
						status.get(x).setXp(status.get(x).getXp()+10);
					}
				}
			}
		}
		
		for(int x = 0; x < status.size(); x++){
			if(status.get(x).getUuid().equals(Main.uuid.get(d))){
				status.get(x).setDeaths(status.get(x).getDeaths()+1);
				status.get(x).setStreak(0);
				
				status.get(x).setCoins(status.get(x).getCoins()-5);
				status.get(x).setXp(status.get(x).getXp()-5);
			}
		}
	}

}
