package zLuck.zUteis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

import zLuck.zMain.zLuck;
import zLuck.zTempos.Jogo;
import zLuck.zTempos.Proteção;

public class Partida {
	
	@SuppressWarnings("deprecation")
	public static void IniciarProteção() { 
		for (Player all : Bukkit.getOnlinePlayers()) {
			Centro.sendCenteredMessage(all, "§7Voce recebeu a §cprotecao");
			Centro.sendCenteredMessage(all, " §7Sao §c" + Arrays.jogador.size() + " §7jogadores que irao batalhar!");
		} 
		Bukkit.getScheduler().cancelTask(Arrays.tempo1);		
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (Arrays.treinopvp.contains(all)) {
				Arrays.treinopvp.remove(all);
				all.chat("/spawn");
			}
			if (all.getAllowFlight()) {
				all.setAllowFlight(false);
				all.setFlying(false);
			}
			all.closeInventory();
	    	all.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
			all.getInventory().clear();
			all.getInventory().setArmorContents(null);
			all.getInventory().addItem(new ItemStack(Material.COMPASS));
		}
        for (Block blocks : Schematic.blocoscoliseu) {
            if (blocks.getType() == Material.getMaterial(33)) {
        		blocks.setType(Material.AIR);
        	}
        }
		
		zLuck.estado = Estado.Proteção;
		new Proteção();
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			Uteis.pegarKit(all);
		}
	}
	public static void IniciarJogo() {
		for (Player all : Bukkit.getOnlinePlayers()) {
	    	all.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
		
		Bukkit.broadcastMessage("§7[§4§l☯§7] Voce perdeu a protecao!");
		Bukkit.getScheduler().cancelTask(Arrays.tempo2);
		
		zLuck.estado = Estado.Jogo;
		new Jogo();
	}

}
