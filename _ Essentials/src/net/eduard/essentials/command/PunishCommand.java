package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PunishCommand extends CommandManager {
	public List<String> messages = new ArrayList<>();
	public List<String> commands = new ArrayList<>();
	public List<String> hovers = new ArrayList<>();
	public String header = "Punições para o Jogador $player";

	public PunishCommand() {
		super("punish", "punir");
		messages.add("Banir jogador permanente");
		commands.add("/ban $player");
		hovers.add("Ao clicar ira Banir jogador permanente");

	}

//	p.sendMessage("");
//	p.sendMessage(" §7 (!) Escolha uma opção para punir o jogador " + alvo.getName());
//	p.sendMessage("");
//	new FancyMessage(" §7• Uso de Hacker ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/ban " + alvo.getName() + " Uso de Hacker").send(p);
//
//	new FancyMessage(" §7• Comercialização de Contas ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/ipban " + alvo.getName() + " Comercialização de Contas").send(p);
//
//	new FancyMessage(" §7• Negou Tela ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/ban " + alvo.getName() + " Negou Tela").send(p);
//
//	new FancyMessage(" §7• Divulgação de Servidores ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/ban " + alvo.getName() + " Divulgação de Servidores").send(p);
//
//	new FancyMessage(" §7• Anti-Jogo ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/tempban " + alvo.getName() + " 10 h Anti-Jogo").send(p);
//
//	new FancyMessage(" §7• Abuso de Bugs ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/ipban " + alvo.getName() + " Abuso de Bugs").send(p);
//
//	new FancyMessage(" §7• Ofensa a Equipe ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/tempban " + alvo.getName() + " 7 d Ofensa a Equipe").send(p);
//
//	new FancyMessage(" §7• Ofensa a Jogador ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/tempban " + alvo.getName() + " 1 d Ofensa a Jogador").send(p);
//	new FancyMessage(" §7• Flod ").tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/mutar " + alvo.getName() + " Flod 1 h ").send(p);
//	new FancyMessage(" §7• Spam ").tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/mutar " + alvo.getName() + " Spam 1 h ").send(p);
//	new FancyMessage(" §7• CapsLock ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/mutar " + alvo.getName() + " CapsLock 1 h ").send(p);
//	new FancyMessage(" §7• Hashtags ")
//			.tooltip("§7Clique para punir o jogador " + alvo.getName() + ".")
//			.command("/mutar " + alvo.getName() + " Hashtags 1 h ").send(p);
//	p.sendMessage("");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length <= 1) {
				sendUsage(sender);
			} else {

				String player = args[0];
				if (Mine.existsPlayer(sender, player)) {
					Player target = Mine.getPlayer(player);
					sender.sendMessage(header.replace("$player", target.getName()));
					int id = 0;
					for (String message : messages) {
						try {
							TextComponent text = new TextComponent(message.replace("$player", target.getName()));
							text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder(hovers.get(id).replace("$player", target.getName()))
											.create()));
							text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
									commands.get(id).replace("$player", target.getName())));

							p.spigot().sendMessage(text);

						} catch (Exception e) {
							e.printStackTrace();
						}

						id++;

					}
				}
			}
		}
		return true;
	}

}
