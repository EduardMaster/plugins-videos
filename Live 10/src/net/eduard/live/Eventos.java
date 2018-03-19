package net.eduard.live;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Eventos implements Listener {

	public static ArrayList<Player> presidiarios = new ArrayList<>();

	@EventHandler
	public void clicar(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player alvo = (Player) e.getRightClicked();

			Player p = e.getPlayer();

			if (p.getItemInHand() == null)
				return;
			ItemStack prender = new ItemStack(Material.BEDROCK);
			ItemMeta meta = prender.getItemMeta();
			meta.setDisplayName("§cPrender");
			prender.setItemMeta(meta);
			if (prender.equals(p.getItemInHand())) {
				if (presidiarios.contains(alvo)) {
					alvo.sendMessage("§aVoce foi solto pelo staff " + p.getName());
					presidiarios.remove(alvo);

					Location loc = alvo.getLocation();
					loc.clone().add(0, -1, 0).getBlock().setType(Material.AIR);
					;
					loc.clone().add(1, 0, 0).getBlock().setType(Material.AIR);
					;
					loc.clone().add(0, 0, 1).getBlock().setType(Material.AIR);
					;
					// loc.clone().add(1, 0, 1).getBlock().setType(Material.AIR);
					;
					loc.clone().add(0, 0, -1).getBlock().setType(Material.AIR);
					;
					loc.clone().add(-1, 0, 0).getBlock().setType(Material.AIR);
					;
					// loc.clone().add(-1, 0, -1).getBlock().setType(Material.AIR);
					;
					// loc.clone().add(-1, 0, 1).getBlock().setType(Material.AIR);
					;
					// loc.clone().add(1, 0, -1).getBlock().setType(Material.AIR);
					;
					loc.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
					;

				} else {
					presidiarios.add(alvo);
					alvo.teleport(alvo.getLocation().add(-0.5, 0.5, -0.5));
					alvo.sendMessage("§cSe deslogar o pal come assinado " + p.getName());
					Location loc = alvo.getLocation();
					loc.clone().add(0, -1, 0).getBlock().setType(Material.BEDROCK);
					;
					loc.clone().add(1, 0, 0).getBlock().setType(Material.BEDROCK);
					;
					loc.clone().add(0, 0, 1).getBlock().setType(Material.BEDROCK);
					;
					// loc.clone().add(1, 0, 1).getBlock().setType(Material.BEDROCK);
					;
					loc.clone().add(0, 0, -1).getBlock().setType(Material.BEDROCK);
					;
					loc.clone().add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
					;
					// loc.clone().add(-1, 0, -1).getBlock().setType(Material.BEDROCK);
					;
					// loc.clone().add(-1, 0, 1).getBlock().setType(Material.BEDROCK);
					;
					// loc.clone().add(1, 0, -1).getBlock().setType(Material.BEDROCK);
					;
					loc.clone().add(0, 2, 0).getBlock().setType(Material.BEDROCK);
					;
				}
			}

		}
	}

	@EventHandler
	public void morrer(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		e.setRespawnLocation(p.getLocation());
		p.setGameMode(GameMode.ADVENTURE);
		p.setAllowFlight(true);
		p.setFlying(true);
		p.getInventory().clear();
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		for (Player jogador : Bukkit.getOnlinePlayers()) {
			if (jogador.equals(p))
				continue;
			jogador.hidePlayer(p);
		}
		ItemStack bussola = new ItemStack(Material.COMPASS);
		ItemMeta meta = bussola.getItemMeta();
		meta.setDisplayName("§aJogadores");
		bussola.setItemMeta(meta);
		p.getInventory().setItem(0, bussola);
		p.getInventory().setArmorContents(null);
		p.sendMessage("§cVoce entrou no modo espectador");

		new BukkitRunnable() {

			@Override
			public void run() {
				p.setGameMode(GameMode.SURVIVAL);
				p.teleport(p.getWorld().getSpawnLocation().setDirection(p.getLocation().getDirection()));
				p.setAllowFlight(false);
				p.setFlying(false);
				for (Player jogador : Bukkit.getOnlinePlayers()) {
					if (jogador.equals(p))
						continue;
					jogador.showPlayer(p);
				}
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.sendMessage("§aVoce saiu do modo espectador");

			}
		}.runTaskLater(Main.getPlugin(Main.class), 20 * 10);

	}
}
