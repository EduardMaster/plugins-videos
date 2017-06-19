package com.hcp.mercado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.caixas.ItemAPI;
import br.com.piracraft.api.util.MySQL;

public class Bau implements Listener{
	
	@SuppressWarnings("deprecation")
	public static void meusitens(Player p){
		Inventory inv = Bukkit.createInventory(p, 54, "§bMeus itens");
		ItemStack vidro = ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 4, " ", false);
		for (int i = 0; i < 9; i++) {
			inv.setItem(i, vidro);
		}
		inv.setItem(4, ItemAPI.Criar(Material.ARROW, 1, 0, "§aSeus itens!", false));
		for(int x = 9; x < 18; x++){
			inv.setItem(x, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 5, " ", false));
		}
		
		try {
			ResultSet rs = MySQL.getQueryResult("SELECT * FROM V_7_DIAS_ITENS_COMPRADO WHERE UUID = '" + Main.uuid.get(p)
							+ "' AND COMPRAATIVA = 1 AND ID_NETWORK = '"+Main.network.get(p)+"' ORDER BY `ID`");
			while (rs.next()) {
				if(rs.getInt("QUANT") > 0){
					if(inv.firstEmpty() > -1){
						inv.addItem(ItemAPI.Criar(Material.getMaterial(rs.getInt("ID_ITEM")), rs.getInt("QUANT"), rs.getInt("ID_VARIACAO"), "§a" + rs.getString("DESCRICAO"), false, 
								Arrays.asList(
										" ",
										"§bUUID §6- §e" + rs.getString("UUID"),
										"§bPlayer §6- §e" + p.getDisplayName())));
					}
				}
			}
			rs.getStatement().getConnection().close();
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		
		p.openInventory(inv);
	}
	
	@EventHandler
	public void clickcompass(PlayerInteractEvent e){
		if (e.getItem() != null && e.getItem().hasItemMeta()) {
			if (e.getItem().getType() == Material.ENDER_CHEST
					&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cMeus itens")) {
				e.setCancelled(true);
				meusitens(e.getPlayer());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void dfs(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
        
		if(e.getInventory().getTitle().equals("§bMeus itens")){
			e.setCancelled(true);
			if(e.getView().getBottomInventory().getType() == InventoryType.PLAYER){
					if(e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem() != null){
						if(e.getSlot() > 16){
							if(e.getClick().isKeyboardClick() || e.getClick().isCreativeAction() || e.getClick().isLeftClick() || e.getClick().isRightClick() ||
									e.getClick().isShiftClick() || e.getClick().equals(ClickType.DOUBLE_CLICK)){
								if(e.getView().getBottomInventory().firstEmpty() > -1){
									if(e.getCurrentItem().getAmount() > 9){
										int x = e.getCurrentItem().getAmount() - 10;
										String y = Carregar.getItem(e.getCurrentItem().getTypeId(), String.valueOf(e.getCurrentItem().getData().getData()));
										
										new BukkitRunnable() {
											public void run() {
												MySQL.execute("UPDATE 7_DIAS_COMPRAS_ITENS_LOJA SET QUANT = " + x
															+ " WHERE UUID = '"+Main.uuid.get(p)+"' AND ID_COD_ITEM = "+ Integer.parseInt(y) +" AND ID_NETWORK = "+Main.network.get(p)+";");
											}
										}.runTaskAsynchronously(com.hcp.Main.plugin);
										
										e.getCurrentItem().setAmount(10);
										e.getView().getBottomInventory().addItem(e.getCurrentItem());
										e.getCurrentItem().setAmount(x);
										e.getInventory().setItem(e.getSlot(), e.getCurrentItem());
									}else{
										String y = Carregar.getItem(e.getCurrentItem().getTypeId(), String.valueOf(e.getCurrentItem().getData().getData()));
										
										new BukkitRunnable() {
											public void run() {
												MySQL.execute("UPDATE 7_DIAS_COMPRAS_ITENS_LOJA SET QUANT = 0"
															+ " WHERE UUID = '"+Main.uuid.get(p)+"' AND ID_COD_ITEM = "+ Integer.parseInt(y) +" AND ID_NETWORK = "+Main.network.get(p)+";");
											}
										}.runTaskAsynchronously(com.hcp.Main.plugin);
										
										e.getView().getBottomInventory().addItem(e.getCurrentItem());
										e.getInventory().remove(e.getCurrentItem());
									}
								}else{
									if(Main.network.get(p) != 1){
										p.sendMessage("§5§lShop§f§l» §cDrop some items, your inventory is full!");
									}else{
										p.sendMessage("§5§lShop§f§l» §cJogue alguns itens fora, seu inventario esta cheio!");
									}
									
									p.closeInventory();
								}
							}					
						}
					}else{
						e.setCancelled(true);
					}
			}
		}
	}
}
