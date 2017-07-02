package zLuck.zMySQL;

import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zEventos.PlayerStatus;
import zLuck.zMain.zLuck;

public class SQLStats {

	public static boolean playerExisti(String nome) {		
		boolean i = true;
		
		if (zLuck.getPlugin().getConfig().get("Status."+nome) == null) {
			i = false;
		}	
		
		return i;
	}
	
	public static void createPlayer(String nome) {
		if (!(playerExisti(nome))) {
			zLuck.getPlugin().getConfig().set("Status."+nome, nome);
			zLuck.getPlugin().getConfig().set("Status."+nome+".Rank", "Bronze");
			zLuck.getPlugin().getConfig().set("Status."+nome+".Matou", 0);
			zLuck.getPlugin().saveConfig();
		}
	}
	public static String getRank(String nome) {
		String i = "";
		
		if (playerExisti(nome)) {			
			i = zLuck.getPlugin().getConfig().getString("Status." +nome+ ".Rank");
		} else {
			createPlayer(nome);
			getRank(nome);
		}		
		return i;		
	}
	public static Integer getCoins(Player p) {
		Integer i = 0;
		
		for(int x = 0; x < PlayerStatus.status.size(); x++){
			if(PlayerStatus.status.get(x).getUuid().equals(Main.uuid.get(p))) {
				i = PlayerStatus.status.get(x).getCoins();
			}
		}
		
		return i;		
	}
	public static Integer getMatou(String nome) {
		Integer i = 0;
		
		if (playerExisti(nome)) {			
			i = zLuck.getPlugin().getConfig().getInt("Status." +nome+ ".Matou");
		} else {
			createPlayer(nome);
			getMatou(nome);
		}		
		return i;		
	}
	public static void setRank(String NOME, String RANK) {
		if (playerExisti(NOME)) {
			zLuck.getPlugin().getConfig().set("Status."+NOME+".Rank", RANK);
			zLuck.getPlugin().saveConfig();
		} else {
			createPlayer(NOME);
			setRank(NOME, RANK);
		}
	}
	public static void setMatou(String nome, Integer matou) {
		if (playerExisti(nome)) {
			zLuck.getPlugin().getConfig().set("Status."+nome+".Matou", matou);
			zLuck.getPlugin().saveConfig();
		} else {
			createPlayer(nome);
			setMatou(nome, matou);
		}
	}

	public static void addMatou(String nome, Integer matou) {
		if (playerExisti(nome)) {
			setMatou(nome, Integer.valueOf(getMatou(nome).intValue() + matou.intValue()));
		} else {
			createPlayer(nome);
			addMatou(nome, matou);
		}
	}
	
	public static void removerMatou(String nome, Integer matou) {
		if (playerExisti(nome)) {
			setMatou(nome, Integer.valueOf(getMatou(nome).intValue() - matou.intValue()));
		} else {
			createPlayer(nome);
			removerMatou(nome, matou);
		}
	}
	
}
