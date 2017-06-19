package br.com.piracraft.lobby2.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.PiraCraftAPI;
import br.com.piracraft.api.util.MySQL;
import br.com.piracraft.lobby2.event.PlayerManager;
import br.com.piracraft.lobby2.extencoes.Loja;

public class Shop implements Listener {

	public static ArrayList<Player> pla = new ArrayList<Player>();

	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/placas")) {
			if (e.getPlayer().isOp()) {
				e.setCancelled(true);

				if (pla.contains(e.getPlayer())) {
					pla.remove(e.getPlayer());

					PlayerManager.darItens(e.getPlayer());
				} else {
					e.getPlayer().getInventory().clear();
					e.getPlayer().setItemInHand(new ItemStack(Material.IRON_AXE));

					e.getPlayer().sendMessage("§aClique nas placas!");

					pla.add(e.getPlayer());
				}
			}
		}
	}

	public static ArrayList<String> comprando = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void clickSign(PlayerInteractEvent e) {
		if (pla.contains(e.getPlayer())) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.SIGN
						|| e.getClickedBlock().getType() == Material.SIGN_POST
						|| e.getClickedBlock().getType() == Material.WALL_SIGN) {
					e.setCancelled(true);

					Sign s = (Sign) e.getClickedBlock().getState();

					if (s.getLine(0).contains("LOJA")) {

					} else {
						if (s.getLine(0).contains(":")) {
							String m = Material.getMaterial(Integer.parseInt(s.getLine(0).split(":")[0])).toString();
							int m1 = Integer.parseInt(s.getLine(0).split(":")[1]);

							for (int x = 0; x < Loja.shop.size(); x++) {
								if (Material.getMaterial(m).getId() == Loja.shop.get(x).getItem()
										&& m1 == Loja.shop.get(x).getVariacao()) {
									if (Loja.shop.get(x).isAtivo()) {
										s.setLine(0, "§f» §4§lLOJA §f«");
										if (Loja.shop.get(x).isCash()) {
											s.setLine(1, "§4Cash §5" + Loja.shop.get(x).getValorCash());
										} else {
											s.setLine(1, "§4Coins §5" + Loja.shop.get(x).getValorCoins());
										}
										s.setLine(2, "§5" + Loja.shop.get(x).getQuantidade() + " §1unidades");
										s.setLine(3, getName(Loja.shop.get(x).getIdTabela()).toUpperCase());

										s.update();
									}
								}
							}
						} else {
							int a = Integer.parseInt(s.getLine(0));
							for (int x = 0; x < Loja.shop.size(); x++) {
								if (a == Loja.shop.get(x).getItem()) {
									if (Loja.shop.get(x).isAtivo()) {
										s.setLine(0, "§f» §4§lLOJA §f«");
										if (Loja.shop.get(x).isCash()) {
											s.setLine(1, "§4Cash §5" + Loja.shop.get(x).getValorCash());
										} else {
											s.setLine(1, "§4Coins §5" + Loja.shop.get(x).getValorCoins());
										}
										s.setLine(2, "§5" + Loja.shop.get(x).getQuantidade() + " §1unidades");
										s.setLine(3, getName(Loja.shop.get(x).getIdTabela()).toUpperCase());

										s.update();
									}
								}
							}
						}
					}
				}
			}
		} else {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.SIGN
						|| e.getClickedBlock().getType() == Material.SIGN_POST
						|| e.getClickedBlock().getType() == Material.WALL_SIGN) {
					e.setCancelled(true);

					Sign s = (Sign) e.getClickedBlock().getState();

					if (s.getLine(0).contains("LOJA")) {
						for (int x = 0; x < Loja.shop.size(); x++) {
							if (Loja.shop.get(x).isAtivo()) {
								if (getItem(Loja.shop.get(x).getItem(), Loja.shop.get(x).getVariacao()) == Loja.shop
										.get(x).getIdTabela()) {
									if (getName(Loja.shop.get(x).getIdTabela()).equalsIgnoreCase(s.getLine(3))) {
										if (!comprando.contains(Main.uuid.get(e.getPlayer()))) {
											if (Loja.shop.get(x).isCash()) {
												if (new PiraCraftAPI(e.getPlayer()).getCash() >= Loja.shop
														.get(x).getValorCash()) {
													buyWithCash(e.getPlayer(), true, Loja.shop.get(x).getValorCash(), 0,
															Loja.shop.get(x).getIdTabela(),
															Loja.shop.get(x).getQuantidade());
													if (Main.network.get(e.getPlayer()) == 1) {
														e.getPlayer().sendMessage(
																"§4§lHardcore§f§l» §eVoce acaba de comprar §b"
																		+ Loja.shop.get(x).getQuantidade()
																		+ "und §ede §a"
																		+ getName(Loja.shop.get(x).getIdTabela()));
													} else {
														e.getPlayer()
																.sendMessage("§4§lHardcore§f§l» §eYou bought §b"
																		+ Loja.shop.get(x).getQuantidade() + "u §eof §a"
																		+ getName(Loja.shop.get(x).getIdTabela()));
													}
												} else {
													if (Main.network.get(e.getPlayer()) == 1) {
														e.getPlayer()
																.sendMessage("§4§lHardcore§f§l» §eVoce necessita ter §a"
																		+ Loja.shop.get(x).getValorCash()
																		+ "§e de cash.");
													} else {
														e.getPlayer().sendMessage("§4§lHardcore§f§l» §eYou need §a"
																+ Loja.shop.get(x).getValorCash() + "§e of cash.");
													}
												}
											} else {
												if (new PiraCraftAPI(e.getPlayer()).getCoins() >= Loja.shop
														.get(x).getValorCoins()) {
													buyWithCash(e.getPlayer(), false, 0,
															Loja.shop.get(x).getValorCoins(),
															Loja.shop.get(x).getIdTabela(),
															Loja.shop.get(x).getQuantidade());
													if (Main.network.get(e.getPlayer()) == 1) {
														e.getPlayer().sendMessage(
																"§4§lHardcore§f§l» §eVoce acaba de comprar §b"
																		+ Loja.shop.get(x).getQuantidade()
																		+ "und §ede §a"
																		+ getName(Loja.shop.get(x).getIdTabela()));
													} else {
														e.getPlayer()
																.sendMessage("§4§lHardcore§f§l» §eYou bought §b"
																		+ Loja.shop.get(x).getQuantidade() + "u §eof §a"
																		+ getName(Loja.shop.get(x).getIdTabela()));
													}
												} else {
													if (Main.network.get(e.getPlayer()) == 1) {
														e.getPlayer()
																.sendMessage("§4§lHardcore§f§l» §eVoce necessita ter §a"
																		+ Loja.shop.get(x).getValorCoins()
																		+ "§e de coins.");
													} else {
														e.getPlayer().sendMessage("§4§lHardcore§f§l» §eYou need §a"
																+ Loja.shop.get(x).getValorCoins() + "§e of coins.");
													}
												}
											}

											comprando.add(Main.uuid.get((Player) e.getPlayer()));

											new BukkitRunnable() {
												public void run() {
													comprando.remove(Main.uuid.get((Player) e.getPlayer()));
												}
											}.runTaskLater(br.com.piracraft.lobby2.Main.getPlugin(), 5);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void buyWithCash(Player p, boolean cash, int cashFinal, int coinsFinal, int idTabela, int quant) {
		new SetarScoreBoard(p,
				br.com.piracraft.api.Main.network.get(p)).setar();
		if (cash) {
			try {
				MySQL.execute("INSERT INTO BANK_MINECRAFT (`ID_SALA`,`ID_ITEM_COMPRA`,`UUID`,`DATAHORA`,`CASH`,`DESCRICAO`,`ID_NETWORK`) VALUES"
								+ " ('" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "','" + idTabela + "','" + Main.uuid.get(p) + "'"
								+ ",'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','"
								+ cashFinal + "','Compra feita utilizando cash','"+Main.network.get(p)+"')");

				ResultSet rs = MySQL.getQueryResult("SELECT * FROM 7_DIAS_COMPRAS_ITENS_LOJA WHERE UUID = '" + Main.uuid.get(p)
								+ "' AND ID_COD_ITEM = '" + idTabela + "' AND ID_NETWORK = '"+Main.network.get(p)+"'");
				if (rs.next()) {
					int quan = rs.getInt("QUANT") + quant;
					MySQL.execute("UPDATE 7_DIAS_COMPRAS_ITENS_LOJA SET QUANT = '" + quan + "'," + "DATAHORA = '"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
							+ "',ATIVO = '1' WHERE UUID = '" + Main.uuid.get(p) + "' AND ID_COD_ITEM = '" + idTabela
							+ "'");
				} else {
					MySQL.execute(
							"INSERT INTO 7_DIAS_COMPRAS_ITENS_LOJA (`ID_COD_ITEM`,`UUID`,`QUANT`,`DATAHORA`,`ATIVO`,`ID_NETWORK`,`CASH`) "
									+ "VALUES " + "('" + idTabela + "','" + Main.uuid.get(p) + "'," + "'" + quant
									+ "','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','1','"
									+ Main.network.get(p) + "','" + cashFinal + "')");
				}
				rs.getStatement().getConnection().close();
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		} else {
			try {
				MySQL.execute(
						"INSERT INTO BANK_MINECRAFT (`ID_SALA`,`ID_ITEM_COMPRA`,`UUID`,`DATAHORA`,`COINS`,`DESCRICAO`,`ID_NETWORK`) VALUES"
								+ " ('" + PiraCraftAPI.getIdNomeSala(Bukkit.getPort(), 0)[0] + "','" + idTabela + "','" + Main.uuid.get(p) + "'"
								+ ",'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','"
								+ coinsFinal + "','Compra feita utilizando coins','"+Main.network.get(p)+"')");

				ResultSet rs = MySQL.getQueryResult("SELECT * FROM 7_DIAS_COMPRAS_ITENS_LOJA WHERE UUID = '" + Main.uuid.get(p)
								+ "' AND ID_COD_ITEM = '" + idTabela + "' AND ID_NETWORK = '"+Main.network.get(p)+"'");
				if (rs.next()) {
					int quan = rs.getInt("QUANT") + quant;
					MySQL.execute("UPDATE 7_DIAS_COMPRAS_ITENS_LOJA SET QUANT = '" + quan + "'," + "DATAHORA = '"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
							+ "',ATIVO = '1' WHERE UUID = '" + Main.uuid.get(p) + "' AND ID_COD_ITEM = '" + idTabela
							+ "'");
				} else {
					MySQL.execute(
							"INSERT INTO 7_DIAS_COMPRAS_ITENS_LOJA (`ID_COD_ITEM`,`UUID`,`QUANT`,`DATAHORA`,`ATIVO`,`ID_NETWORK`,`COINS`) "
									+ "VALUES " + "('" + idTabela + "','" + Main.uuid.get(p) + "'," + "'" + quant
									+ "','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','1','"
									+ Main.network.get(p) + "','" + coinsFinal + "')");
				}
				rs.getStatement().getConnection().close();
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		}
	}

	public static boolean isNumber(String a) {
		boolean b = false;
		try {
			Integer.parseInt(a);
			b = true;
		} catch (NumberFormatException e) {
			b = false;
		}
		return b;
	}

	public static int getItem(int id, int variacao) {
		int a = 0;
		for (int x = 0; x < Loja.lojaMinecraft.size(); x++) {
			if (Loja.lojaMinecraft.get(x).getItem() == id && Loja.lojaMinecraft.get(x).getVariacao() == variacao) {
				a = Loja.lojaMinecraft.get(x).getId();
			}
		}
		return a;
	}

	public static String getName(int idTabela) {
		String a = null;
		for (int x = 0; x < Loja.lojaMinecraft.size(); x++) {
			if (Loja.lojaMinecraft.get(x).getId() == idTabela) {
				a = Loja.lojaMinecraft.get(x).getNomeBukkit();
			}
		}
		return a;
	}
}
