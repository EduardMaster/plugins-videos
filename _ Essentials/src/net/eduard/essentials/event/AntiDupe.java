package net.eduard.essentials.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AntiDupe extends BukkitRunnable implements Listener {
	public static Map<Player, Integer> cliques = new HashMap<>();

//	@EventHandler
	public void bloquear(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (e.getEntity() instanceof Player) {
				double dis = p.getLocation().distance(e.getEntity().getLocation());
				if (dis > 5) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.hasPermission("antihack.admin")) {
							player.sendMessage("§aO jogador " + p.getName()
									+ " esta batendo com mais de 5 blocos de distancia. Distancia: " + dis);
						}
					}
				}
			}

		}
	}

	@Override
	public void run() {

		for (Player player : Bukkit.getOnlinePlayers()) {
			Integer clique = cliques.get(player);
			// player.sendMessage("§aTestando seus cliques: "+clique);
			if (clique != null) {
				if (clique > 30) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("antimacro.admin")) {
							p.sendMessage("§cO jogador §f<jogador> §cestá §e§lPROVAVELMENTE §cde macro. §f(<cliques>)"
									.replace("<jogador>", player.getName()).replace("<cliques>", "" + clique));
						}
					}

				} else if (clique > 15) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("antimacro.admin")) {
							p.sendMessage("§cO jogador §f<jogador> §cestá clicando muito. §f(<cliques>)".replace("<jogador>", player.getName()).replace("<cliques>", "" + clique));
						}
					}
				}
				cliques.remove(player);
			}

		}

	}

	@EventHandler
	public void bloquearRestones(BlockPlaceEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItemInHand() != null) {
			if (e.getItemInHand().getType() == Material.REDSTONE) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cSinto muito, você não pode usar Restone.");
			}
		}
	}

	@EventHandler
	public void bloquearRestoneTocha(BlockPlaceEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItemInHand() != null) {
			if (e.getItemInHand().getType() == Material.REDSTONE_TORCH_ON
					|| e.getItemInHand().getType() == Material.REDSTONE_TORCH_OFF
					|| e.getItemInHand().getType().name().startsWith("PISTON")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cSinto muito, você não pode usar Restone.");
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void pegarRestone(PlayerPickupItemEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getItem().getItemStack().getType() == Material.REDSTONE) {
			// new BukkitRunnable() {
			//
			// @Override
			// public void run() {
			// e.getPlayer().getInventory().remove(Material.REDSTONE);
			// }
			// }.runTaskAsynchronously(JavaPlugin.getPlugin(Main.class));
			// e.getItem().setPickupDelay(200);
			e.setCancelled(true);
			e.getItem().remove();
			// e.getItem().setItemStack(new ItemStack(Material.AIR));
			// e.getItem().remove();
			e.getPlayer().sendMessage("§cSinto muito, você não pode pegar Restone.");
		}
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_AIR) {

			if (cliques.containsKey(p)) {
				Integer clique = cliques.get(p);
				cliques.put(p, ++clique);
			} else {
				cliques.put(p, 1);
			}
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {

			Player p = (Player) e.getDamager();
			if (cliques.containsKey(p)) {
				Integer clique = cliques.get(p);
				cliques.put(p, ++clique);
			} else {
				cliques.put(p, 1);
			}
		}
	}

	@EventHandler
	public void bloquearBigorna(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ANVIL) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cSinto muito, você não pode abrir a bigorna.");
			}
		}
	}

	@EventHandler
	public void bloquearMesaDeEncantamento(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cSinto muito, você não pode abrir a mesa de encantamento.");
			}
		}
	}

	@EventHandler
	public void bloquearFunil(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("antidupe.admin"))
			return;
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.HOPPER) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cSinto muito, você não pode abrir o funil.");
			}
		}
	}

}
