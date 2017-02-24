
package me.eduard.extra;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@EventHandler
	public void ChatLimpo(PlayerCommandPreprocessEvent e) {

		if (e.getMessage().toLowerCase().contains("limparchat")
			| e.getMessage().toLowerCase().contains("chatlimpo")) {
			e.getPlayer().chat("/clearchat");
			e.setCancelled(true);
		}

	}

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para players!");
			return true;
		}
		Player p = (Player) sender;
		int numero = getConfig().getInt("numero_de_linhas_para_limpar");
		if (command.getName().equalsIgnoreCase("clearchat")) {
			for (int i = numero; i > 0; i--) {
				p.sendMessage("");
			}
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
				getConfig().getString("chat_limpo_mensagem")));
		} else if (command.getName().equalsIgnoreCase("clearchatall")) {

			for (int i = numero; i > 0; i--) {
				Bukkit.broadcastMessage("");
			}
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
				getConfig().getString("chat_limpo_mensagem")));
		}

		return true;
	}

	public void onEnable() {

		getCommand("clearchat").setPermission("chatclear.normal");
		getCommand("clearchatall").setPermission("chatclear.all");
		Bukkit.getPluginManager().registerEvents(this, this);
		for (World world : Bukkit.getWorlds()) {
			boolean naoChover =
				getConfig().getBoolean("sempre_dia." + world.getName());
			world.setGameRuleValue("doDaylightCycle", "" + naoChover);
			if (naoChover) {
				world.setTime(6000);
			}
		}
	}

	public void onLoad() {

		getConfig().addDefault("chat_limpo_mensagem",
			"&a&lO chat foi limpo por &6Eduard");
		getConfig().addDefault("numero_de_linhas_para_limpar", 60);
		for (World world : Bukkit.getWorlds()) {
			getConfig().addDefault("sempre_dia." + world.getName(), true);
			getConfig().addDefault("nao_chover." + world.getName(), true);
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@EventHandler
	public void SemChuva(WeatherChangeEvent e) {

		if (getConfig().getBoolean("nao_chover." + e.getWorld().getName())) {
			if (e.toWeatherState()) {
				e.setCancelled(true);
			}
		}
	}
}
