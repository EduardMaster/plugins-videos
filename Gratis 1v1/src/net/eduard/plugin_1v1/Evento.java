package net.eduard.plugin_1v1;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Evento implements Listener {

	@EventHandler
	public void event(PlayerInteractEntityEvent e) {
		Player jogador = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player alvo = (Player) e.getRightClicked();
			ItemStack item = jogador.getItemInHand();
			Material type = item.getType();
			if (type == Material.BLAZE_ROD) {
				Main.getPlugin().convidarJogador(jogador, alvo);
				Main.getPlugin().aceitarPvP(alvo, jogador);
			}
		}

	}
	@EventHandler
	public void event(PlayerDeathEvent e) {
		Player jogador = (Player) e.getEntity();
		if (Main.getPlugin().emPvP.containsKey(jogador)) {
			Player vencedor = Main.getPlugin().emPvP.get(jogador);
			Main.getPlugin().vencerPvP(vencedor, jogador);
		}

	}
	@EventHandler
	public void event(PlayerQuitEvent e) {
		Player jogador = e.getPlayer();
		if (Main.getPlugin().emPvP.containsKey(jogador)) {
			Player vencedor = Main.getPlugin().emPvP.get(jogador);
			Main.getPlugin().vencerPvP(vencedor, jogador);
		}
	}

}
