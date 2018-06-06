package net.eduard.template.event;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.eduard.api.lib.storage.Mine;
import net.eduard.template.Main;

public class Teste implements Listener {
	
	public Teste(Main main) {
		
		Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
			
			@Override
			public void run() {
				for (Player p:Mine.getPlayers()){
					Scoreboard board = criarScoreboard("§6Testando", "§cLinha 1","§2","§eLinha 2");
					p.setScoreboard(board);
				}
				
				
				
			}
		}, 20, 20);
		
	}
	
	@EventHandler
	public void event(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getItem()==null)return;
		if (e.getItem().getType() == Material.COMPASS){
		abrirGui1(p);	
		}
	}
	@EventHandler
	public void event(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals("§cExemplo")){
				e.setCancelled(true);
				if (e.getCurrentItem() ==null)return;
				if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
					PlayerInventory inv = p.getInventory();
					inv.clear();
					inv.setArmorContents(null);
					p.setMaxHealth(20);
					p.setHealth(p.getMaxHealth());
					p.chat("/jogar comando");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jogar comando");
					abrirGui2(p);
				}
				
			}else if (e.getInventory().getTitle().equals("§cExemplo2")){
				if (e.getCurrentItem() ==null)return;
				
				
				
			}
		}
		
	}
	public static void abrirGui1(Player player){
		Inventory inv = Bukkit.createInventory(player, 4*9, "§cExemplo");
		player.openInventory(inv);
	}
	public static void abrirGui2(Player player){
		Inventory inv = Bukkit.createInventory(player, 4*9, "§cExemplo2");
		player.openInventory(inv);
	}
	public static Scoreboard criarScoreboard(String nome,String... linhas){
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("simpleboard", "scoreboard");
		objective.setDisplayName(nome);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Objective health = scoreboard.registerNewObjective("healthbar", Criterias.HEALTH);
		health.setDisplayName(" §aVidas");
		health.setDisplaySlot(DisplaySlot.BELOW_NAME);
		int id = 15;
		for (String linha:linhas){
			objective.getScore(new OfflinePlayer() {
				
				@Override
				public Map<String, Object> serialize() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void setOp(boolean arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean isOp() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void setWhitelisted(boolean arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setBanned(boolean arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean isWhitelisted() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean isOnline() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean isBanned() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean hasPlayedBefore() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public UUID getUniqueId() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Player getPlayer() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getName() {
					return linha;// 16  //40
				}
				
				@Override
				public long getLastPlayed() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public long getFirstPlayed() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public Location getBedSpawnLocation() {
					// TODO Auto-generated method stub
					return null;
				}
			}).setScore(id);
			
			id--;
			if (id==0)break;
		}
		
		return scoreboard;
	}
}
