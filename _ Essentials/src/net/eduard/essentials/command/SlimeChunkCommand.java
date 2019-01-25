package net.eduard.essentials.command;

import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.essentials.EssentialsPlugin;

public class SlimeChunkCommand extends CommandManager {
	

	public SlimeChunkCommand() {
		super("slime","gosma");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;
			ArrayList<Player> lista = EssentialsPlugin.getInstance().getSlimeChunkActive();
			if (lista.contains(p)) {
				lista.remove(p);

				p.sendMessage(" ");
				p.sendMessage("§cVocê desligou o localizador de Slime Chunk, Você não receber\u00E1");
				p.sendMessage("§cmas estes barulhos, caso deseje ativar use §nslime§c");
				p.sendMessage(" ");
				p.playSound(p.getLocation(), Sound.SLIME_WALK, 5.0F, 5.0F);

			} else {

				p.sendMessage(" ");
				p.sendMessage("§aVocê ativou o localizador de §nSlime Chunks§a. Sempre que você");
				p.sendMessage("§apassar em uma Slime Chunk você escutará um §nbarulho de slime§a");
				p.sendMessage("§afique atento e ative os sons do seu Jogo");
				p.sendMessage(" ");
				p.playSound(p.getLocation(), Sound.SLIME_WALK, 5.0F, 5.0F);
				lista.add(p);
			}

		}
		return true;

	}
}
