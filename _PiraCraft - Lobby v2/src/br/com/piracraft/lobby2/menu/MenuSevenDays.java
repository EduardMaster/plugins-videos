package br.com.piracraft.lobby2.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.PiraCraftAPI;
import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.util.MySQL;
import br.com.piracraft.api.util.Servidor;
import br.com.piracraft.lobby2.extencoes.Entradas;
import br.com.piracraft.lobby2.extencoes.NomesBungee;
import br.com.piracraft.lobby2.extencoes.SevenDays;
import br.com.piracraft.lobby2.utils.LobbyAPI;
import br.com.piracraft.lobby2.utils.SetarScoreBoard;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class MenuSevenDays implements Listener {
	/*
	 * CLASSE TEMPORÁRIA!
	 * NAO MEXER
	 * ESTA TUDO 100%	
	 */

	public static void openinv(Player p) {
		Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§9Shopping");

		inv.setItem(1, ItemAPI.Criar(Material.EMERALD, 1, 0, "§aReviver §7§o(Respawn)", false,
				Arrays.asList(" ", "§a- §eCom isso, voce podera", "§edar respawn na sala onde morreu!")));
		inv.setItem(2, ItemAPI.Criar(Material.GOLD_INGOT, 1, 0, "§aTroca de coins §7§o(Exchange)", false,
				Arrays.asList(" ", "§a- §eAqui voce troca seus coins por cash!")));
		inv.setItem(3, ItemAPI.Criar(Material.PAPER, 1, 0, "§aIngresso §7§o(Ticket)", false,
				Arrays.asList(" ", "§a- §eCom isso, voce podera", "§eentrar numa sala que escolher!")));

		p.openInventory(inv);
	}
	
	public static void openinvservers(Player p) {
		Inventory inv = Bukkit.createInventory(p, 18, "§1§lRooms");

		for (int x = 8; x < 18; x++) {
			inv.setItem(x, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		}

		for (int x = 0; x < 9; x++) {
			inv.setItem(x, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 14, " ", false));
		}

		inv.setItem(0, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		inv.setItem(8, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		
		inv.setItem(13, ItemAPI.Criar(Material.DIAMOND, 1, 0, "§a §6Clique aqui para ser levado a loja!", false));
		if(Entradas.contains(Main.uuid.get(p))){
			
		for(int y = 0; y < Entradas.whitelist.size(); y++){
			for (int x = 0; x < SevenDays.disponiveis.size(); x++) {
				if(Entradas.whitelist.get(y).getIdsala() == getSala(SevenDays.disponiveis.get(x).getServidor())){
					if(Entradas.whitelist.get(y).getIdNetwork() == Main.network.get(p)){
						inv.setItem(x + 1,
								ItemAPI.Criar(Material.SKULL_ITEM, 1, 0, "§e§o" + SevenDays.disponiveis.get(x).getServidor(), false,
										Arrays.asList(" ", "§a- §bPremio: §e" + SevenDays.disponiveis.get(x).getPremio(),
												"§a- §bData final: §e" + SevenDays.disponiveis.get(x).getDataFinal())));
					}
				}
			}
		}
		
		}

		p.openInventory(inv);
	}

	public void openchangecoinscash(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "§1§lExchange");

		if (new PiraCraftAPI(p).getCoins() >= 2500) {
			for (int x = 0; x < 27; x++) {
				inv.setItem(x, ItemAPI.Criar(Material.THIN_GLASS, 1, 0, " ", false));
			}
			p.openInventory(inv);

			inv.setItem(4, ItemAPI.Criar(Material.IRON_INGOT, 1, 0,
					"§6Seus coins §b- §a" + new PiraCraftAPI(p).getCoins(), false));
			inv.setItem(13,
					ItemAPI.Criar(Material.GOLD_INGOT, 1, 0, "§eCoins a serem trocados por cash!", false,
							Arrays.asList(" ", "§7Total a ser pago §b- §62500 coins",
									"§7A cada +1 cash, adiciona mais §62500 coins§7!")));
			inv.setItem(22, ItemAPI.Criar(Material.EMERALD_BLOCK, 1, 0, "§aConfirmar compra", false));
		}

		if (new PiraCraftAPI(p).getCoins() < 2500) {
			for (int x = 0; x < 27; x++) {
				inv.setItem(x, ItemAPI.Criar(Material.THIN_GLASS, 1, 0, " ", false));
			}
			p.openInventory(inv);

			inv.setItem(13, ItemAPI.Criar(Material.REDSTONE_BLOCK, 1, 0, "§cVoce nao pode realizar esta transacao.",
					false, Arrays.asList("§e- §aSeus coins §6" + new PiraCraftAPI(p).getCoins())));
		}
	}

	public void openinvbuy(Player p) {
		Inventory inv = Bukkit.createInventory(p, 18, "§1§lTickets");

		for (int x = 8; x < 18; x++) {
			inv.setItem(x, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		}

		for (int x = 0; x < 9; x++) {
			inv.setItem(x, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 14, " ", false));
		}

		inv.setItem(0, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		inv.setItem(8, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));

		for (int x = 0; x < SevenDays.disponiveis.size(); x++) {
			inv.setItem(x + 1,
					ItemAPI.Criar(Material.SKULL_ITEM, 1, 0, "§e§o" + SevenDays.disponiveis.get(x).getServidor(), false,
							Arrays.asList(" ", "§a- §bPremio: §e" + SevenDays.disponiveis.get(x).getPremio(),
									"§a- §bPreco: §e" + SevenDays.disponiveis.get(x).getCustoIngresso(),
									"§a- §bIngressos disponiveis: §e" + SevenDays.disponiveis.get(x).getComprasDisponiveis(),
									"§a- §bData final: §e" + SevenDays.disponiveis.get(x).getDataFinal(),
									"§a- §6Caso ja tenha adquirido, apenas clique para entrar!")));
		}

		p.openInventory(inv);
	}

	public void openinvrespawn(Player p) {
		Inventory inv = Bukkit.createInventory(p, 18, "§1§lRespawn");

		for (int x = 8; x < 18; x++) {
			inv.setItem(x, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		}

		for (int x = 0; x < 9; x++) {
			inv.setItem(x, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 14, " ", false));
		}

		inv.setItem(0, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));
		inv.setItem(8, ItemAPI.Criar(Material.VINE, 1, 0, " ", false));

		for (int x = 0; x < SevenDays.reviver.size(); x++) {
			if (SevenDays.reviver.get(x).getReviverDisponivel() == 1) {
				inv.setItem(x + 1,
						ItemAPI.Criar(Material.SKULL_ITEM, 1, 0, "§e§o" + SevenDays.reviver.get(x).getServidor(), false,
								Arrays.asList(" ", "§a- §bPremio: §e" + SevenDays.disponiveis.get(x).getPremio(),
										"§a- §bPreco: §e" + SevenDays.reviver.get(x).getCustoReviver(),
										"§a- §bIngressos disponiveis: §e" + SevenDays.disponiveis.get(x).getComprasDisponiveis(),
										"§a- §bData final: §e" + SevenDays.disponiveis.get(x).getDataFinal())));
			}
		}

		p.openInventory(inv);
	}

	@EventHandler
	public void onclickinv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equalsIgnoreCase("§1§lRooms")) {
			if (e.getCurrentItem() != null
					&& e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType()==Material.DIAMOND){
					p.teleport(new Location(p.getWorld(), 119, 41, 34));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
				}
			}
		}
		if (e.getInventory().getTitle().equalsIgnoreCase("§1§lExchange")) {
			if (e.getCurrentItem() != null
					&& e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);

				if (e.getCurrentItem().getType() == Material.GOLD_INGOT) {
					e.setCancelled(true);

					e.getInventory().getItem(13).setAmount(e.getInventory().getItem(13).getAmount() + 1);
					e.getInventory().setItem(13, e.getInventory().getItem(13));
					e.getInventory().getItem(13).getItemMeta().setLore(Arrays.asList(" ",
							"§7Total a ser pago §b- §6" + e.getInventory().getItem(13).getAmount() * 2500 + " coins",
							"§7A cada +1 cash, adiciona mais §62500 coins§7!"));

					new SetarScoreBoard(p,
							br.com.piracraft.api.Main.network.get(p)).setar();
				}

				if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
					e.setCancelled(true);

					int coinsPraPagar = e.getInventory().getItem(13).getAmount() * 2500;

					if (new PiraCraftAPI(p).getCoins() >= coinsPraPagar) {
						p.closeInventory();

						if (Main.network.get(p) == 1) {
							p.sendMessage("§c§lExchange§f§l» §bVoce trocou §4§l" + coinsPraPagar + " §bem §a§l"
									+ e.getInventory().getItem(13).getAmount() + " Cash§b!");
						} else {
							p.sendMessage("§c§lExchange§f§l» §bYou exchanged §4§l" + coinsPraPagar + " §bon §a§l"
									+ e.getInventory().getItem(13).getAmount() + " Cash§b!");
						}

						MySQL.execute("INSERT INTO 7_DIAS_TROCA_MOEDAS_CASH (`UUID`,`COINS`,`TIPO`,`ID_NETWORK`) VALUES ('"
								+ Main.uuid.get(p) + "','" + coinsPraPagar + "','1','"+Main.network.get(p)+"')");
						MySQL.execute("INSERT INTO 7_DIAS_TROCA_MOEDAS_CASH (`UUID`,`CASH`,`TIPO`,`ID_NETWORK`) VALUES ('"
								+ Main.uuid.get(p) + "','" + e.getInventory().getItem(13).getAmount() + "','2','"+Main.network.get(p)+"')");
						
						new SetarScoreBoard(p,
								br.com.piracraft.api.Main.network.get(p)).setar();
					}
				}
			}
		}
		if (e.getInventory().getTitle().equalsIgnoreCase("§1§lTickets")) {
			if (e.getCurrentItem() != null
					&& e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);

				if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					if(!isOut(getSala(
									ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())))){
						int price = Integer.parseInt(
								ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)).substring(9,
										ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)).length()));
						if (getPlayerRoom(Main.uuid.get((Player) e.getWhoClicked()),
								getSala(
										ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())))) {
							((Player) e.getWhoClicked()).closeInventory();

							for (int y = 0; y < Entradas.whitelist.size(); y++) {
								if (Entradas.contains(br.com.piracraft.api.Main.uuid.get(p))) {
									if (Entradas.whitelist.get(y).getUuid()
											.equalsIgnoreCase(br.com.piracraft.api.Main.uuid.get(p))) {
										if(Entradas.whitelist.get(y).getIdNetwork() == br.com.piracraft.api.Main.network.get(p)){
											if (Entradas.whitelist.get(y).getIdsala() == getSala(
													ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) {
												if(Entradas.whitelist.get(y).isDisponivel(getSala(
														ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())))){
													for(int z = 0; z <NomesBungee.nomes.size(); z++){
														if(NomesBungee.nomes.get(z).getIdsala()==getSala(
																ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))){
															LobbyAPI.enviar(p, NomesBungee.nomes.get(z).getNome());
														}
													}
												}else{
													p.sendMessage("§e§l- §4§lHardcore §e§l-");
													p.sendMessage(" ");
													if(br.com.piracraft.api.Main.network.get(p) != 1){
														p.sendMessage("§cYou can't join!");
													}else{
														p.sendMessage("§cEntrada bloqueada!");
													}
													p.sendMessage(" ");
												}
											}
										}else{
											p.sendMessage("§e§l- §4§lHardcore §e§l-");
											p.sendMessage(" ");
											if(br.com.piracraft.api.Main.network.get(p) != 1){
												p.sendMessage("§cYou can't join!");
												if(Entradas.whitelist.get(y).getIdNetwork() == 1){
													p.sendMessage("§cYou bought your ticket on §6§lPira§f§lCraft§c.");
												}else{
													p.sendMessage("§cYou bought your ticket on §4§lU§6§lPlay§9§lCraft§c.");
												}
											}else{
												if(Entradas.whitelist.get(y).getIdNetwork() == 1){
													p.sendMessage("§cVoce comprou seu ingresso no §6§lPira§f§lCraft§c.");
												}else{
													p.sendMessage("§cVoce comprou seu ingresso no §4§lU§6§lPlay§9§lCraft§c.");
												}
											}
											p.sendMessage(" ");
										}
									}
								} else {
									p.sendMessage("§e§l- §4§lHardcore §e§l-");
									p.sendMessage(" ");
									if(br.com.piracraft.api.Main.network.get(p) != 1){
										p.sendMessage("§6You can't join!");
										p.sendMessage(
												"§cReason: §7You haven't bought your ticket or you didn't die yet.");
										p.sendMessage(
												"§aSolutions: §7Go to the NPC and get the ticket, if you already bought it and can not get in, wait 5 minutes and try again.");
										p.sendMessage(" ");
									}else{
										p.sendMessage("§6Voce nao pode entrar aqui!");
										p.sendMessage(
												"§cMotivo: §7Voce ainda nao adquiriu o ingresso ou ja morreu.");
										p.sendMessage(
												"§aSolucoes: §7Va ate o NPC e adquira o ingresso, caso ja tenha comprado e nao consegue entrar, aguarde 5 minutos e tente novamente.");
										p.sendMessage(" ");
									}
								}
							}
						} else {
							if (new PiraCraftAPI(p).getCash() >= price) {
								((Player) e.getWhoClicked()).closeInventory();

								((Player) e.getWhoClicked()).sendMessage(" ");
								((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
								((Player) e.getWhoClicked()).sendMessage(" ");
								((Player) e.getWhoClicked()).sendMessage(
										"§bIngresso adquirido: §7" + e.getCurrentItem().getItemMeta().getDisplayName());
								((Player) e.getWhoClicked()).sendMessage("§bCash gasto: §7-" + price);
								((Player) e.getWhoClicked()).sendMessage("§6Ingresso valido apenas nessa sala!");
								((Player) e.getWhoClicked()).sendMessage(" ");

								MySQL.execute(
										"UPDATE MINIGAMES_SALAS_E_SERVIDORES SET SALA_REFRESH = 2 WHERE ID_MINIGAMESSALAS = "
												+ "'"
												+ getSala(ChatColor
														.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))
												+ "'");
								
								MySQL.execute("INSERT INTO 7_DIAS_INSCRICAO_SALA (`ID_SALA`,`UUID`,`ACESSO_COMPRADO`,`VL_ACESSO_PAGO`,`ACESSO_BLOQUEADO`,`ID_NETWORK`)"
													+ " VALUES ('"
													+ getSala(ChatColor
															.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))
													+ "'," + "'" + Main.uuid.get(((Player) e.getWhoClicked())) + "','1','"
													+ price + "','0','"+Main.network.get((Player) e.getWhoClicked())+"');");
								
								new SetarScoreBoard(p,
										br.com.piracraft.api.Main.network.get(p)).setar();
							} else {
								((Player) e.getWhoClicked()).closeInventory();

								((Player) e.getWhoClicked()).sendMessage(" ");
								((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
								((Player) e.getWhoClicked()).sendMessage(" ");
								((Player) e.getWhoClicked()).sendMessage("§bValor do ingresso: §7" + price);
								((Player) e.getWhoClicked()).sendMessage("§cVoce nao tem o cash necessario para a compra.");
								((Player) e.getWhoClicked()).sendMessage(" ");
							}
						}				
					}else{
						((Player) e.getWhoClicked()).closeInventory();

						((Player) e.getWhoClicked()).sendMessage(" ");
						((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
						((Player) e.getWhoClicked()).sendMessage(" ");
						((Player) e.getWhoClicked()).sendMessage("§cSala esgotada!");
						((Player) e.getWhoClicked()).sendMessage(" ");
					}
				}
			}
		}
		if (e.getInventory().getTitle().equalsIgnoreCase("§1§lRespawn")) {
			if (e.getCurrentItem() != null
					&& e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);

				if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					int price = Integer.parseInt(
							ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)).substring(9,
									ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)).length()));
					if (getPlayerRoom(Main.uuid.get((Player) e.getWhoClicked()),
							getSala(
									ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())))) {
						if (new PiraCraftAPI(p).getCash() >= price) {
							((Player) e.getWhoClicked()).closeInventory();

							((Player) e.getWhoClicked()).sendMessage(" ");
							((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
							((Player) e.getWhoClicked()).sendMessage(" ");
							((Player) e.getWhoClicked()).sendMessage(
									"§bRespawn adquirido: §7" + e.getCurrentItem().getItemMeta().getDisplayName());
							((Player) e.getWhoClicked()).sendMessage("§bCash gasto: §7-" + price);
							((Player) e.getWhoClicked()).sendMessage("§6Respawn valido apenas nessa sala!");
							((Player) e.getWhoClicked()).sendMessage(" ");

							MySQL.execute(
									"UPDATE MINIGAMES_SALAS_E_SERVIDORES SET SALA_REFRESH = 2 WHERE ID_MINIGAMESSALAS = "
											+ "'"
											+ getSala(ChatColor
													.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))
											+ "'");
							
							MySQL.execute("INSERT INTO 7_DIAS_COMPRA_REVIVER (`UUID`,`ID_SALA`,`DATAHORA`,`VL_REVIVER_CASH`)"
												+ " VALUES ('" + Main.uuid.get((Player) e.getWhoClicked()) + "','"
												+ getSala(ChatColor
														.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))
												+ "'" + ",'"
												+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','"
												+ price + "');");
							
							MySQL.execute("UPDATE 7_DIAS_INSCRICAO_SALA SET ACESSO_BLOQUEADO = 0, ID_NETWORK = '"+Main.network.get((Player) e.getWhoClicked())+"' WHERE UUID = '"
									+ Main.uuid.get((Player) e.getWhoClicked()) + "'" + " AND ID_SALA = '"
									+ getSala(
											ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))
									+ "';");

							new SetarScoreBoard(p,
									br.com.piracraft.api.Main.network.get(p)).setar();
						} else {
							((Player) e.getWhoClicked()).closeInventory();

							((Player) e.getWhoClicked()).sendMessage(" ");
							((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
							((Player) e.getWhoClicked()).sendMessage(" ");
							((Player) e.getWhoClicked()).sendMessage("§bValor do respawn: §7" + price);
							((Player) e.getWhoClicked()).sendMessage("§cVoce nao tem o cash necessario para a compra.");
							((Player) e.getWhoClicked()).sendMessage(" ");
						}
					} else {
						((Player) e.getWhoClicked()).closeInventory();

						((Player) e.getWhoClicked()).sendMessage(" ");
						((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
						((Player) e.getWhoClicked()).sendMessage(" ");
						((Player) e.getWhoClicked())
								.sendMessage("§cVoce nao esta autorizado a comprar o §c§oreviver §cpara esta sala.");
						((Player) e.getWhoClicked()).sendMessage("§cTente novamente.");
						((Player) e.getWhoClicked()).sendMessage(" ");
					}
				}
			}
		}
		if (e.getInventory().getTitle().equalsIgnoreCase("§9Shopping")) {
			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem() != null
					&& e.getCurrentItem().getType() != Material.AIR) {
				e.setCancelled(true);

				if (e.getSlot() == 1) {
					if (containsPlayer(Main.uuid.get((Player) e.getWhoClicked()))) {
						openinvrespawn((Player) e.getWhoClicked());
					} else {
						((Player) e.getWhoClicked()).closeInventory();
						((Player) e.getWhoClicked()).sendMessage(" ");
						((Player) e.getWhoClicked()).sendMessage("§e- §4§lHardcore §e-");
						((Player) e.getWhoClicked()).sendMessage(" ");
						((Player) e.getWhoClicked()).sendMessage("§eVoce nao pode adquirir seu respawn.");
						((Player) e.getWhoClicked())
								.sendMessage("§bMotivos §7Voce pode nao ter comprado seu ingresso ainda.");
						((Player) e.getWhoClicked()).sendMessage("§6Caso contrario, voce ainda nao morreu.");
						((Player) e.getWhoClicked()).sendMessage(" ");
					}
				}

				if (e.getSlot() == 2) {
					openchangecoinscash((Player) e.getWhoClicked());
				}

				if (e.getSlot() == 3) {
					openinvbuy((Player) e.getWhoClicked());
				}
			}
		}
	}

	public boolean containsPlayer(String uuid) {
		boolean a = false;
		try {
			ResultSet rs = MySQL.getQueryResult(
					"SELECT * FROM 7_DIAS_INSCRICAO_SALA WHERE UUID = '" + uuid + "' AND ACESSO_BLOQUEADO = 1");
			if (rs.next()) {
				a = true;
			}
			rs.getStatement().getConnection().close();
			;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public static boolean isOut(int sala){
		boolean a = false;
		try{
			ResultSet s = MySQL.getQueryResult("SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE ID_MINIGAMESSALAS = '"+sala+"'");
			if(s.next()){
				if(s.getInt("7_DIAS_TOTAL_INGRESSOS_DISPONIVEIS") == 0){
					a=true;
				}
			}
			s.getStatement().getConnection().close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return a;
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------
	 * ------------------------------ DAQUI PARA BAIXO, UTILIZADO APENAS PARA O LOBBY HARDCORE - 7 DIAS -----------------------
	 * ------------------------------------------------------------------------------------------------------------------------
	 * */
	public static boolean getPlayerRoom(String uuid, int sala) {
		boolean a = false;
		try {
			ResultSet rs = MySQL.getQueryResult(
					"SELECT * FROM 7_DIAS_INSCRICAO_SALA WHERE UUID = '" + uuid + "' AND ID_SALA = '" + sala + "'");
			if (rs.next()) {
				a = true;
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static List<String> getRoomList(String uuid) {
		List<String> a = new ArrayList<String>();
		try {
			ResultSet rs = MySQL.getQueryResult("SELECT * FROM 7_DIAS_INSCRICAO_SALA WHERE UUID = '" + uuid + "'");
			while (rs.next()) {
				a.add(PiraCraftAPI.getIdNomeSala(0, rs.getInt("ID_SALA"))[1]);
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public static int getSala(String nomedasala) {
		int a = 0;
		try {
			ResultSet rs = MySQL.getQueryResult("SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE NOMEDASALA = '" + nomedasala + "'");
			if (rs.next()) {
				a = rs.getInt("ID_MINIGAMESSALAS");
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static String getSala(int sala) {
		String a = null;
		try {
			ResultSet rs = MySQL.getQueryResult("SELECT * FROM MINIGAMES_SALAS_E_SERVIDORES WHERE ID_MINIGAMESSALA = '" + sala + "'");
			if (rs.next()) {
				a = rs.getString("NOMEDASALA");
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}
}
