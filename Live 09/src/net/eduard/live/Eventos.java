package net.eduard.live;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Eventos implements Listener {

	@EventHandler
	public void evento(EntitySpawnEvent e) {
		if (e.getEntityType() == EntityType.CREEPER) {
			e.setCancelled(true);
		} else if (e.getEntityType() == EntityType.COW) {
		}
	}

	public static boolean verificarItens(Player jogador) {
		int precisa = 20;
		int contagem = 0;
		for (ItemStack item : jogador.getInventory().getContents()) {
			if (item != null) {
				if (item.getType() == Material.REDSTONE) {
					contagem = contagem + item.getAmount();
				}
			}
		}

		return contagem >= precisa;
	}

	public static boolean verificarItens(Player jogador, int precisa, Material mat) {
		int contagem = 0;
		for (ItemStack item : jogador.getInventory().getContents()) {
			if (item != null) {
				if (item.getType() == mat) {
					contagem = contagem + item.getAmount();
				}
			}
		}

		return contagem >= precisa;
	}

	public static boolean verificarItens(Player jogador, int precisa, Material mat, int data) {
		int contagem = 0;
		for (ItemStack item : jogador.getInventory().getContents()) {
			if (item != null) {
				if (item.getType() == mat && item.getDurability() == data) {
					contagem = contagem + item.getAmount();
				}
			}
		}

		return contagem >= precisa;
	}

	public static void removerItens(Player jogador, int quantosPararemover, Material mat, int data) {
		int contagemDeRemovidos = 0;
		int index = 0;
		for (ItemStack item : jogador.getInventory().getContents()) {
			if (contagemDeRemovidos >= quantosPararemover) {
				break;
			}
			if (item != null) {
				if (item.getType() == mat && item.getDurability() == data) {
					contagemDeRemovidos = contagemDeRemovidos + item.getAmount();
					jogador.getInventory().setItem(index, null);
				}
			}
			index++;
		}

		HashMap<String, Integer> contas = new HashMap<>();
		contas.put("Edu", 1000);

		Integer dinheiroDoEdu = contas.get("Edu");
		contas.put("Edu", 1000);

		Integer dinheiroAtual = contas.get("Edu");
		contas.put("Edu", dinheiroAtual - 300);

		Integer dinheiroAtual2 = contas.get("Edu");
		contas.put("Edu", dinheiroAtual + 400);

	}

	// @EventHandler
	@SuppressWarnings({ "unused", "unused" })
	// public void evento1(BlockChangeEvent e) {
	// Bukkit.broadcastMessage("1");
	//
	// }
//	@EventHandler
	public void evento1(EntityChangeBlockEvent e) {
		Bukkit.broadcastMessage("teste");
		if (e.getEntity() instanceof FallingBlock) {
			FallingBlock fallingBlock = (FallingBlock) e.getEntity();
			if (fallingBlock.getBlockId() == Material.CHEST.getId()) {
				new BukkitRunnable() {
					
					@Override
					public void run() {
						Chest bau = (Chest) e.getBlock().getState();
						while (bau.getInventory().firstEmpty() != -1) {
							bau.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 64, (short) 1));
						}
					}
				}.runTaskLater(Main.getPlugin(Main.class), 1);
				
			}

		}
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK | e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign placa = (Sign) e.getClickedBlock().getState();
				if (placa.getLine(0).equals("ADMINSHOP")) {
					e.setCancelled(true);
					String linha2 = placa.getLine(1);
					if (linha2.startsWith("B")) {
						Integer preco = Integer.valueOf(linha2.split(" ")[1]);
						int quantidade = Integer.parseInt(placa.getLine(2));
						String[] divisao = placa.getLine(3).split(":");
						int id = Integer.parseInt(divisao[0]);
						int data = Integer.valueOf(divisao[1]);

						ItemStack item = new ItemStack(Material.getMaterial(id), quantidade, (short) data);

						if (VaultAPI.getEconomy().has(p, preco)) {

							VaultAPI.getEconomy().withdrawPlayer(p, preco);
							p.getInventory().addItem(item);
							p.sendMessage(
									"§aVoce comprou x" + quantidade + " " + item.getType().name() + " por " + preco);
						} else {
							p.sendMessage("§cVoce não tem dinheiro");
						}

					} else if (linha2.startsWith("S")) {
						Integer preco = Integer.valueOf(linha2.split(" ")[1]);
						int quantidade = Integer.parseInt(placa.getLine(2));
						String[] divisao = placa.getLine(3).split(":");
						int id = Integer.parseInt(divisao[0]);
						int data = Integer.valueOf(divisao[1]);

						ItemStack item = new ItemStack(Material.getMaterial(id), quantidade, (short) data);
						if (verificarItens(p, quantidade, item.getType(), data)) {
							removerItens(p, quantidade, item.getType(), data);

							// remover
							VaultAPI.getEconomy().depositPlayer(p, preco);

							p.sendMessage(
									"§aVoce vendeu x" + quantidade + " de " + item.getType().name() + " por " + preco);
							;
						} else {
							p.sendMessage("§cVoce não tem os itens suficientes.");
						}
					}

				}
			}
		}
	}

	@EventHandler
	public void event(EntityTargetEvent e) {
		if (e.getTarget() != null) {
			if (e.getTarget() instanceof Player) {
				Player alvo = (Player) e.getTarget();
				if (e.getEntity() instanceof Creeper) {
					Creeper creeper = (Creeper) e.getEntity();
					if (creeper.getWorld().getName().equals("world")) {
						e.setCancelled(true);
					}
				}

			}
		}
	}

}
