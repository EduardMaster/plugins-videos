package net.eduard.worldedit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new Eventos(), plugin);
		Bukkit.getPluginManager().registerEvents(this ,this);
	}
	private Location loc1;
	private Location loc2;
	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getMaterial() == Material.WOOD_AXE) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				loc2 = e.getClickedBlock().getLocation();
				p.sendMessage("§dVoce setou a localização 1");
				e.setCancelled(true);
			} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				loc2 = e.getClickedBlock().getLocation();
				p.sendMessage("§dVoce setou a localização 2");
				e.setCancelled(true);
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas players!");

			return true;
		}
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("set")) {
			if (args.length == 0) {
				p.sendMessage("§c/set <id>");
			} else {
				if (args.length >= 1) {
					try {
						Integer numero = Integer.valueOf(args[0]);
						if (loc1 == null) {
							p.sendMessage("§cLocalização 1 não setada!");
							return true;
						}
						if (loc2 == null) {
							p.sendMessage("§cLocalização 2 não setada!");
							return true;
						}
						List<Location> lista = WorldEdit.getLocation(loc1,
								loc2);
						for (Location loc : lista) {
							loc.getBlock().setTypeId(numero);;

						}
						p.sendMessage("§dVoce setou " + lista.size()
								+ " blocos para o tipo "
								+ Material.getMaterial(numero).name());
						

					} catch (Exception e) {
						p.sendMessage("§cPrecisa ser um numero!" + args[0]);
					}
				}
			}
		}
		return true;
	}
	public Location getLoc2() {
		return loc2;
	}
	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	public Location getLoc1() {
		return loc1;
	}
	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}

}
