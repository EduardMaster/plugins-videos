package net.eduard.essentials.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportRequestCommand implements CommandExecutor {

	public static HashMap<String, Long> tpaCooldown = new HashMap<>();
	public static HashMap<String, String> currentRequest = new HashMap<>();

	@SuppressWarnings("static-access")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = null;
		if ((sender instanceof Player)) {
			p = (Player) sender;
		}
		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (p != null) {
				if (!p.hasPermission("Tpa.SemDelay")) {
					int cooldown = 30;
					if (this.tpaCooldown.containsKey(p.getName())) {
						long diff = (System.currentTimeMillis()
								- ((Long) this.tpaCooldown.get(sender.getName())).longValue()) / 1000L;
						if (diff < cooldown) {
//							RexAPI.sendActionBar(p, "§cAguarde " + cooldown + " para usar o tpa novamente.");
							return false;
						}
					}
				}
				if (args.length > 0) {
					final Player target = Bukkit.getPlayer(args[0]);
					long keepAlive = 30 * 20;
					if (target == null) {
						sender.sendMessage(JogadorOff());
						return false;
					}
					if (target == p) {
						sender.sendMessage(JogadorVoce());
						return false;
					}
//					if (!API.tpa(target)) {
//						sender.sendMessage("§cO jogador está com o teleporte desativo.");
//						return true;
//					}
					sendRequest(p, target);

//					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
//						public void run() {
//							killRequest(target.getName());
//						}
//					}, keepAlive);

					this.tpaCooldown.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
				} else {
					p.sendMessage("§cPor favor, use /tpa {Jogador}");
				}
			} else {
				sender.sendMessage("Comandos apenas in game");
				return false;
			}
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("tpaccept")) {
			if (p != null) {
				if (this.currentRequest.containsKey(p.getName())) {
					Player heIsGoingOutOnADate = Bukkit.getPlayer((String) this.currentRequest.get(p.getName()));
					this.currentRequest.remove(p.getName());
					if (heIsGoingOutOnADate != null) {
						heIsGoingOutOnADate.teleport(p);
						p.sendMessage("§aPedido aceito.");
						heIsGoingOutOnADate.sendMessage("§aTeleportando com sucesso");
					} else {
						sender.sendMessage("§cO player que você enviou tpa não esta mais online.");
						return false;
					}
				} else {
					sender.sendMessage("§cVocê não possui um pedido de teleporte.");
					return false;
				}
			} else {
				sender.sendMessage("Comandos apenas in game");
				return false;
			}
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("tpdeny")) {
			if (p != null) {
				if (this.currentRequest.containsKey(p.getName())) {
					Player poorRejectedGuy = Bukkit.getPlayer((String) this.currentRequest.get(p.getName()));
					this.currentRequest.remove(p.getName());
					if (poorRejectedGuy != null) {
						poorRejectedGuy.sendMessage(ChatColor.RED + p.getName() + " recusou seu pedido de teleporte.");
						p.sendMessage("§cVocê recusou o pedido de teleport de " + poorRejectedGuy.getName() + ".");
						return true;
					}
				} else {
					sender.sendMessage("§cVocê não possui um pedido de teleporte.");
					return false;
				}
			} else {
				sender.sendMessage("Comandos apenas in game");
				return false;
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings("static-access")
	public boolean killRequest(String key) {
		if (this.currentRequest.containsKey(key)) {
			Player loser = Bukkit.getPlayer((String) this.currentRequest.get(key));
			if (loser != null) {
				loser.sendMessage(ChatColor.RED + "Seu pedido de teleporte expirou.");
			}
			this.currentRequest.remove(key);

			return true;
		}
		return false;
	}

	@SuppressWarnings("static-access")
	public void sendRequest(Player sender, Player recipient) {
		sender.sendMessage("§ePedido enviado para §6" + recipient.getName() + ".");

		recipient.sendMessage(" ");
		recipient.sendMessage("§6" + sender.getName() + " §epediu para ir até você.");

		TextComponent TpaAceitar = new TextComponent("§ePara aceitar o pedido, use §6/tpaccept");
		TpaAceitar.setBold(Boolean.valueOf(true));
		TpaAceitar.setHoverEvent(
				new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6/tpaccept").create()));
		TpaAceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
		recipient.spigot().sendMessage(TpaAceitar);

		TextComponent TpaNegar = new TextComponent("§ePara aceitar o pedido, use §6/tpdeny.");
		TpaNegar.setBold(Boolean.valueOf(true));
		TpaNegar.setHoverEvent(
				new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6/tpdeny.").create()));
		TpaNegar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
		recipient.spigot().sendMessage(TpaNegar);

		recipient.sendMessage("§eEste pedido será expirado em 1 minuto.");
		recipient.sendMessage(" ");

		this.currentRequest.put(recipient.getName(), sender.getName());
	}

	public static String Teleportando() {
		return "§aTeleportando.";
	}

	public static String TeleportandoAguarde() {
		return "§aAguarde 3 segundos.";
	}

	public static String NaoVip() {
		return "§cVocê precisa do grupo Gerente ou superior para executar este comando.";
	}

	public static String SemPermissao() {
		return "§cVocê precisa do grupo Gerente ou superior para executar este comando.";
	}

	public static String JogadorOff() {
		return "§cEste jogadador não existe em nosso banco de dados, ou esta offline.";
	}

	public static String JogadorVoce() {
		return "§cEste jogadador não existe em nosso banco de dados, ou esta offline.";
	}
}
