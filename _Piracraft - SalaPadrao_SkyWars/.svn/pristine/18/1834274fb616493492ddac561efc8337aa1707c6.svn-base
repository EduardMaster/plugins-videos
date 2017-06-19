package me.leo.skywars;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.PiraCraftAPI;
import me.leo.skywars.Main.status;

public class Eventos implements Listener {

	public static HashMap<Player, Integer> numero = new HashMap<Player, Integer>();

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (Main.spec.contains(e.getPlayer().getName())) {
			e.setCancelled(true);

			for (String n : Main.spec) {
				Player p = Bukkit.getPlayer(n);

				p.sendMessage("§2[SPEC] §7" + e.getPlayer().getDisplayName() + " §c§l» §7" + e.getMessage());
			}
		} else {
			e.setCancelled(true);

			for (String n : Main.jogadores) {
				Player p = Bukkit.getPlayer(n);

				p.sendMessage("§7" + e.getPlayer().getDisplayName() + " §c§l» §7" + e.getMessage());
			}
		}
	}

	public HashMap<Player, Player> ultimo = new HashMap<Player, Player>();
	public Map<String, String> re = new HashMap<String, String>();

	@EventHandler
	void morrer(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		Player matou = e.getEntity().getKiller();
		Player morreu = e.getEntity().getPlayer();
		new BukkitRunnable() {
			@Override
			public void run() {
				if (e.getEntity() instanceof Player) {
					e.getEntity().spigot().respawn();
					
					if (matou != null) {
						if(!Jogador.gambi.contains(matou.getName())){
							Jogador.gambi.add(matou.getName());
							Jogador.setarDBKill(matou, morreu);
							Bukkit.broadcastMessage(
									"§cSkyWars §e» §aO jogador §2" + morreu.getName() + " §amorreu e perdeu 10 coins!");
							new BukkitRunnable() {
								public void run() {
									Jogador.gambi.remove(matou.getName());
									br.com.piracraft.api.Main.getMysql()
									.execute("INSERT INTO MINIGAMES_XP (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`XP`,`COINS`,`DATAHORA`) VALUES ('"
											+ PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "'," + "'" + br.com.piracraft.api.Main.uuid.get(morreu)
											+ "','-10','-10','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "')");
								}
							}.runTaskLater(Main.getInstance(), 20);
						}
					} else {
						if(!Jogador.gambi.contains(morreu.getName())){
							Jogador.gambi.add(morreu.getName());
							Jogador.morrer(morreu);
							new BukkitRunnable() {
								public void run() {
									Jogador.gambi.remove(morreu.getName());
								}
							}.runTaskLater(Main.getInstance(), 20);
						}
					}
				}
			}
		}.runTaskLater(Main.getInstance(), 5);
	}

	@EventHandler
	public void damage(CreatureSpawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	void serverPingEvent(ServerListPingEvent e) {
		if (Main.atual == status.PreJogo) {
			e.setMotd("prejogo");
		}
		if (Main.atual == status.Invencibilidade) {
			e.setMotd("inv");
		}
		if (Main.atual == status.Jogo) {
			e.setMotd("jogo");
		}
	}

	@EventHandler
	void sair(PlayerDeathEvent e) {
		if (Main.jogadores.contains(e.getEntity().getPlayer().getName())) {
			Main.jogadores.remove(e.getEntity().getPlayer().getName());
		}
	}

	@EventHandler
	void respawn(PlayerRespawnEvent e) {
		Jogador.espectador(e.getPlayer());
		new BukkitRunnable() {

			@Override
			public void run() {
				e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 2, 94, 1));

			}
		}.runTaskLater(Main.getInstance(), 40);
	}

	@EventHandler
	void mover(PlayerMoveEvent e) {
		if (e.getPlayer().getLocation().getBlockY() <= -15) {
			if (Main.atual == status.PreJogo) {
				e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 149, 41, -162));
			} else {
				if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
					e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), e.getPlayer().getLocation().getX(),
							e.getPlayer().getLocation().getY() + 180, e.getPlayer().getLocation().getZ()));
					e.getPlayer().setFlying(true);
				} else {
					e.getPlayer().setHealth(0D);
				}

			}
		}
	}

	@EventHandler
	void sair(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		if (Main.jogadores.contains(e.getPlayer().getName())) {
			Main.jogadores.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	void kick(PlayerKickEvent e) {
		if (Main.jogadores.contains(e.getPlayer().getName())) {
			Main.jogadores.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	void login(PlayerLoginEvent e) {
		if (Main.atual == status.PreJogo) {
			if (Main.getInstance().getServer().getOnlinePlayers().size() == Main.getInstance().getServer()
					.getMaxPlayers()) {
				if (br.com.piracraft.api.Main.isVip.get(e.getPlayer())) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (!br.com.piracraft.api.Main.isVip.get(e.getPlayer())) {
							p.kickPlayer("§cVoce foi kicado para um vip entrar!");
							e.allow();
							return;
						}
						e.disallow(Result.KICK_FULL, "§cSala Cheia de VIPS, tente outra sala!");
					}
				} else {
					e.disallow(Result.KICK_FULL, "§cSala Cheio, Compre Vip para poder entrar em partida lotada!");
				}
			} else {
				e.allow();
				Main.jogadores.add(e.getPlayer().getName());
			}
		} else {
			e.disallow(Result.KICK_FULL, "§cPartida Ja teve inicio!");
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	void morrer2(PlayerQuitEvent e) {
		numero.remove(e.getPlayer());
	}

	@EventHandler
	void blockBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			if (Tempos.TempoInv >= 30) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	void inv(InventoryClickEvent e) {
		if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			if (Tempos.TempoInv >= 30) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	void drop(PlayerDropItemEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			if (Tempos.TempoInv >= 30) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	void craft(CraftItemEvent e) {
		if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			if (Tempos.TempoInv >= 30) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	void itemPickup(PlayerPickupItemEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			if (Tempos.TempoInv >= 30) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	void blockPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
		}
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			if (Tempos.TempoInv >= 30) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	void dano(EntityDamageEvent e) {
		if (Main.atual == status.PreJogo) {
			e.setCancelled(true);
		}
		if (Main.atual == status.Invencibilidade) {
			e.setCancelled(true);
		}
	}

	public static Map<Player, Location> jatem = new HashMap<Player, Location>();

	@EventHandler
	void join(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();

		numero.put(p, numero.size() + 1);

		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		Bukkit.broadcastMessage("§eSkyWars §c» §bO jogador " + p.getName() + " se juntou a partida §2[§a"
				+ Main.jogadores.size() + "§2/§a" + Bukkit.getMaxPlayers() + "§2]§b!");
		//p.getInventory().setItem(4, ItemAPI.Criar(Material.CHEST, 1, 0, "§aKits", false, "§7Lista de kits"));
		p.setGameMode(GameMode.SURVIVAL);
		p.setExp(0);
		p.setLevel(0);

		p.teleport(new Location(p.getWorld(), Main.posicionamentoJaulas.get(4).getLobbyX(),
				Main.posicionamentoJaulas.get(4).getLobbyY(), Main.posicionamentoJaulas.get(4).getLobbyZ()));
	}

}
