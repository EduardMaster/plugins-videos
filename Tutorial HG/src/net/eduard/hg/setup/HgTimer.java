package net.eduard.hg.setup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.hg.HgPlugin;

public class HgTimer extends BukkitRunnable {

	@Override
	public void run() {
		for (HgRoom room : HgPlugin.setup.getRooms().values()) {
			HgState state = room.getState();
			
			if (state == HgState.PRE_GAME) {
				room.setTime(room.getTime()-1);
				if (room.getPlayers().size() < HgPlugin.setup
						.getMinPlayersAmount()) {
					Bukkit.broadcastMessage(ChatColor.GREEN
							+ "A contagem esta reiniciando pois não há jogadores suficientes");
					room.setTime(HgPlugin.setup.getTimeToStart());
				}
				if (room.getTime()==0){
					Bukkit.broadcastMessage(ChatColor.GOLD
							+ "O torneio começou! Boa sorte");
					room.setState(HgState.NO_PVP);
					room.setTime(HgPlugin.setup.getTimeInvunerable());
				}else if (room.getTime()%30==0|room.getTime()<=10){
					Bukkit.broadcastMessage(ChatColor.GOLD
							+ "O torneio vai começar em "+room.getTime()+" segundos!");
				}
				
			} else if (state == HgState.NO_PVP) {
				room.setTime(room.getTime()-1);
				Bukkit.broadcastMessage(ChatColor.GOLD
						+ "Você esta invuneravel por 2 minutos se prepare!");
				
				if (room.getTime()==0){
					Bukkit.broadcastMessage(ChatColor.GOLD
							+ "A invunerabilidade Acabou! Que começe a Batalha!");
					room.setState(HgState.GAME);
				}else if (room.getTime()%30==0|room.getTime()<=10){
					Bukkit.broadcastMessage(ChatColor.GOLD
							+ "O invunerabilidade vai acabar em "+room.getTime()+" segundos!");
				}
			}
			else if (state == HgState.GAME) {
				room.setTime(room.getTime()+1);
				if (room.getTime()==HgPlugin.setup.getTimeToGameOver()){
					Bukkit.broadcastMessage(ChatColor.GOLD
							+ "Ninguem venceu a Batalha");
					room.setState(HgState.END_GAME);
				}else if (room.getPlayers().size()==1){
					// vencer jogador
				}
				
				else if (room.getTime() == 15*60){
					// spawnar feast
				}
				if (room.getTime()%5*60==0){
					// spawnr mini feasts
				}
				
			} else {
				room.setTime(room.getTime()-1);
				if (room.getTime()%5==0||room.getTime()<=10){
					Bukkit.broadcastMessage(ChatColor.GOLD
							+ "O torneio vai reinicar em "+room.getTime()+ " segundos!");
					
				}
				if (room.getTime()==0){
					room.setState(HgState.PRE_GAME);
					// acabar
				}
			}

		}
	}

}
