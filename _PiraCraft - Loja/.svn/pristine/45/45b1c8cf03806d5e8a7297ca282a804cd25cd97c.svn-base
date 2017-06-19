package loja;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.PiraCraftAPI;

public class Evento implements Listener {

	public String server = Main.server;
	public static HashMap<Player, String> uuid = new HashMap<Player, String>();

	public int getPrice(String kit, int dia) {
		for (int x = 0; x < Utils.coins.size(); x++) {
			if (Utils.nomess.get(x).equalsIgnoreCase("KIT - " + ChatColor.stripColor(kit.toUpperCase()))) {
				if (dia == 1) {
					return Utils.coins.get(x);
				}
				if (dia == 7) {
					return Utils.coins.get(x) * 3;
				}
				if (dia == 3) {
					return Utils.coins.get(x) * 5;
				}
				if (dia != 1 && dia != 7 && dia != 3) {
					return Utils.coins.get(x) * 4;
				}
			}
		}
		return 0;
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		new BukkitRunnable() {
			public void run() {
				Utils.putThingsOnList(e.getPlayer());
			}
		}.runTaskLater(Main.plugin, 20);
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		if (Gui.comprou.containsKey(e.getPlayer())) {
			Gui.comprou.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if (e.getAction().name().contains("RIGHT")) {
			if (e.getItem() != null) {
				if (e.getItem().hasItemMeta()) {
					if (e.getItem().isSimilar(ItemBuilder.criar("§e§lLoja", Material.DIAMOND))) {
						e.setCancelled(true);

						Gui.lojaKitsPvP(e.getPlayer());
					}
				}
			}
		}
	}

	@EventHandler
	public void use(InventoryClickEvent e) {
		if (e.getInventory().getTitle().contains("§e§lComprar §0§l§")) {
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().hasItemMeta()) {
					e.setCancelled(true);
					if (e.getCurrentItem().getType() != Material.CARPET) {
						if (e.getCurrentItem().getType() != Material.GOLD_INGOT) {
							e.setCancelled(true);

							List<String> lore = e.getCurrentItem().getItemMeta().getLore();
							PiraCraftAPI coins = new PiraCraftAPI((Player) e.getWhoClicked());
							int a = Integer.parseInt(ChatColor
									.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).substring(0, 1));
							List<Integer> number = Arrays.asList(1, 7, 3);

							if (coins.getCoins() >= getPrice(
									e.getInventory().getTitle().substring(18, e.getInventory().getTitle().length()),
									a)) {
								e.getWhoClicked().closeInventory();
								((Player) e.getWhoClicked()).sendMessage("§bVoce acaba de adquirir o kit §e§l"
										+ e.getInventory().getTitle().substring(18,
												e.getInventory().getTitle().length())
										+ " §bpor §f§l" + e.getCurrentItem().getItemMeta().getDisplayName() + "§b!");

								Calendar cal = Calendar.getInstance();
								cal.setTime(new Date());

								if (number.contains(a)) {
									if (a == 1) {
										cal.add(Calendar.HOUR_OF_DAY, Integer.valueOf(1 * 24));
									}
									if (a == 7) {
										cal.add(Calendar.HOUR_OF_DAY, Integer.valueOf(7 * 24));
									}
									if (a == 3) {
										cal.add(Calendar.HOUR_OF_DAY, Integer.valueOf(30 * 24));
									}
								}

								coins.buy(
										Utils.getProduct("KIT - " + e.getInventory().getTitle()
												.substring(18, e.getInventory().getTitle().length()).toUpperCase()),
										getPrice(e.getInventory().getTitle().substring(18,
												e.getInventory().getTitle().length()), a),
										cal.getTime());

								br.com.piracraft.api.Main.getMysql().execute(
										"INSERT INTO `MINIGAMES_XP` (`ID_MINIGAMES`,`ID_SALA`,`UUID`,`DATAHORA`,`XP`, `KILLS`, `DEATH`, `COINS`) VALUES "
												+ "('" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[2] + "','" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0]
												+ "'" + "," + " '" + coins.getUUID() + "', '"
												+ String.valueOf(
														new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
												+ "', '0', '0'" + ", '0', '-"
												+ Integer.parseInt(lore.get(1).substring(15, lore.get(1).length()))
												+ "');");

								Gui.comprou.put((Player) e.getWhoClicked(), "KIT - " + e.getInventory().getTitle()
										.substring(18, e.getInventory().getTitle().length()).toUpperCase());

								if (Bukkit.getPluginManager().isPluginEnabled("KitPvP")) {
									me.douglasamv.kitpvp.utils.Utils.putThingsOnList();
								} else {
									Bukkit.getConsoleSender()
											.sendMessage("§aAcao nao realizada, plugin do kitpvp esta desativado.");
								}
							} else {
								e.getWhoClicked().closeInventory();
								((Player) e.getWhoClicked()).sendMessage("§cVoce nao tem coins suficientes.");
							}
						}
					} else {
						if (Main.server.toLowerCase().contains("kitpvp")) {
							Gui.lojaKitsPvP((Player) e.getWhoClicked());
						}
					}
				}
			}
		}

		if (e.getInventory().getTitle().equalsIgnoreCase("§e§oPira§f§oCraft §b- §aMain")) {
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getType() != Material.CARPET) {
						e.setCancelled(true);

						if (e.getSlot() == 12) {
							if (e.getWhoClicked().hasPermission("admin")) {
								Gui.lojaSW((Player) e.getWhoClicked());
							} else {
								e.getWhoClicked().closeInventory();
								((Player) e.getWhoClicked()).sendMessage("§cAguarde, ainda est§ sendo feita!");
							}
						}

						if (e.getSlot() == 13) {
							Gui.lojaKitsPvP((Player) e.getWhoClicked());
						}
					}
				}
			}
		}

		if (e.getInventory().getTitle().equalsIgnoreCase("§e§oPira§f§oCraft §b- §aKitPvP")) {
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().hasItemMeta()) {
					if (e.getCurrentItem().getType() != Material.CARPET) {
						e.setCancelled(true);

						if (!e.getWhoClicked().hasPermission("kitpvp.kit." + ChatColor
								.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toLowerCase())) {
							CreateGui gui = new CreateGui((Player) e.getWhoClicked(), 27,
									"§e§lComprar §0§l§ " + e.getCurrentItem().getItemMeta().getDisplayName()
											.substring(8, e.getCurrentItem().getItemMeta().getDisplayName().length()),
									ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));

							e.getWhoClicked().openInventory(gui.buildCoins());
						} else {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}

}
