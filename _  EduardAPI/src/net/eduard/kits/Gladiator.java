package net.eduard.kits;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class Gladiator extends Kit {
	public Gladiator() {
		setIcon(Material.IRON_FENCE, "Chame seus inimigos para um Duelo 1v1");
		add(Material.IRON_FENCE);
		setPrice(60 * 1000);
	}

	public int high = 100;
	public int size = 10;
	public int invunerableSeconds = 5;
	public static Material type = Material.GLASS;
	public static HashMap<Player, Location> locations = new HashMap<>();
	public static HashMap<Player, Player> targets = new HashMap<>();
	public static HashMap<Player, ArrayList<Location>> arenas = new HashMap<>();

	@EventHandler
	public void event(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (getPlayers().contains(p)) {
			if (API.isUsing(p, Material.IRON_FENCE)) {
				if (e.getRightClicked() instanceof Player) {
					Player target = (Player) e.getRightClicked();
					if (arenas.containsKey(target) | arenas.containsKey(p)) {
						p.sendMessage("§6Voce ja esta em Batalha!");
						return;
					}
					Location loc = p.getLocation().add(0, high, 0);
					ArrayList<Location> arena = getArena(loc.clone());
					locations.put(p, p.getLocation());
					locations.put(target, target.getLocation());
					targets.put(p, target);
					targets.put(target, p);
					arenas.put(p, arena);
					arenas.put(target, arena);
					p.setNoDamageTicks(20 * invunerableSeconds);
					target.setNoDamageTicks(20 * invunerableSeconds);

					p.teleport(loc.clone().add(size - 1, 1, 1 - size).setDirection(p.getLocation().getDirection()));
					target.teleport(
							loc.clone().add(1 - size, 1, size - 1).setDirection(target.getLocation().getDirection()));
					p.sendMessage("§6Voce esta invuneravel por 5 segundos!");
					target.sendMessage("§6Voce esta invuneravel por 5 segundos!");

				}
				e.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void event(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (arenas.containsKey(p)) {
			win(targets.get(p), p);
		}
	}

	@EventHandler
	public void event(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (arenas.containsKey(p)) {
			win(targets.get(p), p);
		}
	}

	public void win(Player winner, Player loser) {
		winner.sendMessage("§6Voce venceu a batalha!");

		ArrayList<Location> arena = arenas.get(winner);
		for (Location location : arena) {
			location.getBlock().setType(Material.AIR);
		}
		loser.sendMessage("§6Voce perdeu a batalha!");
		winner.teleport(locations.get(winner).setDirection(winner.getLocation().getDirection()));
		loser.teleport(locations.get(loser).setDirection(loser.getLocation().getDirection()));
		targets.remove(winner);
		targets.remove(loser);
		arenas.remove(winner);
		arenas.remove(loser);
		locations.remove(winner);
		locations.remove(loser);
	}

	@EventHandler
	public void event(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (arenas.containsKey(p)) {
			if (e.getBlock().getType() == type) {
				e.setCancelled(true);
			}
		}

	}

	public ArrayList<Location> getArena(Location location) {
		ArrayList<Location> list = new ArrayList<>();
		int xMin = location.getBlockX() - size;
		int xMax = location.getBlockX() + size;
		int zMin = location.getBlockZ() - size;
		int zMax = location.getBlockZ() + size;
		int yMin = location.getBlockY() - 1;
		int yMax = location.getBlockY() + size;
		for (int x = xMin; x <= xMax; x++) {
			for (int y = yMin; y <= yMax; y++) {
				for (int z = zMin; z <= zMax; z++) {
					Location loc = new Location(location.getWorld(), x, y, z);
					boolean set = false;
					if (y == yMax | y == yMin) {
						set = true;
					} else if (x == xMax | x == xMin | z == zMin | z == zMax) {
						set = true;
					}
					if (set) {
						loc.getBlock().setType(type);
					}
					list.add(loc);
				}
			}
		}
		return list;
	}
}
