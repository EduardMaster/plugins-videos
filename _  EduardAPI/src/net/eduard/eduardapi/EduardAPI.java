package net.eduard.eduardapi;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.eduard.api.API;
import net.eduard.api.chat.ChatChannel;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.event.PlayerChatEvent;
import net.eduard.api.event.PlayerTargetEvent;
import net.eduard.api.game.Tag;
import net.eduard.api.manager.EduardPlugin;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.tutorial.eventos.BedrockQuebravel;
import net.eduard.eduardapi.command.ApiCommand;
import net.eduard.eduardapi.command.ConfigCommand;
import net.eduard.eduardapi.command.GotoCommand;
import net.eduard.eduardapi.command.MapCommand;

public class EduardAPI extends EduardPlugin implements Listener {
	private static JavaPlugin plugin;

	public static JavaPlugin getInstance() {
		return plugin;
	}

	public void setup() {
		plugin = this;
		API. resetScoreboards();
		ConfigSection.consoleMessage("§bEduardAPI §fBarAPI §aativado!");
		ConfigSection.consoleMessage("§bEduardAPI §fDataBase §agerada!");
		RexAPI.saveObjects();
		API.loadMaps();
		ConfigSection.consoleMessage("§bEduardAPI §fMapas §acarregados!");
		API.AUTO_RESPAWN = getConfig().getBoolean("auto-respawn");
		API.NO_JOIN_MESSAGE = getConfig().getBoolean("no-join-message");
		API.NO_QUIT_MESSAGE = getConfig().getBoolean("no-quit-message");
		API.NO_DEATH_MESSAGE = getConfig().getBoolean("no-death-message");
		API.CUSTOM_CHAT = config.getBoolean("custom-chat");
		API.ON_JOIN = config.message("on-join-message");
		API.ON_QUIT = config.message("on-quit-message");
		API.SERVER_TAG = config.message("server-tag");
		API.CHAT_FORMAT = config.message("chat-format");
		API.AUTO_SAVE_CONFIG = config
				.getBoolean("auto-save-configs-on-disable-plugin");
		ConfigSection.consoleMessage("§bEduardAPI §aativado!");
		API.event(this);
		API.event(new BedrockQuebravel());
		if (config.getBoolean("auto-relog")) {
			for (Player p : API.getPlayers()) {
				API.callEvent(new PlayerJoinEvent(p, null));
			}
		}
//		API.timer(this, 10, new Runnable() {
//
//			@Override
//			public void run() {
//				updateTargets();
//			}
//		});
		new MapCommand().register();
		new ConfigCommand().register();
		new ApiCommand().register();
		new GotoCommand().register();
		config.saveConfig();
	}
	@EventHandler
	public void evnet(PlayerInteractEvent e) {
		if (e.getMaterial() == Material.GOLD_AXE) {

		}
	}

	public void updateTargets() {
		for (Player p : API.getPlayers()) {
			PlayerTargetEvent event = new PlayerTargetEvent(p,
					GameAPI.getTargetEntity(p));
			API.callEvent(event);

		}
	}

	public void onDisable() {
		API.saveMaps();

		if (API.AUTO_SAVE_CONFIG) {
			Config.saveConfigs();
			ConfigSection.consoleMessage("§bEduardAPI §aConfigs salvadas!");
		}
		ConfigSection.consoleMessage("§bEduardAPI §aMapas salvados!");
		ConfigSection.consoleMessage("§bEduardAPI §cdesativado!");
	}
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (API.CUSTOM_CHAT) {
			Player player = e.getPlayer();
			e.setCancelled(true);
			PlayerChatEvent chat = new PlayerChatEvent(player,
					ChatChannel.LOCAL, API.CHAT_FORMAT, e.getMessage());
			sendChat(chat);
		}

	}
	public static void sendChat(PlayerChatEvent chat) {
		Player p = chat.getPlayer();
		API.callEvent(chat);
		if (!chat.isCancelled()) {
			String message = chat.getFormat();

			for (Entry<String, String> map : chat.getTags().entrySet()) {
				message = message.replaceAll(map.getKey(), map.getValue());
			}
			ConfigSection.sendMessage(chat.getPlayers(),
					chat.getFormat()
							.replace("$channel", chat.getChannel().getTag())
							.replace("$message", chat.getMessage())
							.replace("$player", p.getName()));
		}
	}

	@EventHandler
	public void entrar(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (!Tag.getTags().containsKey(p)) {
			API.resetTag(p);
		}
		if (getConfig().getBoolean("save-players")) {
			RexAPI.saveObject("Players/" + p.getName() + " " + p.getUniqueId(),
					p);
		}
		if (getConfig().getBoolean("custom-join-message")) {
			e.setJoinMessage(API.ON_JOIN.replace("$player", p.getName()));
		}

		if (API.NO_JOIN_MESSAGE) {
			e.setJoinMessage(null);
			return;
		}

	}
	@EventHandler
	public void morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (API.AUTO_RESPAWN) {
			if (p.hasPermission("eduardapi.autorespawn")) {
				API.TIME.delay(1L, new Runnable() {

					public void run() {
						if (p.isDead()) {
							p.setFireTicks(0);
							try {
								RexAPI.makeRespawn(p);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});
			}

		}
		if (API.NO_DEATH_MESSAGE) {
			e.setDeathMessage("");
		}
	}
	@EventHandler
	public void sair(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (getConfig().getBoolean("custom-quit-message"))
			e.setQuitMessage(API.ON_QUIT.replace("$player", p.getName()));
		if (API.NO_QUIT_MESSAGE) {
			e.setQuitMessage("");
		}

	}
}
