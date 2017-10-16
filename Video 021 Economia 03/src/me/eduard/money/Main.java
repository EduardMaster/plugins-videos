
package me.eduard.money;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	public HashMap<OfflinePlayer, Integer> money = new HashMap<>();

	public int getInicial() {

		return cf.getInt("Inicial");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para Players!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("money")) {
			if (args.length == 0) {
				p.sendMessage("§6Seu saldo é de " + money.get(p));
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					p.sendMessage("§cEsse player não existe!");
					return true;
				}
				p.sendMessage("§cSaldo do player " + target.getName() + " "
					+ money.get(target));
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					try {
						Integer numero = Integer.valueOf(args[2]);
						money.put(p, numero);
						p.sendMessage("§6Seu saldo: " + numero);
					} catch (Exception ex) {
						p.sendMessage("§cIsto não é um Numero!");
					}
				}
				if (args[0].equalsIgnoreCase("take")) {
					try {
						Integer numero = Integer.valueOf(args[1]);
						money.put(p,
							money.get(p) - numero < 0 ? 0 : money.get(p) - numero);
						p.sendMessage("§6Seu saldo: " + numero + " Numero retirado: "
							+ (money.get(p) - numero));
					} catch (Exception ex) {
						p.sendMessage("§cIsto não é um Numero!");
					}
				}
				if (args[0].equalsIgnoreCase("add")) {
					try {
						Integer numero = Integer.valueOf(args[1]);
						money.put(p, money.get(p) + numero);
						p.sendMessage("§6Seu saldo: " + numero
							+ " Numero adicionado: " + money.get(p) + numero);
					} catch (Exception ex) {
						p.sendMessage("§cIsto não é um Numero!");
					}
				}
			}
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("set")) {
					Player target = Bukkit.getPlayerExact(args[1]);
					if (target == null) {
						p.sendMessage("§cEsse player não existe!");
						return true;
					}
					// /money set edu 2015
					try {
						Integer numero = Integer.valueOf(args[2]);
						money.put(target, numero);
						p.sendMessage(
							"§6Saldo do player " + target.getName() + " " + numero);
					} catch (Exception ex) {
						p.sendMessage("§cIsto não é um Numero!");
					}
				}
				if (args[0].equalsIgnoreCase("take")) {
					Player target = Bukkit.getPlayerExact(args[2]);
					if (target == null) {
						p.sendMessage("§cEsse player não existe!");
						return true;
					}
					// /money take 100 edu
					try {
						Integer numero = Integer.valueOf(args[1]);
						money.put(target, money.get(target) - numero < 0 ? 0
							: money.get(target) - numero);
						p.sendMessage("§6Saldo do player " + target.getName() + " "
							+ numero + " Numero retirado: "
							+ (money.get(target) - numero));
					} catch (Exception ex) {
						p.sendMessage("§cIsto não é um Numero!");
					}
				}
				if (args[0].equalsIgnoreCase("add")) {
					Player target = Bukkit.getPlayerExact(args[2]);
					if (target == null) {
						p.sendMessage("§cEsse player não existe!");
						return true;
					}
					// /money add 100 edu
					try {
						Integer numero = Integer.valueOf(args[1]);
						money.put(target, money.get(target) + numero);
						p.sendMessage("§6Saldo do player " + target.getName() + " "
							+ numero + " Numero adicionado: " + money.get(target)
							+ numero);
					} catch (Exception ex) {
						p.sendMessage("§cIsto não é um Numero!");
					}
				}
			}
		}
		return true;
	}

	@Override
	public void onDisable() {

		HandlerList.unregisterAll();
		saveMoney();
	}

	@Override
	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {

		m = this;
		cf = getConfig();
		sh = Bukkit.getScheduler();
		pm = Bukkit.getPluginManager();
		saveInicial();
	}

	public void putMoney() {

		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (cf.contains("Money." + p.getName())) {
				money.put(p, cf.getInt("Money." + p.getName()));
			} else {
				money.put(p, getInicial());
			}
		}
	}

	public void saveInicial() {

		cf.addDefault("Inicial", 100);
		cf.options().copyDefaults(true);
		saveConfig();
	}

	public void saveMoney() {

		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			cf.set("Money." + p.getName(), money.get(p));
		}
		saveConfig();
	}
}
