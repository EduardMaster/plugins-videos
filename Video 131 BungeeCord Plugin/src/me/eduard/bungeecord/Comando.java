package me.eduard.bungeecord;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Comando extends Command {

	public Comando() {
		super("teste","teste.permisao","exemplo");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			sender.sendMessage(new TextComponent("§aVoce vai ser enviado para o Lobby"));
			p.connect(BungeeCord.getInstance().getServerInfo("lobby"));
			
		}
		
	}

}
