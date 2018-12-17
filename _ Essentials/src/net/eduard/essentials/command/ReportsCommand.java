package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.util.fancyful.FancyMessage;

public class ReportsCommand implements CommandExecutor {

	HashMap<String, Long> tempo = new HashMap<>();
	int segundos = 60;

	public static ArrayList<String> Report = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;

		if (label.equalsIgnoreCase("report")) {
			if ((args.length == 0) || (args.length == 1)) {

				p.sendMessage("§cPor favor, use /report <jogador> <motivo>");

				return true;
			}

			if (this.tempo.containsKey(p.getName())) {
				long segundosRestantes = ((Long) this.tempo.get(p.getName())).longValue() / 1000L + this.segundos
						- System.currentTimeMillis() / 1000L;
				if (segundosRestantes > 0L) {
					p.sendMessage(
							"§cVocê ainda não pode usar este comando. (Tempo restante: " + segundosRestantes + "s).");
					return true;
				}
				if (segundosRestantes <= 0L) {
					this.tempo.remove(p.getName());
				}
			} else if (!this.tempo.containsKey(p.getName())) {
				this.tempo.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
			}

			if (args.length > 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i] + " ");
				}

				String alvo = args[0];
				Player target = Bukkit.getPlayer(alvo);

				if (target == null) {

					p.sendMessage("§cEste jogador está offline.");
					return true;

				}

				p.sendMessage(" ");
				p.sendMessage("§aVocê reportou o jogador §7" + target.getName() + " §a por §7" + sb.toString());
				p.sendMessage("§aUm membro da nossa equipe foi notificado e o comportamento");
				p.sendMessage("§adeste jogador ser\u00E1 analiso e se preciso punido");
				p.sendMessage(" ");
				p.sendMessage(" §a* O uso abusivo deste comando resultar em punição.");
				p.sendMessage(" ");

				for (Player staff : Bukkit.getOnlinePlayers()) {
					if ((staff.hasPermission("slime.ajudante")) && (!Report.contains(staff.getName()))) {
						staff.sendMessage("");
						staff.sendMessage("§4•§c Uma nova denuncia foi recebia:");
						staff.sendMessage("");
						staff.sendMessage("   §4• §cQuem reportou: §7" + p.getName());
						staff.sendMessage("   §4• §cReportado: §7" + target.getName());
						staff.sendMessage("   §4• §cMotivo: §7" + sb.toString());
						staff.sendMessage("");
						new FancyMessage("   §4•§c Clique para teleportar: ")
								.tooltip("§cClique para teleportar a jogador: §7\"" + target.getName() + "\"" + "§c.")
								.command("/tp " + target.getName()).send(staff);
						staff.sendMessage("");
						staff.playSound(staff.getLocation(), Sound.LEVEL_UP, 1, 1);
					}
				}
			}
		}

		if ((label.equalsIgnoreCase("togglereport"))) {
			if (p.hasPermission("slime.ajudante")) {
				if (Report.contains(p.getName())) {
					Report.remove(p.getName());
					p.sendMessage("§aSeu recebimento de reports foi ativado.");
				} else {
					Report.add(p.getName());
					p.sendMessage("§cSeu recebimento de reports foi desativado.");
				}
			} else {

//				MensagemAPI.semPermissao(p, "Ajudante");

			}
		}
		return false;
	}
}