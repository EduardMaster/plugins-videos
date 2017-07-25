package net.eduard.chatclick;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class Evento implements Listener {
	public static void sendText(Player p) {
		TextComponent texto2 = new TextComponent("§6Texto com cor");
		
		ComponentBuilder builder = new ComponentBuilder("§aLinha 1");
		builder.append("\n§bLinha 2");
		BaseComponent[] lista = builder.create();
		
		HoverEvent hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, lista);
		texto2.setHoverEvent(hover);
		
		ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/testando");
		
		texto2.setClickEvent(click);
		
		p.spigot().sendMessage(texto2);
		
		
		TextComponent texto = new TextComponent("§aTexto no chat");
		BaseComponent[] textos = new ComponentBuilder(
				"§6Texto ao passar o Mouse!").create();
		HoverEvent passarOMouse = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				textos);
		texto.setHoverEvent(passarOMouse);
		ClickEvent clicar = new ClickEvent(Action.RUN_COMMAND, "/comando");
		texto.setClickEvent(clicar);
		p.spigot().sendMessage(texto);

	}
	public static void sendText2(Player p) {

		TextComponent texto = new TextComponent("§aEnvaido");
		BaseComponent[] textos = new ComponentBuilder(
				"§6Clique para fazer algo").create();
		HoverEvent passarOMouse = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				textos);
		texto.setHoverEvent(passarOMouse);
		ClickEvent clicar = new ClickEvent(Action.SUGGEST_COMMAND, "/ban "+p.getName());
		texto.setClickEvent(clicar);
		p.spigot().sendMessage(texto);

	}
	public static void sendTextTest(Player p) {
		TranslatableComponent giveMessage = new TranslatableComponent( "commands.give.success" );
		TranslatableComponent item = new TranslatableComponent( "item.swordDiamond.name" );
		item.setColor( ChatColor.GOLD );
		giveMessage.addWith( item );
		giveMessage.addWith( "32" );
		TextComponent username = new TextComponent( "Thinkofdeath" );
		username.setColor( ChatColor.AQUA );
		giveMessage.addWith( username );
		p.spigot().sendMessage( giveMessage );
	}
	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().startsWith("/test")) {
			e.setCancelled(true);
			sendText(p);

		}
		if (e.getMessage().startsWith("/comando")) {
			e.setCancelled(true);
			sendText2(p);
			sendTextTest(p);
		}
		if (e.getMessage().startsWith("/testando")) {
			e.setCancelled(true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60,1));
		}
		// Player p = e.getPlayer();
		// if (e.getMessage().startsWith("/test")) {
		// e.setCancelled(true);
		// TextComponent textoclicavel = new TextComponent("§6Click aqui");
		// textoclicavel.setHoverEvent(new HoverEvent(
		// HoverEvent.Action.SHOW_TEXT,
		// new ComponentBuilder("§6Ao clicar ira fazer um comando!")
		// .create()));
		// textoclicavel.setClickEvent(
		// new ClickEvent(Action.RUN_COMMAND, "/comando"));
		// p.spigot().sendMessage(textoclicavel);
		//
		// }
		// if (e.getMessage().startsWith("/comando")) {
		// p.sendMessage("§6Voce executou o comando");
		// p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*60,1));
		// }
	}
}
