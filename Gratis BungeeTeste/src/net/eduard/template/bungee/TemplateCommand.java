package net.eduard.template.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class TemplateCommand extends Command{

	public TemplateCommand() {
		super("template");
	}

	@Override
	public void execute(CommandSender sender,
			String[] args) {
		sender.sendMessage("§cEnviando mensagem para o Bukkit");
//		Template.getPlugin().sendToBukkit("console", "kick "+sender.getName());
//		Template.getPlugin(),() -> 
//				BungeeCord.getInstance().getServerInfo("lobby"));
		
//		p.sendData("BungeeCord", new byte[1024]);
		
	}

}
