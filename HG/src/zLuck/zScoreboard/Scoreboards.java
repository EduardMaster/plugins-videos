package zLuck.zScoreboard;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zMySQL.SQLStats;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Uteis;

public class Scoreboards {
	
	public static ArrayList<Player> score = new ArrayList<Player>();
	
    @SuppressWarnings("deprecation")
	public static void setarInicio(Player p) {
        if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("a", "b");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§e§lHARDCOREGAMES");
        	obj.getScore("     ").setScore(12);
        	obj.getScore("  §fRank » §a").setScore(11);
        	obj.getScore("  §fCoins » §a").setScore(10);
        	obj.getScore("    ").setScore(9);
        	obj.getScore("  §fIniciando » ").setScore(8);
        	obj.getScore("  §fJogadores » ").setScore(7);
        	obj.getScore("   ").setScore(6);
        	obj.getScore("  §fKit » §a").setScore(5);
        	obj.getScore("  §fIP » §e" + zLuck.mensagens.getString("IP")).setScore(4);
        	obj.getScore("  ").setScore(3);
        	obj.getScore("  §f/score").setScore(2);
            p.setScoreboard(board);
        }
        
        if(p.getScoreboard().getTeam("rank") == null){
            p.getScoreboard().registerNewTeam("rank").addPlayer(Bukkit.getOfflinePlayer("  §fRank » §a"));
        }
        if(p.getScoreboard().getTeam("coins") == null){
            p.getScoreboard().registerNewTeam("coins").addPlayer(Bukkit.getOfflinePlayer("  §fCoins » §a"));
        }
        if(p.getScoreboard().getTeam("tempo") == null){
            p.getScoreboard().registerNewTeam("tempo").addPlayer(Bukkit.getOfflinePlayer("  §fIniciando » "));
        }
        if(p.getScoreboard().getTeam("jogadores") == null){
            p.getScoreboard().registerNewTeam("jogadores").addPlayer(Bukkit.getOfflinePlayer("  §fJogadores » "));
        }
        if(p.getScoreboard().getTeam("kit") == null){
            p.getScoreboard().registerNewTeam("kit").addPlayer(Bukkit.getOfflinePlayer("  §fKit » §a"));
        }
        
        p.getScoreboard().getTeam("rank").setSuffix(SQLStats.getRank(p.getName()));
        p.getScoreboard().getTeam("coins").setSuffix(""+SQLStats.getCoins(p));
        p.getScoreboard().getTeam("tempo").setSuffix("§a"+Uteis.numeroComPontos(Arrays.tempoA1));
        p.getScoreboard().getTeam("jogadores").setSuffix("§a"+Arrays.jogador.size());
        p.getScoreboard().getTeam("kit").setSuffix(KitAPI.kit.get(p));
    }
    @SuppressWarnings("deprecation")
	public static void setarProteção(Player p) {
        if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null){
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("a", "b");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§e§lHARDCOREGAMES");
        	obj.getScore("     ").setScore(12);
        	obj.getScore("  §fRank » §a").setScore(11);
        	obj.getScore("  §fCoins » §a").setScore(10);
        	obj.getScore("    ").setScore(9);
        	obj.getScore("  §fProteção » ").setScore(8);
        	obj.getScore("  §fJogadores » ").setScore(7);
        	obj.getScore("   ").setScore(6);
        	obj.getScore("  §fKit » §a").setScore(5);
        	obj.getScore("  §fIP » §e" + zLuck.mensagens.getString("IP")).setScore(4);
        	obj.getScore("  ").setScore(3);
        	obj.getScore("  §f/score").setScore(2);
        	obj.getScore(" ").setScore(1);
            p.setScoreboard(board);
        }
        
        if(p.getScoreboard().getTeam("rank") == null){
            p.getScoreboard().registerNewTeam("rank").addPlayer(Bukkit.getOfflinePlayer("  §fRank » §a"));
        }
        if(p.getScoreboard().getTeam("coins") == null){
            p.getScoreboard().registerNewTeam("coins").addPlayer(Bukkit.getOfflinePlayer("  §fCoins » §a"));
        }
        if(p.getScoreboard().getTeam("tempo") == null){
            p.getScoreboard().registerNewTeam("tempo").addPlayer(Bukkit.getOfflinePlayer("  §fProteção » "));
        }
        if(p.getScoreboard().getTeam("jogadores") == null){
            p.getScoreboard().registerNewTeam("jogadores").addPlayer(Bukkit.getOfflinePlayer("  §fJogadores » "));
        }
        if(p.getScoreboard().getTeam("kit") == null){
            p.getScoreboard().registerNewTeam("kit").addPlayer(Bukkit.getOfflinePlayer("  §fKit » §a"));
        }
        
        p.getScoreboard().getTeam("rank").setSuffix(SQLStats.getRank(p.getName()));
        p.getScoreboard().getTeam("coins").setSuffix(""+SQLStats.getCoins(p));
        p.getScoreboard().getTeam("tempo").setSuffix("§a"+Uteis.numeroComPontos(Arrays.tempoA2));
        p.getScoreboard().getTeam("jogadores").setSuffix("§a"+Arrays.jogador.size());
        p.getScoreboard().getTeam("kit").setSuffix(KitAPI.kit.get(p));
    }
    @SuppressWarnings("deprecation")
	public static void setarJogo(Player p) {
        if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("a", "b");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§e§lHARDCOREGAMES");
        	obj.getScore("     ").setScore(12);
        	obj.getScore("  §fRank » §a").setScore(11);
        	obj.getScore("  §fCoins » §a").setScore(10);
        	obj.getScore("    ").setScore(9);
        	obj.getScore("  §fTempo » §a").setScore(8);
        	obj.getScore("  §fJogadores » ").setScore(7);
        	obj.getScore("   ").setScore(6);
        	obj.getScore("  §fKit » §a").setScore(5);
        	obj.getScore("  §fKills » §a").setScore(4);
        	obj.getScore("  ").setScore(3);
            obj.getScore("  §f/score").setScore(2);
        	obj.getScore(" ").setScore(1);
            p.setScoreboard(board);
        }
        
        if(p.getScoreboard().getTeam("rank") == null){
            p.getScoreboard().registerNewTeam("rank").addPlayer(Bukkit.getOfflinePlayer("  §fRank » §a"));
        }
        if(p.getScoreboard().getTeam("coins") == null){
            p.getScoreboard().registerNewTeam("coins").addPlayer(Bukkit.getOfflinePlayer("  §fCoins » §a"));
        }
        if(p.getScoreboard().getTeam("tempo") == null){
            p.getScoreboard().registerNewTeam("tempo").addPlayer(Bukkit.getOfflinePlayer("  §fTempo » §a"));
        }
        if(p.getScoreboard().getTeam("jogadores") == null){
            p.getScoreboard().registerNewTeam("jogadores").addPlayer(Bukkit.getOfflinePlayer("  §fJogadores » "));
        }
        if(p.getScoreboard().getTeam("kit") == null){
            p.getScoreboard().registerNewTeam("kit").addPlayer(Bukkit.getOfflinePlayer("  §fKit » §a"));
        }
        if(p.getScoreboard().getTeam("kills") == null){
            p.getScoreboard().registerNewTeam("kills").addPlayer(Bukkit.getOfflinePlayer("  §fKills » §a"));
        }
        
        p.getScoreboard().getTeam("rank").setSuffix(SQLStats.getRank(p.getName()));
        p.getScoreboard().getTeam("coins").setSuffix(""+SQLStats.getCoins(p));
        p.getScoreboard().getTeam("tempo").setSuffix(Uteis.numeroComPontos(Arrays.tempoA3));
        p.getScoreboard().getTeam("jogadores").setSuffix("§a"+Arrays.jogador.size());
        p.getScoreboard().getTeam("kit").setSuffix(KitAPI.kit.get(p));
        p.getScoreboard().getTeam("kills").setSuffix(""+Arrays.kills.get(p.getName()));
    }
    
}
