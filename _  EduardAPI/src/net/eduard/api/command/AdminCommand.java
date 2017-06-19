
package net.eduard.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import net.eduard.api.API;
import net.eduard.api.game.Jump;
import net.eduard.api.game.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Slot;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.ItemAPI;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.manager.VaultAPI;
import net.eduard.api.util.Cs;

public class AdminCommand extends CMD {

	public AdminCommand() {
		super("admin");
		hasEvents = true;
	}

	public static List<Player> players = new ArrayList<>();
	public Jump jumpEffect = new Jump(Sounds.create(Sound.BAT_LOOP),
			new Vector(0, 2, 0));
	public String messageOn = "Voce entrou no Modo Admin!";
	public String messageOff = "Voce saiu do Modo Admin!";

	public Slot testAutoSoup = new Slot(
			API.newItem(Material.ENDER_PEARL, "Testar Auto-Soup"), 3);

	public Slot testFF = new Slot(
			API.newItem(Material.MAGMA_CREAM, "Ativar Troca Rapida"), 2);

	public Slot testPrison = new Slot(
			API.newItem(Material.MAGMA_CREAM, "Aprisionar Jogador"), 1);

	public Slot testNoFall = new Slot(
			API.newItem(Material.FEATHER, "Testar No-Fall"), 4);

	public Slot testInfo = new Slot(
			API.newItem(Material.BLAZE_ROD, "Ver Informações"), 5);

	public Slot testAntKB = new Slot(
			ItemAPI.addEnchant(API.newItem(Material.STICK, "Testar Knockback"),
					Enchantment.KNOCKBACK, 10),
			6);

	public void joinAdminMode(Player player) {
		ItemAPI.saveItems(player);
		GameAPI.hide(player);
		players.add(player);
		PlayerInventory inv = player.getInventory();
		testAutoSoup.give(inv);
		testFF.give(inv);
		testNoFall.give(inv);
		testInfo.give(inv);
		testAntKB.give(inv);
		testPrison.give(inv);
		
		player.setGameMode(GameMode.CREATIVE);

	}
	public void leaveAdminMode(Player player) {
		ItemAPI.getItems(player);
		GameAPI.show(player);
		player.setGameMode(GameMode.SURVIVAL);
		players.remove(player);
	}
	
	
	
	
	@Override
	public void register(Plugin plugin) {
		new Click(testNoFall.getItem(), new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (players.contains(p)) {
					jumpEffect.create(e.getRightClicked());
				}
			}
		}).register(plugin);
		new Click(testFF.getItem(), new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (players.contains(p)) {
					GameAPI.show(p);
					GameAPI.makeInvunerable(p, 1);
					Cs.chat(p,"§6Troca rapida ativada!");
					API.TIME.delay(20, new Runnable() {

						@Override
						public void run() {
							GameAPI.hide(p);
							Cs.chat(p,"§6Troca rapida desativada!");
						}
					});
				}
			}
		}).register(plugin);
		new Click(testAutoSoup.getItem(), new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (players.contains(p)) {
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						p.openInventory(target.getInventory());

					}
				}
			}
		}).register(plugin);
		new Click(testInfo.getItem(), new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (players.contains(p)) {
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						p.sendMessage("§6Informações do §e" + target.getName());
						p.sendMessage("§aGamemode: §2" + target.getGameMode());
						p.sendMessage("§aKills: §2"
								+ target.getStatistic(Statistic.PLAYER_KILLS));
						p.sendMessage("§aDeaths: §2"
								+ target.getStatistic(Statistic.DEATHS));
						p.sendMessage("§aIP: §2" + RexAPI.getIp(p));
						if (VaultAPI.hasVault() && VaultAPI.hasEconomy()) {
							p.sendMessage("§aMoney: §2"
									+ VaultAPI.getEconomy().getBalance(p));
						}

					}
				}
			}
		}).register(plugin);
		new Click(testPrison.getItem(), new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (players.contains(p)) {
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						Location loc = target.getLocation();
						loc.clone().add(0, 10, 0).getBlock().setType(Material.BEDROCK);
						loc.clone().add(0, 11, 1).getBlock().setType(Material.BEDROCK);
						loc.clone().add(0, 11, -1).getBlock().setType(Material.BEDROCK);
						loc.clone().add(1, 11, 0).getBlock().setType(Material.BEDROCK);
						loc.clone().add(-1, 11, 0).getBlock().setType(Material.BEDROCK);
						loc.clone().add(0, 13, 0).getBlock().setType(Material.BEDROCK);
						target.teleport(loc.add(0,11,0));

					}
				}
			}
		}).register(plugin);

		 super.register(plugin);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (players.contains(p)) {
				players.remove(p);
				leaveAdminMode(p);
				p.sendMessage(messageOff);
			} else {
				players.add(p);
				joinAdminMode(p);
				p.sendMessage(messageOn);
			}

		}
		return true;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (players.contains(p)) {
				e.setCancelled(false);
			}

		}
	}

}
